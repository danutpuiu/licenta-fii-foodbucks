package com.foodbucks.app;

import java.util.List;

public class FilterDTO {

    String name;
    double cost;
    double rating;
    int votestNumber;
    int cookingTime;
    List<String> includedProducts;
    List<String> includingBrands;
    List<String> onlyStores;

    public FilterDTO(String name, double cost, double rating, int votestNumber, int cookingTime, List<String> includedProducts, List<String> includingBrands, List<String> onlyStores) {
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

    public List<String> getIncludedProducts() {
        return includedProducts;
    }

    public void setIncludedProducts(List<String> includedProducts) {
        this.includedProducts = includedProducts;
    }

    public List<String> getIncludingBrands() {
        return includingBrands;
    }

    public void setIncludingBrands(List<String> includingBrands) {
        this.includingBrands = includingBrands;
    }

    public List<String> getOnlyStores() {
        return onlyStores;
    }

    public void setOnlyStores(List<String> onlyStores) {
        this.onlyStores = onlyStores;
    }


}
