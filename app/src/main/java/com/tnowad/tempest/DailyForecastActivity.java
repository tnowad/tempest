package com.tnowad.tempest;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tnowad.tempest.api.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastActivity extends AppCompatActivity {

    private RecyclerView recyclerDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        recyclerDaily = findViewById(R.id.recycler_daily);
        recyclerDaily.setLayoutManager(new LinearLayoutManager(this));

        String json = getIntent().getStringExtra("weatherData");
        WeatherResponse weather = new Gson().fromJson(json, WeatherResponse.class);

        recyclerDaily.setAdapter(new WeatherAdapter(getDailyData(weather)));
    }

    private List<Weather> getDailyData(WeatherResponse weather) {
        List<Weather> dailyData = new ArrayList<>();
        List<String> dates = weather.daily.date;
        List<Double> maxTemps = weather.daily.maxTemp;
        List<Double> minTemps = weather.daily.minTemp;
        List<Double> precipitation = weather.daily.precipitationSum;

        for (int i = 0; i < dates.size(); i++) {
            String day = dates.get(i);
            String tempRange = Math.round(minTemps.get(i)) + "° / " + Math.round(maxTemps.get(i)) + "°";
            int icon = R.drawable.ic_weather_sunny;
            dailyData.add(new Weather(day, tempRange, icon));
        }

        return dailyData;
    }
}
