package com.vtrifonov.weatherapp.activities;

import android.os.Bundle;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.fragments.DetailsFragment;

public class DetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (isTabletLand()) {
            finish();
            return;
        }

        int position = getIntent().getExtras().getInt("position");

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.details_fragment);

        detailsFragment.updateDetails(position);
    }

}
