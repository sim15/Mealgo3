package com.example.mealgo3.data;

public class Nutrient {
    private String unitName;
    private Integer value;

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