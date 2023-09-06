package com.finalproject.secondhand.enums;

public enum CategoryList {

    Hobi("Hobi"),
    Kendaraan("Kendaraan"),
    Properti("Properti"),
    Elektronik("Elektronik"),
    Pakaian("Pakaian");

    private final String name;

    CategoryList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


