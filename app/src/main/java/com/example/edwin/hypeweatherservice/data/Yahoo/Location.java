package com.example.edwin.hypeweatherservice.data.yahoo;

import com.example.edwin.hypeweatherservice.data.JSONPopulator;

import org.json.JSONObject;

/**
 * Created by Edwin on 8-5-2016.
 */
public class Location implements JSONPopulator {
    private String city;
    private String country;
    private String region;


    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public void populate(JSONObject data) {
        city = data.optString("city");
        country = data.optString("country");
        region = data.optString("region");
    }
}
