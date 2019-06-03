package com.foodbucks.app;

import java.util.List;
import java.util.UUID;

public class RecipeDTO {
    UUID recipeId;

    private String name;
    private String description;
    private List<IngredientDTO> ingredients;
    private List<InstructionStepDTO> instructionsSteps;
    private int servings;
    private int calories;
    private int cookingTime;
    private int likes;
    private int votes;
    private int cost;
    private int rating;

    public RecipeDTO(UUID recipeId, String name, String description, List<IngredientDTO> ingredients,
                     List<InstructionStepDTO> instructionsSteps, int servings, int calories,
                     int cookingTime, int likes, int votes, int cost, int rating) {
        this.recipeId = recipeId;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructionsSteps = instructionsSteps;
        this.servings = servings;
        this.calories = calories;
        this.cookingTime = cookingTime;
        this.likes = likes;
        this.votes = votes;
        this.cost = cost;
        this.rating = rating;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(UUID recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public List<InstructionStepDTO> getInstructionsSteps() {
        return instructionsSteps;
    }

    public void setInstructionsSteps(List<InstructionStepDTO> instructionsSteps) {
        this.instructionsSteps = instructionsSteps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
