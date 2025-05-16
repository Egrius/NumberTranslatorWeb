package com.example.demo.service;

/**
 * Исключение, выбрасываемое при обнаружении недопустимых символов во введенном числе.
 * Используется для обработки ошибок, связанных с вводом данных, не соответствующих ожидаемому числовому формату.
 * <p>
 * Пример использования:
 * <pre>
 * if (!input.matches("\\d+")) {
 *     throw new InvalidCharException("Ошибка! Недопустимые символы!");
 * }
 * </pre>
 */
public class InvalidCharException extends Exception {

    /**
     * Конструктор по умолчанию для исключения InvalidCharException.
     */
    public InvalidCharException() {
        super();
    }

    /**
     * Конструктор исключения InvalidCharException с пользовательским сообщением.
     * @param msg сообщение об ошибке.
     */
    public InvalidCharException(String msg) {
        super(msg);
    }
}
