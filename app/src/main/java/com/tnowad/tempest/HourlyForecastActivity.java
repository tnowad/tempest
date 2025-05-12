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
        recyclerHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String json = getIntent().getStringExtra("weatherData");
        WeatherResponse weather = new Gson().fromJson(json, WeatherResponse.class);

        if (weather != null && weather.hourly != null) {
            recyclerHourly.setAdapter(new HourlyWeatherAdapter(buildHourlyData(weather)));
        }
    }

    private List<HourlyWeather> buildHourlyData(WeatherResponse weather) {
        List<HourlyWeather> list = new ArrayList<>();
        var time = weather.hourly.time;
        var temps = weather.hourly.temperature;
        var codes = weather.hourly.weatherCode;
        var wind = weather.hourly.windSpeed;
        var precip = weather.hourly.precipitationProbability;

        int count = time.size();

        for (int i = 0; i < count; i++) {
            String hour = time.get(i).substring(11, 16) + " - " + time.get(i).substring(8, 10) + "/" + time.get(i).substring(5, 7);
            String temp = Math.round(temps.get(i)) + "°";
            int icon = getWeatherIcon(codes.get(i));
            list.add(new HourlyWeather(hour, temp, icon, wind.get(i), precip.get(i)));
        }

        return list;
    }

    private int getWeatherIcon(int code) {
        if (code >= 50 && code < 80) return R.drawable.ic_weather_rain;
        if (code >= 80) return R.drawable.ic_weather_storm;
        return R.drawable.ic_weather_sunny;
    }
}
