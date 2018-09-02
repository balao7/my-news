package com.eric.mynews.repositories;

import android.support.annotation.NonNull;

import com.eric.mynews.models.ForecastResponse;
import com.eric.mynews.server.FakeWeatherServer;

import io.reactivex.Single;

public class FakeWeatherRepoImpl implements WeatherRepository {
    @Override
    public Single<ForecastResponse> getForecast(@NonNull String location, int numDays) {
        return Single.just(FakeWeatherServer.getInstance()
                .getForecast());
    }
}
