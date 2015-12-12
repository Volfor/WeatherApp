package com.vtrifonov.weatherapp.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.fragments.DetailsFragment;

public class DetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.details_bar);
        setSupportActionBar(toolbar);

        String city = PreferenceManager.getDefaultSharedPreferences(this).getString("city", "Cherkasy");
        String country = PreferenceManager.getDefaultSharedPreferences(this).getString("country", "ua");

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(String.format(getString(R.string.current_city_format), city, country));
        }

        if (isTabletLand()) {
            finish();
            return;
        }

        int position = getIntent().getExtras().getInt("position");

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.details_fragment);

        detailsFragment.updateDetails(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
