package com.vtrifonov.weatherapp;

import com.vtrifonov.weatherapp.model.WeatherObject;

public interface WeatherGetter {
    void onWeatherLoaded(WeatherObject weatherObject);
}
