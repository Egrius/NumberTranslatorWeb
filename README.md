# NumberTranslatorWeb

A web service that converts numbers into Russian text with correct
declensions: `123` -> `сто двадцать три`, `1.5` -> `одна целая пять десятых`.

## Motivation

An early project - my first real Spring Boot app. The goal was to build
something non-trivial algorithmically, not another CRUD. Number-to-text is
tricky in Russian: declensions depend on the digit, the grammatical case, and
the part of the number (integer or fractional).

The focus:

- Plain JDBC via `JdbcTemplate` - no ORM, no Hibernate
- Manual `RowMapper` mapping
- Dictionary stored in the database, not hardcoded
- Correct Russian declensions for thousands, millions, billions, trillions

## How it works

The number is split into 3-digit groups from the right. Each group is
translated by matching digits to dictionary rows, then joined with the right
declension based on the group's magnitude:

```
1234567
-> 1 234 567
-> один миллион + двести тридцать четыре тысячи + пятьсот шестьдесят семь
```

Two edge cases make it non-trivial:

- **10–19 must be translated as a unit.** You can't say "десять один" - it's
  "одиннадцать". The code short-circuits for this range.
- **Fractional part is a different grammar.** `1.5` is "одна целая пять
  десятых", not "один целый пять десятые". The last integer digit determines
  the form of "целая"/"целых".

Negative numbers and leading-zero validation are handled too.

## Dictionary in the database

Instead of hardcoding Russian numerals in Java, they live in tables:

| Table          | Holds                                          |
|----------------|------------------------------------------------|
| `digits`       | 1..9                                           |
| `tens`         | 10, 20, 30, ..., 90                            |
| `hundreds`     | 100, 200, ..., 900                             |
| `numbers`      | 10..19 (teens, special case)                   |
| `declinations` | singular / few / many forms + fraction bases   |

`DeclinationsRepository` handles the lookup. `DeclinationsModel` carries all
three forms at once, so the service picks the right one without extra queries.

## Technologies

- Java 22, Spring Boot
- Spring JDBC (`JdbcTemplate`) - no JPA
- Thymeleaf for the web UI
- MySQL for the dictionary
- JUnit 5, Mockito
- Docker, Docker Compose

## Requirements

- JDK 22+
- Maven
- Docker

## Quick start

```bash
git clone https://github.com/Egrius/NumberTranslatorWeb

cd NumberTranslatorWeb

# Build the .jar that Dockerfile copies 
mvn clean package

docker compose up --build
```

Then open in browser: http://localhost:8088/number_translator

On first start, MySQL automatically loads the dictionary from `dump.sql`.  
Subsequent starts reuse the persisted data (named volume `mysql-data`).  

To reset the database (re-run `dump.sql`): `docker compose down -v && docker compose up`.

## What's interesting inside

### 1. Parameterized repository hierarchy

`BaseRepository<T, V>` is a small abstraction over `JdbcTemplate`:

```java
public abstract class BaseRepository<T, V> {
    protected final JdbcTemplate jdbc;
    public abstract T getRepresentationByValue(V value);
}
```

Each subtype (`DigitsRepository`, `TensRepository`, ...) only supplies the SQL
and the `RowMapper`. The service layer doesn't know the column names - it just
asks for a value and gets back a model.

### 2. Group-by-3 processing with modular arithmetic

`NumberTranslatorService.buildNumber(...)` walks the digit list and splits it
into groups of 3, 2, or 1 depending on what's left:

```java
if (countToPass % 3 == 0) { /* hundreds + tens + units */ }
else if (countToPass % 3 == 2) { /* tens + units */ }
else { /* single digit */ }
```

This handles arbitrary length without recursion or padding.

### 3. Declension logic driven by the digit

Russian declension depends on the last digit of the group:

- ends in `1` -> singular (`одна тысяча`)
- ends in `2..4` -> few (`две тысячи`)
- ends in `0, 5..9, 11..19` -> many (`пять тысяч`)

The rule lives in `DeclinationsRepository.getIntDeclination(...)`. The service
doesn't need to know the grammar - it just passes the number and the magnitude.

### 4. Fractional handling is separate

Fractional part goes through a different path: strip trailing zeros to
determine the magnitude ("десятых" vs "сотых" vs "тысячных"), strip leading
zeros to get the number itself, and pick the form of "целая"/"целых" based on
the last integer digit.

Trying to reuse the integer pipeline would produce wrong grammar.

## Tests

Unit tests for repositories (Mockito mocking `JdbcTemplate`) and for
`VocabularyService` with mocked repository dependencies. Boundary cases are
covered: `0`, negative numbers, fractional with trailing zeros, invalid
characters.

Run: `mvn test`

## Restrictions and compromises

- **Only Russian.** The grammar rules are hardcoded for Russian declensions.
  Supporting another language would mean a different rule engine, not just
  another dictionary.
- **No integration tests.** Repository tests mock `JdbcTemplate`, so they
  verify that the right SQL is built, not that the DB returns what's expected.
  A Testcontainers-based setup would catch more (schema drift, wrong column
  names).
- **The public demo is offline.** It was hosted on Render with a free MySQL
  instance from freesqldatabase.com - both expired. To run it, use the local
  Docker setup.
- **UI is minimal.** One Thymeleaf page, a form, and a result block. No
  JavaScript, no validation on the client side.
- **No API.** The service is web-only - no JSON endpoint. Adding one would be
  a one-file change.

## Status

Finished as a learning project. Algorithm works, UI works, tests pass.
Not maintained.

If you want to run it: local Docker setup, no external dependencies.
