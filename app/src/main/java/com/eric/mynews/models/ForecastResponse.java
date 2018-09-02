package com.eric.mynews.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastResponse {
    @SerializedName("current") @Expose private CurrentDayWeather current;
    @SerializedName("forecast") @Expose private Forecast forecast;

    @NonNull
    public CurrentDayWeather getCurrent() {
        return current;
    }

    @NonNull
    public Forecast getForecast() {
        return forecast;
    }

    public ForecastResponse(CurrentDayWeather current, Forecast forecast) {
        this.current = current;
        this.forecast = forecast;
    }
}
