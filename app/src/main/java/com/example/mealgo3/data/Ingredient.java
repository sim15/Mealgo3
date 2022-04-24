package com.example.mealgo3.data;

import java.util.ArrayList;

public class Ingredient {
    public String name, description, category;
    ArrayList<Nutrient> nutrients;

    public Ingredient(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void addNutrient(Nutrient newNutrient) {
        this.nutrients.add(newNutrient);
    }

}
