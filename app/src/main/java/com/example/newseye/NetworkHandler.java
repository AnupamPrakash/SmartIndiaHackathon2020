package com.example.newseye;

public class NetworkHandler {
    public static String BASE_URL = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=";
    public static String APIKEY = "627f49c425094c8b8db5c88d1688b097";
    public String getURL()
    {
        return BASE_URL+APIKEY;
    }
}
