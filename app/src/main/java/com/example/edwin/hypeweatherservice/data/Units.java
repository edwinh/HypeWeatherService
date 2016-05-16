package com.example.edwin.hypeweatherservice.data;

import org.json.JSONObject;

/**
 * Created by Edwin on 7-5-2016.
 */
public class Units implements JSONPopulator {

    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");

    }
}
