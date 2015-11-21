package com.vtrifonov.weatherapp.model;

import com.google.gson.annotations.SerializedName;

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
        @SerializedName("3h")
        private float _3h;
    }

    class Snow {
        @SerializedName("3h")
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

    public float getMinTemerature() {
        return main.temp_min;
    }

    public float getMaxTemerature() {
        return main.temp_max;
    }

    public float getPressure() {
        return main.pressure;
    }

    public float getHumidity() {
        return main.humidity;
    }

    public float getClouds() {
        return clouds.all;
    }

    public float getWindSpeed() {
        return wind.speed;
    }

    public float getWindDirection() {
        return wind.deg;
    }

    public float getRain() {
        if (rain != null) {
            return rain._3h;
        } else {
            return -1;
        }
    }

    public float getSnow() {
        if (snow != null) {
            return snow._3h;
        } else {
            return -1;
        }
    }

    public String getWeatherMain(int i) {
        return weather.get(i).main;
    }

}
