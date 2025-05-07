package com.example.demo.model;

public class BaseNumberModel {
    private final int number;
    private final String numberStr;

    public BaseNumberModel(int number, String numberStr) {
        this.number = number;
        this.numberStr = numberStr;
    }

    public int getNumber() {
        return number;
    }

    public String getNumberStr() {
        return numberStr;
    }

}
