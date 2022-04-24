package com.example.mealgo3.data;

import java.util.ArrayList;

public class Recipe {
    public String name, category;
    public ArrayList<Ingredient> ingredients;
    public ArrayList<String> keywords;

    public Recipe(String name) {
        this.name = name;
    }

    public void addIngredient(Ingredient ingred) {
        this.ingredients.add(ingred);
    }
}
