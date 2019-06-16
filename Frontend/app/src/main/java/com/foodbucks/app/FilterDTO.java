package com.foodbucks.app;

public class FilterDTO {

    String name;
    double cost;
    double rating;
    int votestNumber;
    int cookingTime;
    String includedProducts;
    String includingBrands;
    String onlyStores;

    public FilterDTO(String name, double cost, double rating, int votestNumber, int cookingTime, String includedProducts, String includingBrands, String onlyStores) {
        this.name = name;
        this.cost = cost;
        this.rating = rating;
        this.votestNumber = votestNumber;
        this.cookingTime = cookingTime;
        this.includedProducts = includedProducts;
        this.includingBrands = includingBrands;
        this.onlyStores = onlyStores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getVotestNumber() {
        return votestNumber;
    }

    public void setVotestNumber(int votestNumber) {
        this.votestNumber = votestNumber;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getIncludedProducts() {
        return includedProducts;
    }

    public void setIncludedProducts(String includedProducts) {
        this.includedProducts = includedProducts;
    }

    public String getIncludingBrands() {
        return includingBrands;
    }

    public void setIncludingBrands(String includingBrands) {
        this.includingBrands = includingBrands;
    }

    public String getOnlyStores() {
        return onlyStores;
    }

    public void setOnlyStores(String onlyStores) {
        this.onlyStores = onlyStores;
    }


}
