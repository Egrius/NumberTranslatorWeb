package com.example.demo;

/**
 * Класс предназначен для обработки склонений числовых значений на русском языке.
 * Поддерживает склонения для целых чисел и дробных частей, включая большие числа (тысячи, миллионы и т.д.).
 * <p>
 * Пример использования:
 * <pre>
 * Declination declination = new Declination();
 * String result = declination.getIntDeclination(1, 4);
 * System.out.println(result); // вывод: "тысяча"
 * </pre>
 */
public class Declination {

    /**
     * Конструктор класса Declination.
     */
    public Declination() { }

    /**
     * Массив строковых основ для дробных частей.
     * Используется для склонения дробных частей чисел в зависимости от разряда.
     */
    private static final String[] fractionalBases = {
            "десят", "сот", "тысячн", "десятитысячн", "стотысячн", "миллионн",
            "десятимиллионн", "стомиллионн", "триллион", "десятитриллионн", "стотриллионн",
            "триллионн", "десятитриллионн", "стотриллионн"
    };

    /**
     * Определяет и возвращает необходимое склонение для целой части числа.
     *
     * @param number числовое значение (1 - единственное число, 2-4 - несколько, остальные - множественное число).
     * @param singular форма для единственного числа.
     * @param few форма для нескольких.
     * @param many форма для множественного числа.
     * @return строковое представление склонения.
     */
    private String getBigIntNumberDeclination(int number, String singular, String few, String many) {
        if (number == 1) return singular;
        else if (number >= 2 && number <= 4) return few;
        else return many;
    }


    /**
     * Возвращает склонение для целой части числа.
     *
     * @param number числовое значение.
     * @param count  разряд числа (4-6 для тысяч, 7-9 для миллионов и т.д.).
     * @return строковое представление склонения.
     */
    public String getIntDeclination (int number, int count) {
        if (count >= 4 && count <= 6) {
            return getBigIntNumberDeclination(number, "тысяча", "тысячи", "тысяч");
        } else if (count >= 7 && count <= 9) {
            return getBigIntNumberDeclination(number, "миллион", "миллиона", "миллионов");
        } else if (count >= 10 && count <= 12) {
            return getBigIntNumberDeclination(number, "миллиард", "миллиарда", "миллиардов");
        } else if (count >= 13 && count <= 15) {
            return getBigIntNumberDeclination(number, "триллион", "триллиона", "триллионов");
        }
        else return "";
    }

    /**
     * Возвращает окончание для дробной части числа.
     *
     * @param number числовое значение.
     * @param singularEnding окончание для единственного числа.
     * @param pluralEnding окончание для множественного числа.
     * @return строковое представление окончания.
     */
    private String getFractionalEnding(int number, String singularEnding, String pluralEnding) {
        return (number == 1) ? singularEnding : pluralEnding;
    }


    /**
     * Возвращает склонение для дробной части числа.
     *
     * @param number числовое значение.
     * @param count количество разрядов для дробной части.
     * @return дробная часть числа с правильным склонением.
     */
    public String getFractionalDeclination (int number, int count) {
        String base = fractionalBases[count - 1];
        String ending = getFractionalEnding(number, "ая", "ых");
        return base + ending;
    }
}