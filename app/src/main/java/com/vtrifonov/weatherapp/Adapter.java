package com.vtrifonov.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vtrifonov.weatherapp.model.Forecast;
import com.vtrifonov.weatherapp.model.WeatherObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Adapter extends ArrayAdapter<Forecast> {

    private final Context context;
    private final ArrayList<Long> dates;
    private final ArrayList<Float> temperaturess;
    private final ArrayList<String> icons;
    private final ArrayList<String> descriptions;

    private final static SimpleDateFormat sDate = new SimpleDateFormat("dd", Locale.ENGLISH);
    private final static SimpleDateFormat sDay = new SimpleDateFormat("EEE", Locale.ENGLISH);
    private final static SimpleDateFormat sTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private final static SimpleDateFormat sMonth = new SimpleDateFormat("MMMM", Locale.ENGLISH);


    public Adapter(Context context, WeatherObject weather) {
        super(context, R.layout.list_item, weather.getForecastsList());
        this.context = context;
        this.dates = weather.getForecastsDates();
        this.temperaturess = weather.getForecastsTemperatures();
        this.icons = weather.getForecastsIcons();
        this.descriptions = weather.getForecastsDescriptions();
    }

    static class ViewHolder {
        public TextView date;
        public TextView day;
        public TextView time;
        public TextView month;
        public TextView temperature;
        public ImageView icon;
        public TextView description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item, null, true);

            viewHolder = new ViewHolder();
            initViews(viewHolder, view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Date date = new Date(dates.get(position) * 1000L);
        int temperature = Math.round(temperaturess.get(position));

        viewHolder.date.setText(sDate.format(date));
        viewHolder.day.setText(sDay.format(date));
        viewHolder.time.setText(sTime.format(date));
        viewHolder.month.setText(sMonth.format(date));
        viewHolder.temperature.setText("+" + temperature);
        viewHolder.icon.setImageDrawable(getContext().getDrawable(android.R.drawable.btn_star));
        viewHolder.description.setText(descriptions.get(position));

        return view;
    }

    public void initViews(ViewHolder viewHolder, View view) {
        viewHolder.date = (TextView) view.findViewById(R.id.txt_date);
        viewHolder.day = (TextView) view.findViewById(R.id.txt_day);
        viewHolder.time = (TextView) view.findViewById(R.id.txt_time);
        viewHolder.month = (TextView) view.findViewById(R.id.txt_month);
        viewHolder.temperature = (TextView) view.findViewById(R.id.txt_temp);
        viewHolder.icon = (ImageView) view.findViewById(R.id.img_icon);
        viewHolder.description = (TextView) view.findViewById(R.id.txt_weather);
    }

}
