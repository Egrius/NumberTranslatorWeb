# 📟 NumberTranslatorWeb

**Веб-сервис** для перевода чисел в прописной формат (например, 123 -> "сто двадцать три") с поддержкой дробных чисел, отрицательных значений и правильных склонений.

> 🎯 Проект создан в учебных целях для отработки: прямого JDBC, ручного маппинга, Docker-контейнеризации и деплоя на Render. Проект является завершённым.

## 🔧 Требования
- Java 21+
- Docker и Docker Compose (или MySQL отдельно)
- Maven

## ✨ Возможности

- Поддержка чисел до триллионов — разбивка на разряды (тысячи, миллионы и т.д.)
- Дробные числа — корректное склонение ("целая"/"целых", "десятая"/"десятых")
- Отрицательные числа — приставка "минус"
- Валидация ввода — обработка ошибок (недопустимые символы, ведущие нули)
- Веб-интерфейс — минималистичная HTML-страница на Thymeleaf

## 💻 Принцип работы

### Обработка чисел

Алгоритм разбивает число на разряды и собирает его по частям:
``` java
// Пример: число 1 234 567
// 1  →  "один миллион"
// 234 → "двести тридцать четыре тысячи"  
// 567 → "пятьсот шестьдесят семь"
```
### Особые случаи

- 10-19 — обрабатываются отдельно (не "десять один", а "одиннадцать")
- Склонения — правильные окончания для разных разрядов
- Дробная часть — автоматическое удаление ведущих и завершающих нулей

### Контейнеризация и деплой
``` docker
# Dockerfile

FROM openjdk:22-jdk-slim-buster
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
```

``` yaml
# docker-compose.yaml
services:
  translator:
    build: .
    container_name: translator
    ports:
      - '8088:8088'
    restart: unless-stopped
    networks:
      - app-network

networks:
  app-network:
    external: true
```

Сам сервис был развёрнут на **Render**. 
<br>
<img width="100%" alt="Интерфейс NumberTranslatorWeb" src="https://github.com/user-attachments/assets/509abb49-c650-4160-a7e6-fc2c05842605" />
<br>
База данных была развёрнута на **freesqldatabase.com**. 
В настоящее время база данных не активна ввиду того, что бесплатный срок истёк.
<br>

## 🧪 Тестирование

- Unit-тесты репозиториев с Mockito
- Тест сервиса с моками зависимостей
- Проверка граничных случаев (0, отрицательные, дробные)

## 📁 Структура пакетов

com.example.demo/<br>
├── config/           # Конфигурация <br>
├── controller/       # NumberTranslatorController (Thymeleaf) <br>
├── model/            # Модели данных (DigitsModel, TensModel и др.) <br>
├── repository/       # JDBC-репозитории <br>
├── service/          # Бизнес-логика перевода <br>
└── resources/ <br>
&nbsp;&nbsp;&nbsp;&nbsp;├── static/       # CSS, JS (минимально) <br>
&nbsp;&nbsp;&nbsp;&nbsp;└── templates/    # main.html (Thymeleaf)

## 🚀 Быстрый старт

```bash
# 1. Клонировать репозиторий

git clone https://github.com/Egrius/NumberTranslatorWeb.git
cd NumberTranslatorWeb

# 2. Сборка с Maven
mvn clean package

# 3. Запуск Docker Compose
docker-compose up -d

# 4. Открытие в браузере
http://localhost:8088/number_translator
```

## 📬 Контакты

Если есть вопросы или предложения:
- GitHub: @Egrius
- Telegram: @egoriuuuz
