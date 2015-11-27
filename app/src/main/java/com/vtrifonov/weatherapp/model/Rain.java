package com.vtrifonov.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Rain extends RealmObject {
    @SerializedName("3h")
    private float precipitation;

    public float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }
}