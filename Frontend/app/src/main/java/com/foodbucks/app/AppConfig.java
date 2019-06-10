package com.foodbucks.app;

public class AppConfig {

    public static String NGROK_CHANGING_STTRING = "d61130f1";
    public static String NGROK_ADDRESS = "https://"+NGROK_CHANGING_STTRING+".ngrok.io";
    public static String URL_GET_ALL_RECIPES = NGROK_ADDRESS + "/recipes/all/";
    public static String URL_POST_RECIPE = NGROK_ADDRESS + "/recipes/";
    public static String URL_GET_RECIPE_DETAILS = NGROK_ADDRESS + "/recipes/recipedetails?id=";

    public static String URL_GET_ALL_PRODUCTS = NGROK_ADDRESS + "/products/all/";
    public static String URL_POST_PRODUCT = NGROK_ADDRESS + "/products/";
    public static String URL_GET_PRODUCT_PRICES = NGROK_ADDRESS + "products/productprices?id=";

    public static String URL_GET_ALL_STORES = NGROK_ADDRESS + "/stores/all/";
    public static String URL_POST_STORE = NGROK_ADDRESS + "/stores/";
    public static String URL_GET_STORE_DETAILS = NGROK_ADDRESS + "/stores/storedetails?id=";
}
