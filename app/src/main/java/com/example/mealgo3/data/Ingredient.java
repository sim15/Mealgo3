package com.example.mealgo3.data;

//import java.util.ArrayList;
//
//public class Ingredient {
//    public String name, description, category;
//    ArrayList<Nutrient> nutrients;
//
//    public Ingredient(String name) {
//        this.name = name;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public void addNutrient(Nutrient newNutrient) {
//        this.nutrients.add(newNutrient);
//    }
//
//}

import java.util.Map;

public class Ingredient {
   public  String foodCategory;
   public Map<String, Nutrient> foodNutrients;
   public String ingredientName;
   public String ingredients;
   public String lowercaseDescription;
   public Integer servingSize;
   public String servingSizeUnit;
   public Integer similarity;


   public Ingredient() {

   }

    public Ingredient(String foodCategory, Map<String, Nutrient> foodNutrients, String ingredientName, String ingredients, String lowercaseDescription, Integer servingSize, String servingSizeUnit, Integer similarity) {
        this.foodCategory = foodCategory;
        this.foodNutrients = foodNutrients;
        this.ingredientName = ingredientName;
        this.ingredients = ingredients;
        this.lowercaseDescription = lowercaseDescription;
        this.servingSize = servingSize;
        this.servingSizeUnit = servingSizeUnit;
        this.similarity = similarity;
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
}
