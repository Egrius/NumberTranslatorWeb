package com.example.demo.model;

public class DeclinationsModel {
    private final BaseType base;
    private final int number;
    private final String singular;
    private final String few;
    private final String many;

    public DeclinationsModel(BaseType base, int number, String singular, String few, String many) {
        this.base = base;
        this.number = number;
        this.singular = singular;
        this.few = few;
        this.many = many;
    }

    public BaseType getBase() {
        return base;
    }

    public int getNumber() {
        return number;
    }

    public String getSingular() {
        return singular;
    }

    public String getFew() {
        return few;
    }

    public String getMany() {
        return many;
    }
}
