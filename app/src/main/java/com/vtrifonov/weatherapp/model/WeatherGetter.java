package com.vtrifonov.weatherapp.model;

public interface WeatherGetter {
    void onWeatherLoaded(WeatherObject weatherObject);
}
