package com.vtrifonov.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.services.UpdateWeatherService;

public class PreferencesActivity extends PreferenceActivity {

    private EditTextPreference city_edit;
    private EditTextPreference country_edit;

    private CheckBoxPreference enable_service_checkBox;
    private ListPreference check_interval_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        city_edit = (EditTextPreference) findPreference("city");
        country_edit = (EditTextPreference) findPreference("country");

        city_edit.setSummary(city_edit.getText());
        country_edit.setSummary(country_edit.getText());

        city_edit.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String summary = (String) newValue;
                if (summary.trim().length() != 0) {
                    city_edit.setSummary(summary);
                    return true;
                }
                return false;
            }
        });

        country_edit.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String summary = (String) newValue;
                if (summary.trim().length() != 0) {
                    country_edit.setSummary(summary);
                    return true;
                }
                return false;
            }
        });

        enable_service_checkBox = (CheckBoxPreference) findPreference("service_enabled");
        check_interval_list = (ListPreference) findPreference("update_interval");

        check_interval_list.setEnabled(enable_service_checkBox.isChecked());

        enable_service_checkBox.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                check_interval_list.setEnabled(enable_service_checkBox.isChecked());
                return false;
            }
        });

        enable_service_checkBox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean enabled = (Boolean) newValue;
                if (enabled) {
                    startService(new Intent(PreferencesActivity.this, UpdateWeatherService.class));
                } else {
                    stopService(new Intent(PreferencesActivity.this, UpdateWeatherService.class));
                }
                return true;
            }
        });

        check_interval_list.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                stopService(new Intent(PreferencesActivity.this, UpdateWeatherService.class));
                startService(new Intent(PreferencesActivity.this, UpdateWeatherService.class));
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.app_bar, root, false);
        root.addView(bar, 0);
        bar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        bar.setTitle(R.string.preferences_label);
        bar.setTitleTextColor(getResources().getColor(R.color.white));

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
