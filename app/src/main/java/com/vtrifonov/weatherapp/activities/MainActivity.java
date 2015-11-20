package com.vtrifonov.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.fragments.ListFragment;
import com.vtrifonov.weatherapp.model.RetrieveWeatherTask;
import com.vtrifonov.weatherapp.model.WeatherGetter;
import com.vtrifonov.weatherapp.model.WeatherObject;

public class MainActivity extends BaseActivity implements WeatherGetter, ListFragment.OnListItemSelectedListener {

    private static ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrieveWeatherTask task = new RetrieveWeatherTask(this);
        task.execute("Cherkasy", "ua");

        if (!isTabletLand())
            listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list_id_single_pane);
    }

    @Override
    public void onWeatherLoaded(WeatherObject weatherObject) {
        listFragment.setupListView();
    }

    @Override
    public void onItemSelected(int position) {

        if (!isTabletLand()) {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            startActivity(intent);
        }
        //todo:show new activity or update fragment
    }
}
