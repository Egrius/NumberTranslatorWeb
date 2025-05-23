package com.example.demo.service;

public class StartsFromZeroException extends Exception {
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
