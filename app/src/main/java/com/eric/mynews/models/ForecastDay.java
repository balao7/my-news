package com.eric.mynews.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class ForecastDay {
    @SerializedName("day") @Expose private Day day;
    @SerializedName("date") @Expose private String date;

    @NonNull
    public Day getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public ForecastDay(Day day, String date) {
        this.day = day;
        this.date = date;
    }

    public String getDayOfWeek() {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(getDate());
            return new SimpleDateFormat("EEEE", Locale.US).format(date);
        } catch (ParseException e) {
            Timber.e(e);
        }
        return "";
    }
}
