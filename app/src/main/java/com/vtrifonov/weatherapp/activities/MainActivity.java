package com.vtrifonov.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.asynctasks.RetrieveWeatherTask;
import com.vtrifonov.weatherapp.fragments.DetailsFragment;
import com.vtrifonov.weatherapp.fragments.ListFragment;
import com.vtrifonov.weatherapp.fragments.NoConnectionFragment;
import com.vtrifonov.weatherapp.interfaces.WeatherGetter;
import com.vtrifonov.weatherapp.model.Forecast;
import com.vtrifonov.weatherapp.model.NetworkConnection;
import com.vtrifonov.weatherapp.services.UpdateWeatherService;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends BaseActivity implements WeatherGetter, ListFragment.OnListItemSelectedListener {

    private static ListFragment listFragment;
    private static DetailsFragment detailsFragment;
    private static NoConnectionFragment noConnectionFragment;
    public static RealmConfiguration realmConfiguration;

    private String city;
    private String country;
    private TextView currCity;
    private int position;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realmConfiguration = new RealmConfiguration.Builder(this).build();

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("service_enabled", true)) {
            startService(new Intent(this, UpdateWeatherService.class));
        }

        city = PreferenceManager.getDefaultSharedPreferences(this).getString("city", "Cherkasy");
        country = PreferenceManager.getDefaultSharedPreferences(this).getString("country", "ua");

        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
        detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);
        noConnectionFragment = (NoConnectionFragment)
                getSupportFragmentManager().findFragmentById(R.id.no_connection_fragment);
        currCity = (TextView) findViewById(R.id.current_city);
        spinner = (ProgressBar) findViewById(R.id.progressBar);

        currCity.setText(String.format(getString(R.string.current_city_format), city, country));

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setIcon(R.mipmap.ic_launcher);
        }

        if (savedInstanceState == null) {
            if (NetworkConnection.isConnected(MainActivity.this)) {
                spinner.setVisibility(View.VISIBLE);
                RetrieveWeatherTask task = new RetrieveWeatherTask(MainActivity.this);
                task.execute(city, country);
            } else {
                if (Realm.getInstance(realmConfiguration).isEmpty())
                    noConnectionFragment.getView().setVisibility(View.VISIBLE);
                else
                    updateInfo();
            }
        } else {
            position = savedInstanceState.getInt("position");
            if (position >= 0) {
                if (isTabletLand()) {
                    detailsFragment.updateDetails(position);
                }
            }
        }
    }

    @Override
    public void onWeatherLoaded(ArrayList<Forecast> forecasts) {
        Realm realm = Realm.getInstance(realmConfiguration);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(forecasts);
        realm.commitTransaction();
        realm.close();

        updateInfo();
    }

    public void updateInfo() {
        listFragment.setupListView();
        currCity.setText(String.format(getString(R.string.current_city_format), city, country));
        if (isTabletLand())
            detailsFragment.updateDetails(0);
        spinner.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(int position) {
        this.position = position;
        if (isTabletLand()) {
            detailsFragment.updateDetails(position);
        } else {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refresh() {
        if (NetworkConnection.isConnected(MainActivity.this)) {
            spinner.setVisibility(View.VISIBLE);

            city = PreferenceManager.getDefaultSharedPreferences(this).getString("city", "Cherkasy");
            country = PreferenceManager.getDefaultSharedPreferences(this).getString("country", "ua");

            RetrieveWeatherTask task = new RetrieveWeatherTask(MainActivity.this);
            task.execute(city, country);
            noConnectionFragment.getView().setVisibility(View.GONE);
        } else {
            Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("position", position);
        super.onSaveInstanceState(savedInstanceState);
    }

}
