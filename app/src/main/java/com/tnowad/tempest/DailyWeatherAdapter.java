package com.tnowad.tempest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.WeatherViewHolder> {

    private final List<DailyWeather> forecastList;

    public DailyWeatherAdapter(List<DailyWeather> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_forecast_item, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        DailyWeather weather = forecastList.get(position);
        holder.tvDay.setText(weather.getDay());
        holder.tvTempRange.setText(weather.getTempRange());
        holder.imgWeather.setImageResource(weather.getIconResId());
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        final TextView tvDay;
        final TextView tvTempRange;
        final ImageView imgWeather;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tv_day);
            tvTempRange = itemView.findViewById(R.id.tv_temp_range);
            imgWeather = itemView.findViewById(R.id.img_weather);
        }
    }
}
