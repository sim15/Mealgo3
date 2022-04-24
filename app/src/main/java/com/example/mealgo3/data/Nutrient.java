package com.example.mealgo3.data;

public class Nutrient {
    public String name;
    public Double quantityGrams;

    public Nutrient(String name, String unit, Double quantity) {
        this.name = name;
//        this.unit = unit;
        // TODO: Fix to convert to grams.
        this.quantityGrams = quantity;
    }

    public String getName() {
        return name;
    }

    public Double getQuantity() {
        return quantityGrams;
    }

}
