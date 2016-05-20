package com.example.edwin.hypeweatherservice;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Class that implements
 */
public class MyLocationListener implements LocationListener {

    private MyLocationListenerCallback callback;

    public MyLocationListener(MyLocationListenerCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onLocationChanged(Location location) {
        callback.LocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
