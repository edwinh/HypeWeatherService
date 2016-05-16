package com.example.edwin.hypeweatherservice.data;

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
    private int iconId;


    public int getWeatherID() {
        return weatherID;
    }

    public String getMainGroup() {
        return mainGroup;
    }

    public String getDescription() {
        return description;
    }

    public int getIconId() {
        return iconId;
    }

    @Override
    public void populate(JSONObject data) {
        weatherID = data.optInt("id");
        mainGroup = data.optString("main");
        description = data.optString("description");
        iconId = data.optInt("icon");

    }
}
