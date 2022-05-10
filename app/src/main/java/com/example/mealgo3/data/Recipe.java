package com.example.mealgo3.data;

import java.util.ArrayList;
import java.util.Map;

public class Recipe {
    // TODO: Public?
    public Double AggregatedRating;
    public String CookTime;
    public ArrayList<String> Keywords;
    public String Name;
    public String PrepTime;
    public String RecipeCategory;
    public Integer RecipeId;
    public Integer ReviewCount;
    public String TotalTime;
    public ArrayList<String> images;
    public ArrayList<String> ingredients;
    public Map<String, Double> nutrition;
    public ArrayList<String> substrings;

    public Recipe(){}

    public Recipe(Double aggregatedRating, String cookTime, ArrayList<String> keywords, String name, String prepTime, String recipeCategory, Integer recipeId, Integer reviewCount, String totalTime, ArrayList<String> images, ArrayList<String> ingredients, Map<String, Double> nutrition, ArrayList<String> substrings) {
        AggregatedRating = aggregatedRating;
        CookTime = cookTime;
        Keywords = keywords;
        Name = name;
        PrepTime = prepTime;
        RecipeCategory = recipeCategory;
        RecipeId = recipeId;
        ReviewCount = reviewCount;
        TotalTime = totalTime;
        this.images = images;
        this.ingredients = ingredients;
        this.nutrition = nutrition;
        this.substrings = substrings;
    }

    public Double getAggregatedRating() {
        return AggregatedRating;
    }

    public void setAggregatedRating(Double aggregatedRating) {
        AggregatedRating = aggregatedRating;
    }

    public String getCookTime() {
        return CookTime;
    }

    public void setCookTime(String cookTime) {
        CookTime = cookTime;
    }

    public ArrayList<String> getKeywords() {
        return Keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        Keywords = keywords;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrepTime() {
        return PrepTime;
    }

    public void setPrepTime(String prepTime) {
        PrepTime = prepTime;
    }

    public String getRecipeCategory() {
        return RecipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        RecipeCategory = recipeCategory;
    }

    public Integer getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(Integer recipeId) {
        RecipeId = recipeId;
    }

    public Integer getReviewCount() {
        return ReviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        ReviewCount = reviewCount;
    }

    public String getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(String totalTime) {
        TotalTime = totalTime;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<String, Double> getNutrition() {
        return nutrition;
    }

    public void setNutrition(Map<String, Double> nutrition) {
        this.nutrition = nutrition;
    }

    public ArrayList<String> getSubstrings() {
        return substrings;
    }

    public void setSubstrings(ArrayList<String> substrings) {
        this.substrings = substrings;
    }
}