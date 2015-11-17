package com.vtrifonov.weatherapp.model;

public class City {
    private int id;
    private String name;
    private Coordinates coord;
    private String country;

    class Coordinates {
        private float lon;
        private float lat;
    }

}
