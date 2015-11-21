package com.vtrifonov.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.fragments.DetailsFragment;
import com.vtrifonov.weatherapp.fragments.ListFragment;
import com.vtrifonov.weatherapp.model.RetrieveWeatherTask;
import com.vtrifonov.weatherapp.model.WeatherGetter;
import com.vtrifonov.weatherapp.model.WeatherObject;

public class MainActivity extends BaseActivity implements WeatherGetter, ListFragment.OnListItemSelectedListener {

    private static ListFragment listFragment;
    private static DetailsFragment detailsFragment;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
        detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        if (savedInstanceState == null) {
            RetrieveWeatherTask task = new RetrieveWeatherTask(this);
            task.execute("Cherkasy", "ua");
        } else {
            position = savedInstanceState.getInt("position");
            if (position >= 0) {
                if (isTabletLand()) {
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
        if (isTabletLand())
            detailsFragment.updateDetails(0);
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("position", position);
        super.onSaveInstanceState(savedInstanceState);
    }
}
