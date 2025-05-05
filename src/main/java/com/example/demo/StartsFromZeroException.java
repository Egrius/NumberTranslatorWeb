package com.example.demo;


/**
 * Исключение, выбрасываемое при вводе числа, которое начинается с нуля.
 * Это исключение помогает предотвратить ошибки, где начальный ноль не допускается.
 * <p>
 * Пример использования:
 * <pre>
 * String input = "01234";
 * if (input.startsWith("0")) {
 *     throw new StartsFromZeroException("Число не может начинаться с нуля: " + input);
 * }
 * </pre>
 */
public class StartsFromZeroException extends Exception{

    /**
     * Конструктор по умолчанию для исключения StartsFromZeroException.
     */
    public StartsFromZeroException() {
        super();
    }
    
    /**
     * Конструктор исключения StartsFromZeroException с пользовательским сообщением.
     * @param msg сообщение об ошибке.
     */
    public StartsFromZeroException(String msg) {
        super(msg);
    }
}
