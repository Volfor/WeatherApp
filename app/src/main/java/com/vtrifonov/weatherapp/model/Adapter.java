package com.vtrifonov.weatherapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vtrifonov.weatherapp.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Adapter extends ArrayAdapter<Forecast> {

    private final Context context;
    private WeatherObject weatherObject;

    private final static SimpleDateFormat fDate = new SimpleDateFormat("dd", Locale.ENGLISH);
    private final static SimpleDateFormat fDay = new SimpleDateFormat("EEEE", Locale.ENGLISH);
    private final static SimpleDateFormat fTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private final static SimpleDateFormat fMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);

    private final static DecimalFormat fTemp = new DecimalFormat("+#;-#");

    public Adapter(Context context, WeatherObject weather) {
        super(context, R.layout.list_item, weather.getForecastsList());
        this.context = context;
        this.weatherObject = weather;
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

        Date date = new Date(weatherObject.getForecastsDates().get(position) * 1000L);
        int temperature = Math.round(weatherObject.getForecastsTemperatures().get(position));

        viewHolder.date.setText(fDate.format(date));
        viewHolder.day.setText(fDay.format(date));
        viewHolder.time.setText(fTime.format(date));
        viewHolder.month.setText(fMonth.format(date));
        viewHolder.temperature.setText(fTemp.format(temperature));
        viewHolder.description.setText(weatherObject.getForecastsDescriptions().get(position));

        loadIcons(context, weatherObject.getForecastsIcons().get(position), viewHolder.icon);

        return view;
    }

    public void initViews(ViewHolder viewHolder, View view) {
        viewHolder.date = (TextView) view.findViewById(R.id.txt_date);
        viewHolder.day = (TextView) view.findViewById(R.id.txt_day);
        viewHolder.month = (TextView) view.findViewById(R.id.txt_month);
        viewHolder.time = (TextView) view.findViewById(R.id.txt_time);
        viewHolder.temperature = (TextView) view.findViewById(R.id.txt_temp);
        viewHolder.icon = (ImageView) view.findViewById(R.id.img_icon);
        viewHolder.description = (TextView) view.findViewById(R.id.txt_weather);
    }

    public void loadIcons(Context context, String code, ImageView imgView) {
        Picasso.with(context).load(RetrieveWeatherTask.IMG_URL + code + ".png").into(imgView);
    }

}
