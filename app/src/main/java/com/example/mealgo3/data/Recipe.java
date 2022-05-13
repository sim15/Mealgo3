package com.example.mealgo3.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Recipe implements Serializable {
    // TODO: Public?
    private Double AggregatedRating;
    private String CookTime;
    private ArrayList<String> Keywords;
    private String Name;
    private String PrepTime;
    private String RecipeCategory;
    private Integer RecipeId;
    private Integer ReviewCount;
    private String TotalTime;
    private ArrayList<String> images;
    private List<String> ingredients;
    private Object nutrition;
    private ArrayList<String> substrings;


    public Recipe() {

    }

    public Recipe(Double aggregatedRating, String cookTime, ArrayList<String> keywords, String name, String prepTime, String recipeCategory, Integer recipeId, Integer reviewCount, String totalTime, ArrayList<String> images, List<String> ingredients, Object nutrition, ArrayList<String> substrings) {
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

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Object getNutrition() {
        return nutrition;
    }

    public void setNutrition(Object nutrition) {
        this.nutrition = nutrition;
    }

    public ArrayList<String> getSubstrings() {
        return substrings;
    }

    public void setSubstrings(ArrayList<String> substrings) {
        this.substrings = substrings;
    }
}