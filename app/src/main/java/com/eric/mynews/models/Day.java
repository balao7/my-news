package com.eric.mynews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {
    @SerializedName("avgtemp_c")
    @Expose
    private float avgtempC;

    public float getAvgtempC() {
        return avgtempC;
    }

    public Day(float avgtempC) {
        this.avgtempC = avgtempC;
    }
}
