package com.example.edwin.hypeweatherservice.service;

import com.example.edwin.hypeweatherservice.data.yahoo.Channel;

/**
 * Created by Edwin on 7-5-2016.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);

}
