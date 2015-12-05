package com.vtrifonov.weatherapp.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtrifonov.weatherapp.activities.MainActivity;
import com.vtrifonov.weatherapp.model.Forecast;
import com.vtrifonov.weatherapp.interfaces.WeatherGetter;
import com.vtrifonov.weatherapp.model.WeatherObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmObject;

public class RetrieveWeatherTask extends AsyncTask<String, Void, ArrayList<Forecast>> {

    private WeatherGetter weatherGetter;

    static final String API_KEY = "20be3fa6c35af06a89d2d5fbdb410b6d";
    static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    public static final String IMG_URL = "http://openweathermap.org/img/w/";

    public RetrieveWeatherTask(WeatherGetter weatherGetter) {
        this.weatherGetter = weatherGetter;
    }

    @Override
    protected ArrayList<Forecast> doInBackground(String... args) {

        String city = args[0],
                country = args[1];

        return getWeather(city, country);
    }

    @Override
    protected void onPostExecute(ArrayList<Forecast> forecasts) {
        super.onPostExecute(forecasts);

        if (!forecasts.isEmpty()) {
            weatherGetter.onWeatherLoaded();
        }
    }

    public ArrayList<Forecast> getWeather(String city, String country) {
        try {
            URL url = new URL(API_URL + city + "," + country + "&units=metric&APPID=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Realm realm = null;

            try {
                realm = Realm.getInstance(MainActivity.realmConfiguration);

                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());

                Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStrategy() {
                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return f.getDeclaringClass().equals(RealmObject.class);
                            }

                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        })
                        .create();

                WeatherObject weatherObject = gson.fromJson(inputStreamReader, WeatherObject.class);

                realm.beginTransaction();
                Collection<Forecast> realmForecasts = realm.copyToRealmOrUpdate(weatherObject.getForecasts());
                realm.commitTransaction();

                return new ArrayList<>(realmForecasts);
            } finally {
                realm.close();
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

}