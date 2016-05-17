package com.example.edwin.hypeweatherservice.data.openweather;

import com.example.edwin.hypeweatherservice.data.JSONPopulator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Edwin on 16-5-2016.
 */
public class WeatherType implements JSONPopulator {
    private int weatherID;
    private String mainGroup;
    private String description;
    private String iconId;


    public int getWeatherID() {
        return weatherID;
    }

    public String getMainGroup() {
        return mainGroup;
    }

    public String getDescription() {
        return description;
    }

    public String getIconId() {
        return iconId;
    }

    @Override
    public void populate(JSONObject data) {
        weatherID = data.optInt("id");
        mainGroup = data.optString("main");
        description = data.optString("description");
        iconId = data.optString("icon");

    }
}
