package com.vtrifonov.weatherapp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveWeatherTask extends AsyncTask<String, Void, WeatherObject> {

    private WeatherGetter weatherGetter;

    static final String API_KEY = "20be3fa6c35af06a89d2d5fbdb410b6d";
    static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    public static final String IMG_URL = "http://openweathermap.org/img/w/";

    public RetrieveWeatherTask(WeatherGetter weatherGetter) {
        this.weatherGetter = weatherGetter;
    }

    @Override
    protected WeatherObject doInBackground(String... args) {

        String city = args[0],
                country = args[1];

        return getWeather(city, country);
    }

    @Override
    protected void onPostExecute(WeatherObject weatherObject) {
        super.onPostExecute(weatherObject);

        if (weatherObject != null) {
            WeatherSingleton.getInstance().onWeatherLoaded(weatherObject);
            weatherGetter.onWeatherLoaded(weatherObject);
        }
    }

    public WeatherObject getWeather(String city, String country) {
        try {
            URL url = new URL(API_URL + city + "," + country + "&units=metric&APPID=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                Gson gson = new GsonBuilder().create();

                return gson.fromJson(inputStreamReader, WeatherObject.class);
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

}