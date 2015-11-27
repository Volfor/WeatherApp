package com.vtrifonov.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class WeatherObject extends RealmObject {
    private City city;
    @SerializedName("list")
    private RealmList<Forecast> forecasts;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public RealmList<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(RealmList<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
