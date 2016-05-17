package com.example.edwin.hypeweatherservice.data.openweather;

import com.example.edwin.hypeweatherservice.data.JSONPopulator;

import org.json.JSONObject;

/**
 * Created by Edwin on 16-5-2016.
 */
public class Sys implements JSONPopulator {
    private String country;
    private String city;

    public String getCountry() {
        return country;
    }

    @Override
    public void populate(JSONObject data) {
        country = data.optString("country");
        city = data.optString("name");

    }
}
