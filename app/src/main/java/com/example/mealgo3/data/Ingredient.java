package com.example.mealgo3.data;

import java.util.ArrayList;
import java.util.Map;

public class Ingredient {
    private  String foodCategory;
    private Map<String, Nutrient> foodNutrients;
    private String ingredientName;
    private String ingredients;
    private String lowercaseDescription;
    private Integer servingSize;
    private String servingSizeUnit;
    private Integer similarity;
    private ArrayList<String> substrings;


   public Ingredient() {

   }

    public Ingredient(String foodCategory, Map<String, Nutrient> foodNutrients, String ingredientName, String ingredients, String lowercaseDescription, Integer servingSize, String servingSizeUnit, Integer similarity, ArrayList<String> substrings) {
        this.foodCategory = foodCategory;
        this.foodNutrients = foodNutrients;
        this.ingredientName = ingredientName;
        this.ingredients = ingredients;
        this.lowercaseDescription = lowercaseDescription;
        this.servingSize = servingSize;
        this.servingSizeUnit = servingSizeUnit;
        this.similarity = similarity;
        this.substrings = substrings;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public Map<String, Nutrient> getFoodNutrients() {
        return foodNutrients;
    }

    public void setFoodNutrients(Map<String, Nutrient> foodNutrients) {
        this.foodNutrients = foodNutrients;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getLowercaseDescription() {
        return lowercaseDescription;
    }

    public void setLowercaseDescription(String lowercaseDescription) {
        this.lowercaseDescription = lowercaseDescription;
    }

    public Integer getServingSize() {
        return servingSize;
    }

    public void setServingSize(Integer servingSize) {
        this.servingSize = servingSize;
    }

    public String getServingSizeUnit() {
        return servingSizeUnit;
    }

    public void setServingSizeUnit(String servingSizeUnit) {
        this.servingSizeUnit = servingSizeUnit;
    }

    public Integer getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Integer similarity) {
        this.similarity = similarity;
    }

    public ArrayList<String> getSubstrings() {
        return substrings;
    }

    public void setSubstrings(ArrayList<String> substrings) {
        this.substrings = substrings;
    }
}
