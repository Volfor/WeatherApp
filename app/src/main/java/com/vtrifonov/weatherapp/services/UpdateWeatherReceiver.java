package com.vtrifonov.weatherapp.services;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.activities.MainActivity;
import com.vtrifonov.weatherapp.model.Forecast;

import java.util.ArrayList;

import io.realm.Realm;

public class UpdateWeatherReceiver extends BroadcastReceiver {

    ArrayList<Forecast> forecasts;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(UpdateWeatherService.ACTION_UPDATE)) {

            forecasts = UpdateWeatherService.GetForecasts();

            Realm realm = Realm.getInstance(UpdateWeatherService.realmConfiguration);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(forecasts);
            realm.commitTransaction();
            realm.close();

            Log.d("MY_LOG", "action update");

            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_stat_thunder_512)
                    .setContentTitle("Weather updated")
                    .setContentText("Touch to see the forecast")
                    .setAutoCancel(true);

            Intent resultIntent = new Intent(context, MainActivity.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(resultPendingIntent);

            UpdateWeatherService.notificationManager.notify(UpdateWeatherService.NOTIFICATION_ID, builder.build());
        }
    }

}
