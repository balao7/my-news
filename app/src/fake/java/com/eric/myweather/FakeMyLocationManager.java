package com.eric.mynews;

import android.location.Location;

import io.reactivex.Observable;

public class FakeMyLocationManager extends MyLocationManager {
    FakeMyLocationManager() {

    }

    public Observable<Location> observeLocation() {
        Location dummyLocation = new Location("dummy");
        dummyLocation.setLatitude(1.352083);
        dummyLocation.setLongitude(103.819836);
        return Observable.just(dummyLocation);
    }
}
