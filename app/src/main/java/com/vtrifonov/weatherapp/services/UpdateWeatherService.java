package com.vtrifonov.weatherapp.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.activities.MainActivity;
import com.vtrifonov.weatherapp.asynctasks.RetrieveWeatherTask;
import com.vtrifonov.weatherapp.interfaces.WeatherGetter;
import com.vtrifonov.weatherapp.model.Forecast;
import com.vtrifonov.weatherapp.model.NetworkConnection;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.RealmConfiguration;

public class UpdateWeatherService extends Service implements WeatherGetter {

    public static NotificationManager notificationManager;
    public static final int NOTIFICATION_ID = 1;
    public static final String ACTION_UPDATE = "com.vtrifonov.weatherapp.ACTION_UPDATE";
    private static int UPDATE_INTERVAL;
    private static ArrayList<Forecast> forecasts;
    private SharedPreferences sharedPreferences;
    public static RealmConfiguration realmConfiguration;
    private Timer timer = new Timer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        realmConfiguration = new RealmConfiguration.Builder(this).build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("MY_LOG", "Service created");

        final String city = sharedPreferences.getString("city", "Cherkasy");
        final String country = sharedPreferences.getString("country", "ua");

        UPDATE_INTERVAL = Integer.valueOf(sharedPreferences.getString("update_interval", "1"));

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (NetworkConnection.isConnected(UpdateWeatherService.this)) {
                    new RetrieveWeatherTask(UpdateWeatherService.this).execute(city, country);
                    Log.d("MY_LOG", "Service onStartCommand");
                }
            }
        }, 10000, UPDATE_INTERVAL * 60000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        notificationManager.cancel(NOTIFICATION_ID);
        Log.d("MY_LOG", "Service stopped");
        super.onDestroy();
    }

    @Override
    public void onWeatherLoaded(ArrayList<Forecast> forecasts) {
        Log.d("MY_LOG", "Weather loaded");

        UpdateWeatherService.forecasts = forecasts;

        Intent updateIntent = new Intent(UpdateWeatherService.this, UpdateWeatherReceiver.class);
        updateIntent.setAction(ACTION_UPDATE);
        PendingIntent pendingIntentUpdate = PendingIntent.getBroadcast(UpdateWeatherService.this, 0,
                updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat
                .Builder(UpdateWeatherService.this)
                .setSmallIcon(R.drawable.ic_stat_thunder_512)
//                .setLargeIcon((((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap()))
                .setContentTitle("Weather update available")
                .setContentText("Touch to see the forecast")
                .addAction(R.drawable.ic_sync_white_24dp,
                        "Update", pendingIntentUpdate)
                .setAutoCancel(true);

        Intent resultIntent = new Intent(UpdateWeatherService.this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(UpdateWeatherService.this, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static ArrayList<Forecast> GetForecasts() {
        return forecasts;
    }
}

