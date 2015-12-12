package com.vtrifonov.weatherapp.interfaces;

import com.vtrifonov.weatherapp.model.Forecast;

import java.util.ArrayList;

public interface WeatherGetter {
    void onWeatherLoaded(ArrayList<Forecast> forecasts);
}
