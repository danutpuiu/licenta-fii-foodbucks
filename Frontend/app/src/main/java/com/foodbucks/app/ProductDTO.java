package com.foodbucks.app;
import java.util.UUID;

public class ProductDTO {
    private String name;
    private String brand;
    private String unitOfMeasurement;
    private String storeName;
    private double quantity;
    private double price;

    private UUID productId;

    public ProductDTO(String name, String brand, String unitOfMeasurement, String storeName, double quantity, double price, UUID productId){

        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.unitOfMeasurement = unitOfMeasurement;
        this.quantity = quantity;

        this.storeName = storeName;
        this.price = price;
    }

    public void setProductId(UUID productId){
        this.productId = productId;
    }

    public void setName(String name){
        this.name = name;
    }

    public  void setBrand(String brand){
        this.brand = brand;
    }

    public  void setUnitOfMeasurement(String unitOfMeasurement){
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public  void setStoreName(String storeName){
        this.storeName = storeName;
    }

    public void setQuantity(double quantity){
        this.quantity = quantity;
    }

    public void setPrice(double price){
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public String getStoreName() {
        return storeName;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public UUID getProductId() {
        return productId;
    }
}
