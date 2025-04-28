package com.tnowad.tempest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<Weather> weatherList;

    public WeatherAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        holder.tvHour.setText(weather.getHour());
        holder.tvTemp.setText(weather.getTemperature());
        holder.imgWeather.setImageResource(weather.getIconResId());
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHour, tvTemp;
        public ImageView imgWeather;

        public ViewHolder(View view) {
            super(view);
            tvHour = view.findViewById(R.id.tv_hour);
            tvTemp = view.findViewById(R.id.tv_temp);
            imgWeather = view.findViewById(R.id.img_weather);
        }
    }
}
