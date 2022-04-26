package com.example.mealgo3.data;

//public class Nutrient {
//    public String name;
//    public Double quantityGrams;
//
//    public Nutrient(String name, String unit, Double quantity) {
//        this.name = name;
////        this.unit = unit;
//        // TODO: Fix to convert to grams.
//        this.quantityGrams = quantity;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Double getQuantity() {
//        return quantityGrams;
//    }
//
//}

public class Nutrient {
    public String unitName;
    public Integer value;

    public Nutrient () {

    }

    public Nutrient(String unitName, Integer value) {
        this.unitName = unitName;
        this.value = value;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}