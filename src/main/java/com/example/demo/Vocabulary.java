package com.example.demo;

//import java.util.Map;

/**
 * Класс предназначен для перевода чисел в строковое представление на русском языке.
 * <p>
 * Поддерживает единицы, десятки, сотни, а также склонения дробных частей.
 * <p>
 * Пример использования:
 * <pre>
 * Vocabulary vocabulary = new Vocabulary();
 * String result = vocabulary.translateNumber(123, 1, NumberPartType.INTEGER);
 * System.out.println(result); // вывод: "сто двадцать три"
 * </pre>
 */
/*
public class Vocabulary {
    /**
     * Карта чисел от 0 до 9 и их строковых представлений.
     */
/*
    private final Map<Integer, String> digits = Map.ofEntries(
            Map.entry(1, "один"),
            Map.entry(2, "два"),
            Map.entry(3, "три"),
            Map.entry(4, "четыре"),
            Map.entry(5, "пять"),
            Map.entry(6, "шесть"),
            Map.entry(7, "семь"),
            Map.entry(8, "восемь"),
            Map.entry(9, "девять")
    );
    */
    /**
     * Карта чисел от 10 до 19 и их строковых представлений.
     */
    /*
    private final Map<Integer, String> numbers = Map.ofEntries(
            Map.entry(10, "десять"),
            Map.entry(11, "одиннадцать"),
            Map.entry(12, "двенадцать"),
            Map.entry(13, "тринадцать"),
            Map.entry(14, "четырнадцать"),
            Map.entry(15, "пятнадцать"),
            Map.entry(16, "шестнадцать"),
            Map.entry(17, "семнадцать"),
            Map.entry(18, "восемнадцать"),
            Map.entry(19, "девятнадцать")
    );
    */
    /**
     * Карта десятков от 10 до 90 и их строковых представлений.
     */
    /*
    private final Map<Integer, String> tens = Map.ofEntries(
            Map.entry(1, "десять"),
            Map.entry(2, "двадцать"),
            Map.entry(3, "тридцать"),
            Map.entry(4, "сорок"),
            Map.entry(5, "пятьдесят"),
            Map.entry(6, "шестьдесят"),
            Map.entry(7, "семьдесят"),
            Map.entry(8, "восемьдесят"),
            Map.entry(9, "девяносто")
    );
    */
    /**
     * Карта сотен от 100 до 900 и их строковых представлений.
     */
    /*
    private final Map<Integer, String> hundreds = Map.ofEntries(
            Map.entry(1, "сто"),
            Map.entry(2, "двести"),
            Map.entry(3, "триста"),
            Map.entry(4, "четыреста"),
            Map.entry(5, "пятьсот"),
            Map.entry(6, "шестьсот"),
            Map.entry(7, "семьсот"),
            Map.entry(8, "восемьсот"),
            Map.entry(9, "девятьсот")
    );
*/
    /**
     * Переменная типа {@link Declination}, предназначенная для корректного склонения числа
     */
    /*
    private final Declination declination = new Declination();
*/
    /**
     * Конструктор класса
     */
    //public Vocabulary() { }

    /**
     * Переводит числовое значение в строковое представление с учётом склонений.
     * Число разбивается на группы трехзначных чисел, которые представляют разряд сотен, десятков и единиц.
     * <p>
     * Пример:
     * <pre>
     * Vocabulary vocabulary = new Vocabulary();
     * String result = vocabulary.translateNumber(123, 2, NumberPartType.FRACTIONAL);
     * System.out.println(result); // вывод: "сто двадцать три"
     * </pre>
     *
     * @param number часть числа для перевода (максимум можно передавать трехзначное число).
     * @param count разряд передаваемой группы чисел.
     * @param type тип числа (например, целое или дробное) {@link NumberPartType}.
     * @return строковое представление числа с учётом склонения.
     */
    /*
    protected String translateNumber(int number, int count, NumberPartType type) {
        // Переменная, для хранения числа сотен. Пример: 123, hundredsPart = 1.
        int hundredsPart = number / 100;
        // Переменная для хранения числа десятков и единиц. Пример: 123, tensAndUnits = 23.
        int tensAndUnits = number % 100;
        // Переменная для хранения числа десятков. Пример: 123, tensPart = 2.
        int tensPart = tensAndUnits / 10;
        // Переменная для хранения числа единиц. Пример: 123, tensPart = 2.
        int unitsPart  = tensAndUnits % 10;

        // Временная переменная для хранения результата перевода
        String result = "";

        // Проверка на наличие сотен, при наличии сотен к результату добавляется соответствующий переод
        if(hundredsPart != 0) {
            result += hundreds.get(hundredsPart) + " ";
        }

        /*
         * Проверка исключительной ситуации (числа от 10 до 19 нельзя собрать по частям).
         * Иначе число собирается по частям. Пример: 21 -> двадцать + один.
         */
/*
        if (tensAndUnits >= 10 && tensAndUnits <= 19){
            result += numbers.get(tensAndUnits) + " " + declination.getIntDeclination(tensAndUnits, count);
            return result.trim();

        } else {
            // Дополнительная проверка, что число, представляющее количество десятков не указывает на 10.
            if (tensPart >= 2) {
                result += tens.get(tensPart) + " ";
            }
            if (unitsPart != 0) {
                // Проверка числа, представляющего разряд числа. В зависимости от этого определяется результат.
                switch (count) {
                    case 1,2,3:
                        // Проверка принадлежность передаваемой части к дробной части числа.
                        if(type == NumberPartType.FRACTIONAL) {
                            if(unitsPart == 1) result += "одна";
                            else if(unitsPart == 2) result += "две";
                            else  result += digits.get(unitsPart);
                            return result.trim();
                        }

                        result += digits.get(unitsPart);
                        return result.trim();

                    case 4, 5, 6:
                        /*
                         * Случай, где передаваемая часть числа представляет разряд тысяч.
                         * Выполняется соответсвующий перевод.
                         */
/*
                        if (unitsPart == 1) result += "одна" + " ";
                        else if (unitsPart == 2) result += "две" + " ";
                        else result += digits.get(unitsPart) + " ";
                        break;

                    default:
                        result += digits.get(unitsPart) + " ";
                        break;
                }
            }
        }
        //Запись в строку результата
        result += declination.getIntDeclination(unitsPart != 0 ? unitsPart : tensAndUnits, count);
        return result.trim();
    }

    /**
     * Возвращает склонение дробной части числа.
     *
     * @param lastDigit последняя цифра дробной части.
     * @param countToPass количество разрядов для склонения.
     * @return строковое представление дробной части числа с корректным склонением.
     */
    /*
    protected String getFractionalDeclination(int lastDigit, int countToPass) {
        return declination.getFractionalDeclination(lastDigit, countToPass);
    }
}
*/