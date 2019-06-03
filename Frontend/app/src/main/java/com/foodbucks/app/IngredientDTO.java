package com.foodbucks.app;

import java.util.UUID;

public class IngredientDTO {
    private String unitOfMeasurement;
    private String name;
    private double quantity;
    private double cost;
    private int nrOfProductsNecessary;
    private UUID ingredientId;

    public IngredientDTO(String unitOfMeasurement, String name, double quantity, double cost, int nrOfProductsNecessary, UUID ingredientId) {
        this.unitOfMeasurement = unitOfMeasurement;
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
        this.nrOfProductsNecessary = nrOfProductsNecessary;
        this.ingredientId = ingredientId;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getNrOfProductsNecessary() {
        return nrOfProductsNecessary;
    }

    public void setNrOfProductsNecessary(int nrOfProductsNecessary) {
        this.nrOfProductsNecessary = nrOfProductsNecessary;
    }

    public UUID getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(UUID ingredientId) {
        this.ingredientId = ingredientId;
    }
}
