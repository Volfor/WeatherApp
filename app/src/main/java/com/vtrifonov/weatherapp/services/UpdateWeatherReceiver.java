package com.vtrifonov.weatherapp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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

            UpdateWeatherService.notificationManager.cancel(UpdateWeatherService.NOTIFICATION_ID);

            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);

            Log.d("MY_LOG", "action update");
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

}
