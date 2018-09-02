package com.eric.mynews;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import timber.log.Timber;

public class MyLocationManager {
    private LocationManager locationManager;

    public MyLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public MyLocationManager() {}

    @SuppressLint("MissingPermission")
    public Observable<Location> observeLocation() {
        return Flowable.create(new FlowableOnSubscribe<Location>() {
            @Override
            public void subscribe(FlowableEmitter<Location> emitter) throws Exception {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) { emitter.onNext(location); }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Timber.i("onLocationChanged %s", location);
                        emitter.onNext(location);
                        locationManager.removeUpdates(this);
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {
                        emitter.onError(new UnknownError("Location provider disabled"));
                    }
                });
            }
        }, BackpressureStrategy.LATEST)
                .toObservable();
    }
}
