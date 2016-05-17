package com.example.edwin.hypeweatherservice.data.yahoo;

import com.example.edwin.hypeweatherservice.data.JSONPopulator;

import org.json.JSONObject;

/**
 * Created by Edwin on 7-5-2016.
 */
public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String description;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        description = data.optString("text");

    }
}
