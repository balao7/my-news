package com.eric.mynews.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast {
    @NonNull @SerializedName("forecastday") @Expose private List<ForecastDay> forecastday = new ArrayList<>();

    @NonNull
    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    public Forecast(@NonNull List<ForecastDay> forecastday) {
        this.forecastday = forecastday;
    }
}
