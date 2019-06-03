package com.foodbucks.app;

import java.util.UUID;

public class StoreDTO {
    private String name;
    private String description;
    private String website;
    private UUID storeId;


    public StoreDTO(String name, String description, String website, UUID storeId) {
        this.name = name;
        this.description = description;
        this.website = website;
        this.storeId = storeId;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }


}
