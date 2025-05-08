package com.tnowad.tempest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {

    private final List<HourlyWeather> hourlyList;

    public HourlyWeatherAdapter(List<HourlyWeather> hourlyList) {
        this.hourlyList = hourlyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_forecast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        var hw = hourlyList.get(pos);
        holder.tvHour.setText(hw.getHour());
        holder.tvTemp.setText(hw.getTemp());
        holder.tvWind.setText(hw.getWindSpeed() + " km/h");
        holder.tvPrecip.setText(hw.getPrecipitationProbability() + "%");
        holder.imgWeather.setImageResource(hw.getIconResId());
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvHour, tvTemp, tvWind, tvPrecip;
        final ImageView imgWeather;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHour = itemView.findViewById(R.id.tv_hour);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            tvWind = itemView.findViewById(R.id.tv_wind);
            tvPrecip = itemView.findViewById(R.id.tv_precip);
            imgWeather = itemView.findViewById(R.id.img_weather);
        }
    }
}
