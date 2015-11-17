package com.vtrifonov.weatherapp.activities;

import android.support.v7.app.AppCompatActivity;

import com.vtrifonov.weatherapp.R;

public class BaseActivity extends AppCompatActivity {

    protected boolean isTabletLand() {
        return getResources().getBoolean(R.bool.isTabletLand);
    }
}
