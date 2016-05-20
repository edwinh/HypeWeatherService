package com.example.edwin.hypeweatherservice.data.openweather;

import org.json.JSONObject;

/**
 * Created by Edwin on 16-5-2016.
 */
public class OpenWeather implements JSONPopulator {
    private Main main;
    private WeatherType weatherType;
    private Sys sys;
    private String city;

    public Main getMain() {
        return main;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public Sys getSys() {
        return sys;
    }

    public String getCity() {
        return city;
    }

    @Override
    public void populate(JSONObject data) {
        main = new Main();
        main.populate(data.optJSONObject("main"));

        weatherType = new WeatherType();
        weatherType.populate(data.optJSONArray("weather").optJSONObject(0));

        sys = new Sys();
        sys.populate(data.optJSONObject("sys"));

        city = data.optString("name");


    }
}
