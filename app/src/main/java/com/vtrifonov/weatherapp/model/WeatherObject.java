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

    public ArrayList<String> getForecastsWeatherMain() {
        ArrayList<String> main = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            main.add(list.get(i).getWeatherMain(0));
        }

        return main;
    }

    public ArrayList<Float> getForecastsMaxTemperatures() {
        ArrayList<Float> maxTemps = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            maxTemps.add(list.get(i).getMaxTemerature());
        }

        return maxTemps;
    }

    public ArrayList<Float> getForecastsMinTemperatures() {
        ArrayList<Float> minTemps = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            minTemps.add(list.get(i).getMinTemerature());
        }

        return minTemps;
    }

    public ArrayList<Float> getForecastsPressures() {
        ArrayList<Float> pressures = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            pressures.add(list.get(i).getPressure());
        }

        return pressures;
    }

    public ArrayList<Float> getForecastsHumidity() {
        ArrayList<Float> humiditiList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            humiditiList.add(list.get(i).getHumidity());
        }

        return humiditiList;
    }

    public ArrayList<Float> getForecastsWindSpeed() {
        ArrayList<Float> windSpeedList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            windSpeedList.add(list.get(i).getWindSpeed());
        }

        return windSpeedList;
    }

    public ArrayList<Float> getForecastsWindDir() {
        ArrayList<Float> windDirList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            windDirList.add(list.get(i).getWindDirection());
        }

        return windDirList;
    }

    public ArrayList<Float> getForecastsClouds() {
        ArrayList<Float> clouds = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            clouds.add(list.get(i).getClouds());
        }

        return clouds;
    }

    public ArrayList<Float> getForecastsRain() {
        ArrayList<Float> rain = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            rain.add(list.get(i).getRain());
        }

        return rain;
    }

    public ArrayList<Float> getForecastsSnow() {
        ArrayList<Float> snow = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            snow.add(list.get(i).getSnow());
        }

        return snow;
    }

}
