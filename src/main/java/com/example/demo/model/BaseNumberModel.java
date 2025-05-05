package com.example.demo.model;

public class BaseNumberModel {
    private final int number;
    private final String numberSstr;

    public BaseNumberModel(int number, String numberSstr) {
        this.number = number;
        this.numberSstr = numberSstr;
    }

    public int getNumber() {
        return number;
    }

    public String getNumberSstr() {
        return numberSstr;
    }
}
