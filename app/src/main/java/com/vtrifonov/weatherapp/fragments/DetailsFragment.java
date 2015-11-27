package com.vtrifonov.weatherapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.activities.MainActivity;
import com.vtrifonov.weatherapp.model.Forecast;
import com.vtrifonov.weatherapp.model.RetrieveWeatherTask;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class DetailsFragment extends Fragment {
    private int currentPosition = -1;

    private TextView date_number;
    private TextView month;
    private TextView day;
    private TextView time;

    private TextView temp;
    private ImageView icon;
    private TextView weather_description;

    private TextView min_temp;
    private TextView max_temp;
    private TextView pressure;
    private TextView humidity;
    private TextView clouds;
    private TextView wind_speed;
    private TextView wind_dir;
    private TextView rain;
    private TextView snow;

    private final SimpleDateFormat fDate = new SimpleDateFormat("dd", Locale.ENGLISH);
    private final SimpleDateFormat fDay = new SimpleDateFormat("EEEE", Locale.ENGLISH);
    private final SimpleDateFormat fTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private final SimpleDateFormat fMonth = new SimpleDateFormat("MMMM", Locale.ENGLISH);

    private final DecimalFormat fTemp = new DecimalFormat("+#;-#");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (currentPosition != -1) {
            updateDetails(currentPosition);
        }
    }

    public void updateDetails(int position) {
        Realm realm = Realm.getInstance(MainActivity.realmConfiguration);
        RealmResults<Forecast> forecasts = realm.where(Forecast.class).findAll();

        if (realm.isEmpty()) {
            return;
        }

        Date date = new Date(forecasts.get(position).getDate() * 1000L);
        DecimalFormat fPrecipitation = new DecimalFormat("##.###");
        int temperature = Math.round(forecasts.get(position).getWeatherConditions().getTemp());
        int min_temperature = Math.round(forecasts.get(position).getWeatherConditions().getTempMin());
        int max_temperature = Math.round(forecasts.get(position).getWeatherConditions().getTempMax());

        date_number.setText(fDate.format(date));
        day.setText(fDay.format(date));
        month.setText(fMonth.format(date));
        time.setText(fTime.format(date));

        temp.setText(fTemp.format(temperature));
        weather_description.setText(forecasts.get(position).getWeather().get(0).getDescription());

        Picasso.with(getContext()).load(RetrieveWeatherTask.IMG_URL + forecasts.get(position)
                .getWeather().get(0).getIcon() + ".png").into(icon);

        min_temp.setText(String.format(getString(R.string.temp_format_details), min_temperature));
        max_temp.setText(String.format(getString(R.string.temp_format_details), max_temperature));
        pressure.setText(String.format(getString(R.string.pressure_format),
                forecasts.get(position).getWeatherConditions().getPressure()));
        humidity.setText(String.format(getString(R.string.percents_format),
                forecasts.get(position).getWeatherConditions().getHumidity()));
        clouds.setText(String.format(getString(R.string.percents_format),
                forecasts.get(position).getClouds().getCloudiness()));
        wind_speed.setText(String.format(getString(R.string.wind_speed_format),
                forecasts.get(position).getWind().getSpeed()));
        wind_dir.setText(String.format(getString(R.string.wind_direction_format),
                forecasts.get(position).getWind().getDeg()));

        if (forecasts.get(position).getRain() != null && forecasts.get(position).getRain().getPrecipitation() > 0) {
            getActivity().findViewById(R.id.rain_view).setVisibility(View.VISIBLE);

            rain.setText(String.format(getString(R.string.precipitation_format),
                    fPrecipitation.format(forecasts.get(position).getRain().getPrecipitation())));
        }

        if (forecasts.get(position).getSnow() != null && forecasts.get(position).getSnow().getPrecipitation() > 0) {
            getActivity().findViewById(R.id.snow_view).setVisibility(View.VISIBLE);

            snow.setText(String.format(getString(R.string.precipitation_format),
                    fPrecipitation.format(forecasts.get(position).getSnow().getPrecipitation())));
        }

        currentPosition = position;
    }

    public void initViews(View view) {
        date_number = (TextView) view.findViewById(R.id.date);
        month = (TextView) view.findViewById(R.id.month);
        day = (TextView) view.findViewById(R.id.day);
        time = (TextView) view.findViewById(R.id.time);

        temp = (TextView) view.findViewById(R.id.temp);
        icon = (ImageView) view.findViewById(R.id.icon);
        weather_description = (TextView) view.findViewById(R.id.weather_description);

        min_temp = (TextView) view.findViewById(R.id.min_temp);
        max_temp = (TextView) view.findViewById(R.id.max_temp);
        pressure = (TextView) view.findViewById(R.id.pressure);
        humidity = (TextView) view.findViewById(R.id.humidity);
        clouds = (TextView) view.findViewById(R.id.clouds);
        wind_speed = (TextView) view.findViewById(R.id.wind_speed);
        wind_dir = (TextView) view.findViewById(R.id.wind_dir);
        rain = (TextView) view.findViewById(R.id.rain);
        snow = (TextView) view.findViewById(R.id.snow);
    }

}
