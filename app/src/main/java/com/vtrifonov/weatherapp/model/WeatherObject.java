package com.vtrifonov.weatherapp.model;

import java.util.ArrayList;

public class WeatherObject {
    private City city;
    private ArrayList<Forecast> list;

    public ArrayList<Forecast> getForecastsList() {
        return list;
    }

    public ArrayList<Long> getForecastsDates() {
        ArrayList<Long> dates = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            dates.add(list.get(i).getDate());
        }

        return dates;
    }

    public ArrayList<Float> getForecastsTemperatures() {
        ArrayList<Float> temps = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            temps.add(list.get(i).getTemperature());
        }

        return temps;
    }

    public ArrayList<String> getForecastsIcons() {
        ArrayList<String> icons = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            icons.add(list.get(i).getWeatherIcon(0));
        }

        return icons;
    }

    public ArrayList<String> getForecastsDescriptions() {
        ArrayList<String> descriptions = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            descriptions.add(list.get(i).getWeatherDescription(0));
        }

        return descriptions;
    }

}
