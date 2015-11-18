package com.vtrifonov.weatherapp.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtrifonov.weatherapp.WeatherGetter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveWeatherTask extends AsyncTask<String, Void, WeatherObject> {

    private WeatherGetter weatherGetter;

    static final String API_KEY = "20be3fa6c35af06a89d2d5fbdb410b6d";
    static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";

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
            weatherGetter.onWeatherLoaded(weatherObject);
        }
        // TODO: do something with the forecasts
    }

    public WeatherObject getWeather(String city, String country) {
        try {
            URL url = new URL(API_URL + city + "," + country + "&units=metric&APPID=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String json = stringBuilder.toString();

                Gson gson = new GsonBuilder().create();

                return gson.fromJson(json, WeatherObject.class);

            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

}