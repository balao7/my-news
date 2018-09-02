package com.eric.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentDayWeather {
    @SerializedName("temp_c") @Expose private int tempC;

    public int getTempC() {
        return tempC;
    }

    public CurrentDayWeather(int tempC) {
        this.tempC = tempC;
    }
}
