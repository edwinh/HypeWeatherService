package com.example.edwin.hypeweatherservice.service;

import android.location.Location;
import android.os.AsyncTask;

import com.example.edwin.hypeweatherservice.data.OpenWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Edwin on 16-5-2016.
 */
public class OpenWeatherService {
    private OpenWeatherServiceCallback callback;
    private static final String TAG = "OpenWeatherService";
    private final static String OPENWEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&APPID=%s&units=metric";
    private final static String API_KEY = "81977298a8e5e5e51ad668ae8bc0df32";
    private Location location;
    private Exception error;

    public OpenWeatherService(OpenWeatherServiceCallback callback) {
        this.callback = callback;
    }

    public void refreshWeather(Location location){
        this.location = location;

        new AsyncTask<Location, Void, String>() {
            @Override
            protected void onPostExecute(String s) {
                if (s == null ) {
                    callback.serviceFailure(error);
                    return;
                }

                try {
                    JSONObject json = new JSONObject(s);
                    OpenWeather weather = new OpenWeather();
                    weather.populate(json);

                    callback.serviceSuccess(weather);

                } catch (JSONException e) {
                    error = e;
                    callback.serviceFailure(e);
                }
            }

            @Override
            protected String doInBackground(Location... params) {
                double longitude = params[0].getLongitude();
                double latitude = params[0].getLatitude();


                String endpoint = String.format(OPENWEATHER_URL, latitude, longitude, API_KEY);

                URL url = null;
                try {
                    url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder result = new StringBuilder();
                    String line;

                    while ( (line = reader.readLine()) != null ){
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {
                    error = e;

                }
                return null;
            }
        }.execute(location);


    }
}
