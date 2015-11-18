package com.vtrifonov.weatherapp.model;

import java.util.ArrayList;

public class Forecast {
    private long dt;
    private WeatherConditions main;
    private ArrayList<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private Rain rain;
    private Snow snow;

    class WeatherConditions {
        private float temp;
        private float temp_min;
        private float temp_max;
        private float pressure;
        private float humidity;
    }

    class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    class Clouds {
        private float all;
    }

    class Wind {
        private float speed;
        private float deg;
    }

    class Rain {
        private float _3h;
    }

    class Snow {
        private float _3h;
    }

    public long getDate() {
        return dt;
    }

    public float getTemperature() {
        return main.temp;
    }

    public String getWeatherIcon(int i) {
        return weather.get(i).icon;
    }

    public String getWeatherDescription(int i) {
        return weather.get(i).description;
    }

}
