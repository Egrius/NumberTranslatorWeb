package com.example.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс NumberTranslator отвечает за перевод чисел из цифрового представления в строковое.
 * Поддерживает как целые, так и дробные числа.
 * <p>
 * Примеры поддерживаемого ввода:
 * - "123.45"
 * - "-0,67"
 * <p>
 * Пример использования:
 * <pre>
 *     NumberTranslator translator = new NumberTranslator();
 *     String result = translator.printNumber("123.45");
 *     System.out.println(result); // "сто двадцать три целых сорок пять сотых"
 * </pre>
 *
 * Используемые вспомогательные классы:
 * @see Vocabulary
 * @see Declination
 */
public class NumberTranslator {

    // Переменная типа Vocabulary, необходимая для перевода числа.
    private final Vocabulary vocabulary;

     // Конструктор класса NumberTranslator и инициализация переменной типа Vocabulary
    public NumberTranslator() {
        vocabulary = new Vocabulary();
    }

    /**
     * Переводит заданное число из строкового представления в текстовое.
     * Этот метод может обрабатывать как целые, так и дробные числа, а также учитывать отрицательные значения.
     * <p>
     * Пример использования:
     * <pre>
     *     NumberTranslator translator = new NumberTranslator();
     *     String result = translator.printNumber("-123.45");
     *     System.out.println(result);
     *     // Вывод: "минус сто двадцать три целых сорок пять сотых"
     * </pre>
     *
     * @param number строка, представляющая число для перевода (например, "123.45").
     *               Может быть целым, дробным либо отрицательным числом.
     * @return строковое представление числа (например, "сто двадцать три").
     * @throws StartsFromZeroException выбрасывается, если введенное целое число начинается с недопустимого нуля.
     * @throws StringIndexOutOfBoundsException выбрасывается, если входная строка пуста.
     * @throws InvalidCharException выбрасывается, если входная строка содержит символы,
     *                              отличные от цифр, точки или запятой.
     *
     * @see #checkForNegative(String, StringBuilder)
     * @see #specialCase(StringBuilder, String)
     * @see #buildNumber(StringBuilder, List, NumberPartType)
     * @see #buildFractionalNumber(StringBuilder, List, int)
     */
    public String printNumber(String number) throws StartsFromZeroException,
                                                    StringIndexOutOfBoundsException,
                                                    InvalidCharException
    {
        // В случае, если передаваемая строка пустая, выбрасывается ошибка.
        if (number == null || number.isEmpty()) {
            throw new StringIndexOutOfBoundsException("Входная строка пуста.");
        }

        // Переменная, в которую будет записываться результат перевода.
        StringBuilder result = new StringBuilder();

        try {
            // Проверка числа на отрицательность
            number = checkForNegative(number, result);

            // Проверка ввёденных символов, в случае несоответствия выбрасывается ошибка.
            if (!number.matches("^[\\d.,]+$")) {
                throw new InvalidCharException("Ошибка! Число содержит недопустимые символы!");
            }
            // Массив, состоящий из целой и дробной части, которые отделены "." либо ",".
            String[] parts = number.split("[.,]");

            // integerPart хранит целую часть числа, decimalPart хранит дробную часть числа.
            String integerPart = parts[0];
            String fractionalPart = parts.length > 1 ? parts[1] : "";

            // Если целая часть числа пустая, то выбрасывается ошибка.
            if (integerPart.isEmpty()) {
                throw new StringIndexOutOfBoundsException("Ошибка! Целая часть не может быть пустой!");
            }
            // Проверка на случаи, когда целая часть начинается с нуля и правильный вывод -0
            specialCase(result, integerPart);

            // Список, в котором будут храниться все цифры целой части числа.
            List<Integer> digitsInt;

            // Проверка дробной части на пустоту. Если пусто, то строится только целая часть числа.
            if (!fractionalPart.isEmpty()) {

                // Заполнение списка, выделенного под целую часть числа.
                digitsInt = intPartToDigits(integerPart);
                // 'Сборка' целой части числа.
                buildNumber(result, digitsInt, NumberPartType.FRACTIONAL);

                // Список, в котором будут храниться все цифры дробной части числа.
                List<Integer> digitsFractional = intPartToDigits(fractionalPart);
                // 'Сборка' дробной части числа.
                buildFractionalNumber(result, digitsFractional, digitsInt.getLast());
            }
            else {
                // Заполнение списка, выделенного под целую часть числа и 'сборка' целой части числа.
                digitsInt = intPartToDigits(integerPart);
                buildNumber(result, digitsInt, NumberPartType.INTEGER);
            }
            return result.toString().trim();

        } catch (StartsFromZeroException e) {
            throw new StartsFromZeroException();
        } catch (StringIndexOutOfBoundsException e) {
            throw new StringIndexOutOfBoundsException(e.getMessage());
        }
    }

    /**
     * Функция проверяет, начинается ли число с недопустимого нуля, и выбрасывает исключение, если это так.
     * <p>
     * Пример:
     * <pre>
     *     StringBuilder result = new StringBuilder();
     *     String number = "00123";
     *     specialCase(result, number); // выбрасывает исключение StartsFromZeroException
     * </pre>
     *
     * @param result строка, в которую записывается результат перевода.
     * @param number строка введенного числа.
     * @throws StartsFromZeroException выбрасывается, если число начинается с нуля.
     */
    private void specialCase(StringBuilder result, String number) throws StartsFromZeroException {
        if (number.equals("-0") || number.equals("0")) {
            result.append("ноль");
        }
        if ((number.startsWith("-0") || number.startsWith("0")) && number.length() > 2) {
            throw new StartsFromZeroException();
        }
    }

    /**
     * Проверяет число на отрицательность, в случае отрицательности добавляет "минус"
     * к результату перевода.
     *
     * @param number - введенное пользователем число.
     * @param result - строка, в которую записывается результат перевода.
     * @return - число введенное пользователем, не содержащее знака "-",
     * если он был изначально, то к результату перевода добавляется "минус".
     */
    private String checkForNegative(String number, StringBuilder result) {
        if (number.charAt(0) == '-') {
            result.append("минус").append(" ");
            number = number.substring(1);
            return number;
        }
        return number;
    }

    /**
     * Разбивает целую часть числа на цифры и возвращает список этих цифр.
     * <p>
     * Пример:
     * <pre>
     *     intPartToDigits("123.45"); // вернет [1, 2, 3]
     *     intPartToDigits("00123"); // вернет [0, 0, 1, 2, 3]
     * </pre>
     *
     * Исключительные случаи:
     * - Если число содержит только дробную часть (например, ".45"), метод вернет пустой список.
     *
     * @param number строковое представление числа.
     * @return список цифр целой части числа.
     */
    private List<Integer> intPartToDigits(String number) {
        // Возвращаемый список типа Integer, в котором будут храниться цифры числа.
        List<Integer> digits = new ArrayList<>();

        // Проход по целой части введенного числа.
        for (int i = 0; i < number.length(); i++) {
            // В случае обнаружения разделителя целой части от дробной, возвращается список целой части.
            if(number.charAt(i) == '.' || number.charAt(i) == ',') {
                return digits;
            }
            // Добавление цифр в список.
            digits.add(Character.getNumericValue(number.charAt(i)));
        }
        // При отсутствии разделителя число возвращается в виде списка всех его цифр и считается целым.
        return digits;
    }

    /**
     * Удаляет ведущие нули из дробной части числа.
     *
     * @param digitsDecimal - список цифр дробной части числа.
     * @return - список цифр без ведущих нулей.
     */
    private List<Integer> deleteLeadingZeros(List<Integer> digitsDecimal) {
        // Проверка на наличие дробной части.
        if (digitsDecimal.isEmpty()) return digitsDecimal;

        // Удаление ведущих нулей из дробной части числа.
        return digitsDecimal
                .stream()
                .dropWhile(e -> e == 0)
                .toList();
    }

    /**
     * Удаляет завершающие нули из дробной части числа.
     *
     * @param digitsDecimal список цифр дробной части числа.
     * @return список цифр без завершающих нулей.
     */
    private List<Integer> deleteEndZeros(List<Integer> digitsDecimal) {
        // Проверка на наличие дробной части
        if (digitsDecimal.isEmpty()) return digitsDecimal;

        // Переменная для хранения индекса последнего ненулевого элемента.
        int lastNonZeroIndex = digitsDecimal.size() - 1;

        // Поиск последнего ненулевого элемента.
        while (lastNonZeroIndex >= 0 && digitsDecimal.get(lastNonZeroIndex) == 0) {
            lastNonZeroIndex--;
        }

        // Удаление завершающих нулей из дробной части числа.
        return digitsDecimal.stream()
                .limit(lastNonZeroIndex + 1)
                .toList();
    }

    /**
     * Собирает дробную часть числа, удаляет лишние нули, переводит цифры в строковое представление
     * и добавляет склонения.
     * <p>
     * Метод использует вспомогательные методы для удаления ведущих и завершающих нулей:
     * @see #deleteLeadingZeros(List)
     * @see #deleteEndZeros(List)
     *
     * Также применяется метод для перевода чисел:
     * @see Vocabulary#translateNumber(int, int, NumberPartType)
     *
     * @param result строка, в которой хранится конечный результат перевода числа.
     * @param digitsDecimal список цифр дробной части.
     * @param lastIntDigit последняя цифра целой части числа, необходимая для корректного склонения.
     */
    private void buildFractionalNumber(StringBuilder result, List<Integer> digitsDecimal, int lastIntDigit) {
        // Удаление завершающих нулей дробной части, для корректного определения разрядности
        digitsDecimal = deleteEndZeros(digitsDecimal);

        // Параметр, отвечающий за разрядность дробного числа и определяющий корректный перевод.
        int countToPass = digitsDecimal.size();

        // Удаление ведущих нулей числа, необходимо для корректного перевода числа.
        digitsDecimal = deleteLeadingZeros(digitsDecimal);

        // Проверка на наличие дробной части
        if (countToPass > 0) {
            // Проверка последней цифры дробной части и добавление соответствующего окончания
            if(lastIntDigit == 1) result.append("целая").append(" ");
            else  result.append("целых").append(" ");

            // Перевод числа, содержащегося в дробной части.
            buildNumber(result, digitsDecimal, NumberPartType.FRACTIONAL);

            // Последняя цифра дробной части числа, необходима для корректного склонения дробной части.
            int lastDigit = digitsDecimal.getLast();

            // Получение склонения с помощью метода класса Vocabulary и добавление его к результату перевода.
            String declension = vocabulary.getFractionalDeclination(lastDigit, countToPass);
            result.append(declension).append(" ");
        }
    }

    /**
     * Сборка строкового представления частей числа (целые и дробные).
     * Метод используется для преобразования последовательности цифр в строковый вид
     * с учетом их принадлежности к целой или дробной части числа.
     * <p>
     * Пример:
     * <pre>
     *     List<Integer> digits = List.of(1, 2, 3);
     *     buildNumber(result, digits, NumberPartType.INTEGER);
     *     // Результат: "сто двадцать три"
     * </pre>
     *
     * @param result строка, в которую добавляется результат перевода числа.
     * @param digits список цифр, представляющих часть числа (целую или дробную).
     * @param type тип числа (целая часть или дробная часть), определяется перечислением {@link NumberPartType}.
     * @see #translateIntPart(int, int)
     * @see #translateFractionalPart(int, int)
     */
    private void buildNumber(StringBuilder result, List<Integer> digits, NumberPartType type) {
        int count = digits.size();
        int i = 0;

        while (i < count) {
            int num = 0;
            int countToPass = count - i;

            if (countToPass % 3 == 0) {
                num += digits.get(i) * 100 + digits.get(i + 1) * 10 + digits.get(i + 2);
                i += 3;

            } else if (countToPass % 3 == 2) {
                num += digits.get(i) * 10 + digits.get(i + 1);
                i += 2;

            } else {
                num += digits.get(i);
                i++;
            }

            // Условие для обработки целой или дробной части числа
            if(type == NumberPartType.INTEGER) result.append(translateIntPart(num, countToPass)).append(" ");
            else result.append(translateFractionalPart(num, countToPass)).append(" ");
        }
    }

    /**
     * Переводит целую часть числа в строковое представление.
     *
     * @param number часть числа (например, сотни, десятки или единицы).
     * @param countToPass разрядность передаваемой части числа.
     * @return строковое представление переданной части числа. Например: "сто", "одиннадцать".
     */
    private String translateIntPart(int number, int countToPass) {
        return vocabulary.translateNumber(number, countToPass, NumberPartType.INTEGER);
    }

    /**
     * Переводит дробную часть числа и записывает её в строку результата.
     *
     * @param number - часть дробной части числа? используя метод translateNumber класса Vocabulary.
     * @param countToPass - разрядность передаваемой части числа.
     * @return - перевод части дробного числа.
     * @see Vocabulary
     */
    private String translateFractionalPart(int number, int countToPass) {
        return vocabulary.translateNumber(number, countToPass, NumberPartType.FRACTIONAL);
    }
}