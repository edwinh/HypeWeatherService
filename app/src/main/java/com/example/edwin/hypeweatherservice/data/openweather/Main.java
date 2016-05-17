package com.example.edwin.hypeweatherservice.data.openweather;

import com.example.edwin.hypeweatherservice.data.JSONPopulator;

import org.json.JSONObject;

/**
 * Created by Edwin on 16-5-2016.
 */
public class Main implements JSONPopulator {
    private double tempMin;
    private double tempMax;
    private double temp;

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTemp() {
        return temp;
    }

    @Override
    public void populate(JSONObject data) {
        tempMin = data.optDouble("temp_min");
        tempMax = data.optDouble("temp_max");
        temp = data.optDouble("temp");

    }
}
