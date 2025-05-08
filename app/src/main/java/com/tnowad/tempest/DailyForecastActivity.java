
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

        if (weather != null && weather.daily != null) {
            List<DailyWeather> dailyData = buildDailyData(weather);
            recyclerDaily.setAdapter(new DailyWeatherAdapter(dailyData));
        }
    }

    private List<DailyWeather> buildDailyData(WeatherResponse weather) {
        final double PRECIP_THRESHOLD = 50.0;

        List<DailyWeather> dailyData = new ArrayList<>();
        List<String> dates = weather.daily.date;
        List<Double> maxTemps = weather.daily.maxTemp;
        List<Double> minTemps = weather.daily.minTemp;
        List<Double> precipitation = weather.daily.precipitationSum;

        int days = dates.size();

        for (int i = 0; i < days; i++) {
            String day = dates.get(i);
            String tempRange = Math.round(minTemps.get(i)) + "° / " + Math.round(maxTemps.get(i)) + "°";
            double precip = precipitation.get(i);

            int icon = precip > PRECIP_THRESHOLD
                    ? R.drawable.ic_weather_rain
                    : R.drawable.ic_weather_sunny;

            dailyData.add(new DailyWeather(day, tempRange, icon, precip));
        }

        return dailyData;
    }
}
