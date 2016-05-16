package com.example.edwin.hypeweatherservice.service;

import com.example.edwin.hypeweatherservice.data.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Edwin on 7-5-2016.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);

}
