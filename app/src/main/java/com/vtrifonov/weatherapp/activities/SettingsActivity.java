package com.vtrifonov.weatherapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.vtrifonov.weatherapp.R;

public class SettingsActivity extends BaseActivity {

    EditText city;
    EditText country;

    String city_txt;
    String country_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_bar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        city = (EditText) findViewById(R.id.edit_city);
        country = (EditText) findViewById(R.id.edit_country);

        city.setText(getDefaults("city", this));
        country.setText(getDefaults("country", this));
    }


    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                city_txt = city.getText().toString();
                country_txt = country.getText().toString();

                setDefaults("city", city_txt, this);
                setDefaults("country", country_txt, this);

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        city_txt = city.getText().toString();
        country_txt = country.getText().toString();

        setDefaults("city", city_txt, this);
        setDefaults("country", country_txt, this);

        super.onBackPressed();
    }

}
