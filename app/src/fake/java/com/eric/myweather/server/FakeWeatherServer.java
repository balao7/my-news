package com.eric.mynews.server;

import com.eric.mynews.models.CurrentDayWeather;
import com.eric.mynews.models.Forecast;
import com.eric.mynews.models.ForecastResponse;

import java.util.ArrayList;

public class FakeWeatherServer {
    private static FakeWeatherServer theInstance;
    private ForecastResponse forecast;
    private boolean isError;

    public static FakeWeatherServer getInstance() {
        if (theInstance == null) {
            theInstance = new FakeWeatherServer();
        }
        return theInstance;
    }

    private FakeWeatherServer() {
        this.forecast = new ForecastResponse(new CurrentDayWeather(25), new Forecast(new ArrayList<>()));
        reset();
    }

    public void reset() {
        this.isError = false;
    }

    public void simulateError() {
        this.isError = true;
    }

    public ForecastResponse getForecast() {
        if (isError) { return null; }
        return forecast;
    }
}
