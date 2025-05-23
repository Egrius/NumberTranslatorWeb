package com.example.demo.service;

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