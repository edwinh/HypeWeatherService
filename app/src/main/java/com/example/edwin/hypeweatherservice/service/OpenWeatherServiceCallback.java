package com.example.edwin.hypeweatherservice.service;

import com.example.edwin.hypeweatherservice.data.openweather.OpenWeather;

/**
 * Created by Edwin on 7-5-2016.
 */
public interface OpenWeatherServiceCallback {
    void serviceSuccess(OpenWeather openWeather);
    void serviceFailure(Exception exception);

}
