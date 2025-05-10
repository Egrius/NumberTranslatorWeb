package com.example.demo.model;

public class DeclinationsModel {
    private final BaseType base;
    private final int number;
    private final String singular;
    private final String few;
    private final String many;
    private final int fraction_base_id;
    private final String fraction_base;

    public DeclinationsModel(BaseType base, int number, String singular, String few, String many, Integer fraction_base_id, String fraction_base) {
        this.base = base;
        this.number = number;
        this.singular = singular;
        this.few = few;
        this.many = many;
        this.fraction_base_id = fraction_base_id;
        this.fraction_base = fraction_base;
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

    public int getFraction_base_id() {
        return fraction_base_id;
    }

    public String getFraction_base() {
        return fraction_base;
    }
}
