package com.vtrifonov.weatherapp.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.vtrifonov.weatherapp.Adapter;
import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.WeatherGetter;
import com.vtrifonov.weatherapp.model.RetrieveWeatherTask;
import com.vtrifonov.weatherapp.model.WeatherObject;

public class MainActivity extends BaseActivity implements WeatherGetter {

    ListView listView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrieveWeatherTask task = new RetrieveWeatherTask(this);
        task.execute("Cherkasy", "ua");
    }

    @Override
    public void onWeatherLoaded(WeatherObject weatherObject) {
        listView = (ListView) findViewById(R.id.list_titles);
        adapter = new Adapter(MainActivity.this, weatherObject);
        listView.setAdapter(adapter);
    }

}
