package com.example.edwin.hypeweatherservice;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edwin.hypeweatherservice.data.Main;
import com.example.edwin.hypeweatherservice.data.OpenWeather;
import com.example.edwin.hypeweatherservice.data.Sys;
import com.example.edwin.hypeweatherservice.data.WeatherType;
import com.example.edwin.hypeweatherservice.service.OpenWeatherService;
import com.example.edwin.hypeweatherservice.service.OpenWeatherServiceCallback;

public class MainActivity extends AppCompatActivity implements OpenWeatherServiceCallback {

    private static final String TAG = "MainActivity";

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private TextView cityEditTextView;
    private Button showWeatherButton;

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
        cityEditTextView = (TextView) findViewById(R.id.cityTextView);
        showWeatherButton = (Button) findViewById(R.id.showWeatherButton);
        lm = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                cityEditTextView.setText(location.getLatitude()+ "," + location.getLongitude());
                lm.removeUpdates(this);
                service.refreshWeather(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {
                cityEditTextView.setText("Just a moment, locating...");
            }
            @Override
            public void onProviderDisabled(String provider) {
                cityEditTextView.setText("Provider Disabled");
            }
        };

    }
    private void retrieveWeather() {

        final int minTime = 10;
        final int minDistance = 1;
        final Criteria criteria;

        service = new OpenWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        if (lm != null) {
            String provider = lm.getBestProvider(criteria, true);
            if (provider.equals(LocationManager.PASSIVE_PROVIDER))
            {
                dialog.hide();
                cityEditTextView.setText("Turn on your location services");
                lm.removeUpdates(listener);
                return;
            }
            lm.requestLocationUpdates(provider, minTime, minDistance, listener);
            Location location = lm.getLastKnownLocation(provider);
            if (location == null ) {
                cityEditTextView.setText("Just a moment, locating...");
                return;
            }
            String latlong = String.format("%f, %f", location.getLatitude(), location.getLongitude());
            cityEditTextView.setText(latlong);
            service.refreshWeather(location);
            lm.removeUpdates(listener);
        }
    }


    //public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {}

    @Override
    public void serviceSuccess(OpenWeather weather) {
        dialog.hide();

        Main main = weather.getMain();
        Sys sys = weather.getSys();
        WeatherType weatherType = weather.getWeatherType();

        int resourceId = getResources().getIdentifier("drawable/icon_"+ weatherType.getIconId(),null,getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(main.getTempMax()+"\u00B0"+"C");
        conditionTextView.setText(weatherType.getDescription());
        locationTextView.setText(weather.getCity()+", "+sys.getCountry());
    }

    public void showWeather (View v) {
        retrieveWeather();
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
