package com.vtrifonov.weatherapp.model;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkConnection {

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
