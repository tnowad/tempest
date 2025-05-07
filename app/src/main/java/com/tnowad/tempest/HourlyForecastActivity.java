package com.tnowad.tempest;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tnowad.tempest.api.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastActivity extends AppCompatActivity {

    private RecyclerView recyclerHourly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);

        recyclerHourly = findViewById(R.id.recycler_hourly);
        recyclerHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        String json = getIntent().getStringExtra("weatherData");
        WeatherResponse weather = new Gson().fromJson(json, WeatherResponse.class);

        recyclerHourly.setAdapter(new WeatherAdapter(getHourlyData(weather)));
    }

    private List<Weather> getHourlyData(WeatherResponse weather) {
        List<Weather> hourlyData = new ArrayList<>();
        List<String> times = weather.hourly.time;
        List<Double> temps = weather.hourly.temperature;
        List<Integer> codes = weather.hourly.weatherCode;

        int count = Math.min(times.size(), 24); // Only show next 24 hours

        for (int i = 0; i < count; i++) {
            String hourLabel = times.get(i).substring(11, 16); // Extract HH:mm
            String temp = Math.round(temps.get(i)) + "°C";
            int icon = getIconForCode(codes.get(i));
            hourlyData.add(new Weather(hourLabel, temp, icon));
        }

        return hourlyData;
    }

    private int getIconForCode(int code) {
        if (code >= 51 && code <= 67) return R.drawable.ic_weather_rain;
        else if (code >= 71 && code <= 86) return R.drawable.ic_weather_snow;
        else if (code >= 95) return R.drawable.ic_weather_storm;
        else if (code >= 1 && code <= 3) return R.drawable.ic_weather_cloudy;
        else return R.drawable.ic_weather_sunny;
    }
}
