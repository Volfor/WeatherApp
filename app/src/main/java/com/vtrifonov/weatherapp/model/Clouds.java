package com.vtrifonov.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Clouds extends RealmObject {
    @SerializedName("all")
    private float cloudiness;

    public float getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(float cloudiness) {
        this.cloudiness = cloudiness;
    }
}