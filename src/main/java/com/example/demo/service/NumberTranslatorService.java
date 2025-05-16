package com.example.demo.service;

import com.example.demo.model.BaseType;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NumberTranslatorService {
    private final VocabularyService vocabulary;

    public NumberTranslatorService(VocabularyService vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String printNumber(String number) throws StartsFromZeroException,
                                                    StringIndexOutOfBoundsException,
                                                    InvalidCharException,
                                                    SQLException
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
                buildNumber(result, digitsInt, BaseType.FRACTIONAL);

                // Список, в котором будут храниться все цифры дробной части числа.
                List<Integer> digitsFractional = intPartToDigits(fractionalPart);
                // 'Сборка' дробной части числа.
                buildFractionalNumber(result, digitsFractional, digitsInt.getLast());
            }
            else {
                // Заполнение списка, выделенного под целую часть числа и 'сборка' целой части числа.
                digitsInt = intPartToDigits(integerPart);
                buildNumber(result, digitsInt, BaseType.INTEGER);
            }
            return result.toString().trim();

        } catch (StartsFromZeroException e) {
            throw new StartsFromZeroException();
        } catch (StringIndexOutOfBoundsException e) {
            throw new StringIndexOutOfBoundsException(e.getMessage());
        }
    }

    private void specialCase(StringBuilder result, String number) throws StartsFromZeroException {
        if (number.equals("-0") || number.equals("0")) {
            result.append("ноль");
        }
        if ((number.startsWith("-0") || number.startsWith("0")) && number.length() > 2) {
            throw new StartsFromZeroException();
        }
    }

    private String checkForNegative(String number, StringBuilder result) {
        if (number.charAt(0) == '-') {
            result.append("минус").append(" ");
            number = number.substring(1);
            return number;
        }
        return number;
    }

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

    private List<Integer> deleteLeadingZeros(List<Integer> digitsDecimal) {
        // Проверка на наличие дробной части.
        if (digitsDecimal.isEmpty()) return digitsDecimal;

        // Удаление ведущих нулей из дробной части числа.
        return digitsDecimal
                .stream()
                .dropWhile(e -> e == 0)
                .toList();
    }

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

    private void buildFractionalNumber(StringBuilder result, List<Integer> digitsDecimal, int lastIntDigit)
                                                                                            throws SQLException {
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
            buildNumber(result, digitsDecimal, BaseType.FRACTIONAL);

            // Последняя цифра дробной части числа, необходима для корректного склонения дробной части.
            int lastDigit = digitsDecimal.getLast();

            // Получение склонения с помощью метода класса Vocabulary и добавление его к результату перевода.
            String declension = vocabulary.getFractionalDeclination(lastDigit, countToPass);
            result.append(declension).append(" ");
        }
    }

    private void buildNumber(StringBuilder result, List<Integer> digits, BaseType type) throws SQLException{
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
            if(type == BaseType.INTEGER) result.append(translateIntPart(num, countToPass)).append(" ");
            else result.append(translateFractionalPart(num, countToPass)).append(" ");
        }
    }

    private String translateIntPart(int number, int countToPass) throws SQLException {
        return vocabulary.translateNumber(number, countToPass, BaseType.INTEGER);
    }


    private String translateFractionalPart(int number, int countToPass) throws SQLException{
        return vocabulary.translateNumber(number, countToPass, BaseType.FRACTIONAL);
    }
}
