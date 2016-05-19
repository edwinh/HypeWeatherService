package com.example.edwin.hypeweatherservice;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edwin.hypeweatherservice.data.openweather.Main;
import com.example.edwin.hypeweatherservice.data.openweather.OpenWeather;
import com.example.edwin.hypeweatherservice.data.openweather.Sys;
import com.example.edwin.hypeweatherservice.data.openweather.WeatherType;
import com.example.edwin.hypeweatherservice.service.OpenWeatherService;
import com.example.edwin.hypeweatherservice.service.OpenWeatherServiceCallback;

public class MainActivity extends AppCompatActivity implements OpenWeatherServiceCallback {

    private static final String TAG = "MainActivity";

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private TextView latLongTextView;
    private OpenWeatherService service;
    private ProgressDialog dialog;
    private LocationManager lm;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        latLongTextView = (TextView) findViewById(R.id.latLongTextView);
        lm = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lm.removeUpdates(listener);
                String latlong = String.format("%f, %f", location.getLatitude(), location.getLongitude());
                latLongTextView.setText(latlong);
                service.refreshWeather(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {
                latLongTextView.setText("Provider Disabled");
            }
        };

        showWeather(null);
    }

    public void showWeather (View v) {
        final int minTime = 10;
        final int minDistance = 1;
        final Criteria criteria;

        service = new OpenWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Getting the weather for your location...");
        dialog.show();

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            dialog.hide();
            return;
        }

        if (lm != null) {
            String provider = lm.getBestProvider(criteria, true);

            // Check if location services are available
            if (provider.equals(LocationManager.PASSIVE_PROVIDER))
            {
                dialog.hide();
                Toast.makeText(this, "Turn on your location services", Toast.LENGTH_LONG).show();
                return;
            }

            Location location = lm.getLastKnownLocation(provider);
            if (location == null) {
                // There is no location at all
                lm.requestLocationUpdates(provider, minTime, minDistance, listener);
                return;
            }
            if (location.getTime()+(1*1000*60) < System.currentTimeMillis())
                // Location is older than 1 minute and should be refreshed
                lm.requestLocationUpdates(provider, minTime, minDistance, listener);

            // Retrieve weather anyway, regardless of old location
            service.refreshWeather(location);
            String latlong = String.format("%f, %f", location.getLatitude(), location.getLongitude());
            latLongTextView.setText(latlong);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    return;
                } else {
                    showWeather(null);
                }
            }
        }
    }

    @Override
    public void serviceSuccess(OpenWeather weather) {
        dialog.hide();

        Main main = weather.getMain();
        Sys sys = weather.getSys();
        WeatherType weatherType = weather.getWeatherType();

        // Determine which icon to use
        int resourceId = getResources().getIdentifier("drawable/icon_"+ weatherType.getIconId(),null,getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        if (weatherIconDrawable != null)
            weatherIconImageView.setImageDrawable(weatherIconDrawable);
        else {
            resourceId = getResources().getIdentifier("drawable/na",null,getPackageName());
            weatherIconImageView.setImageDrawable(getResources().getDrawable(resourceId, getTheme()));
        }

        String temp = String.format("%.1f\u00B0C",main.getTemp());
        temperatureTextView.setText(temp);
        conditionTextView.setText(weatherType.getDescription());
        locationTextView.setText(weather.getCity()+", "+sys.getCountry());
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
