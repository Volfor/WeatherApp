package com.vtrifonov.weatherapp.model;

public class WeatherSingleton implements WeatherGetter {
    private static volatile WeatherSingleton instance;
    private WeatherObject weatherObject;
    private boolean weatherAvailable = false;

    public static WeatherSingleton getInstance() {
        if (instance == null) {
            instance = new WeatherSingleton();
        }
        return instance;
    }

    public WeatherObject getWeather() {
        return weatherObject;
    }

    public boolean weatherAvailable(){
        return weatherAvailable;
    }

    @Override
    public void onWeatherLoaded(WeatherObject weatherObject) {
        this.weatherObject = weatherObject;
        weatherAvailable = true;
    }

}
