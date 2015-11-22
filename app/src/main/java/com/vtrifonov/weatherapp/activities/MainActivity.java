package com.vtrifonov.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.fragments.DetailsFragment;
import com.vtrifonov.weatherapp.fragments.ListFragment;
import com.vtrifonov.weatherapp.model.RetrieveWeatherTask;
import com.vtrifonov.weatherapp.model.WeatherGetter;
import com.vtrifonov.weatherapp.model.WeatherObject;

public class MainActivity extends BaseActivity implements WeatherGetter, ListFragment.OnListItemSelectedListener {

    private static ListFragment listFragment;
    private static DetailsFragment detailsFragment;

    private static String city;
    private static String country;
    private TextView currCity;
    private int position;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDefaults();

        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
        detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);
        currCity = (TextView) findViewById(R.id.current_city);
        spinner = (ProgressBar) findViewById(R.id.progressBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setIcon(R.mipmap.ic_launcher);
        }

        if (savedInstanceState == null) {
            spinner.setVisibility(View.VISIBLE);
            RetrieveWeatherTask task = new RetrieveWeatherTask(this);
            task.execute(city, country);
        } else {
            position = savedInstanceState.getInt("position");
            if (position >= 0) {
                if (isTabletLand()) {
                    currCity.setText(String.format(getString(R.string.current_city_format), city, country));
                    detailsFragment.updateDetails(position);
                } else {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onWeatherLoaded(WeatherObject weatherObject) {
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
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                checkDefaults();
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkDefaults() {
        if (SettingsActivity.getDefaults("city", this) == null ||
                SettingsActivity.getDefaults("city", this).equals("")) {
            SettingsActivity.setDefaults("city", "Cherkasy", this);
        }

        if (SettingsActivity.getDefaults("country", this) == null ||
                SettingsActivity.getDefaults("country", this).equals("")) {
            SettingsActivity.setDefaults("country", "ua", this);
        }

        city = SettingsActivity.getDefaults("city", this);
        country = SettingsActivity.getDefaults("country", this);
    }

    public void refresh() {
        spinner.setVisibility(View.VISIBLE);
        RetrieveWeatherTask task = new RetrieveWeatherTask(this);
        task.execute(city, country);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("position", position);
        super.onSaveInstanceState(savedInstanceState);
    }
}
