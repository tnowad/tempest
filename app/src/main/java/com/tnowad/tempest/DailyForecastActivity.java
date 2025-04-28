package com.tnowad.tempest;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastActivity extends AppCompatActivity {

    private RecyclerView recyclerDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        recyclerDaily = findViewById(R.id.recycler_daily);

        // Set up RecyclerView with adapter
        recyclerDaily.setLayoutManager(new LinearLayoutManager(this));
        recyclerDaily.setAdapter(new WeatherAdapter(getDailyData()));
    }

    // Mock data for demonstration
    private List<Weather> getDailyData() {
        List<Weather> dailyData = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dailyData.add(new Weather("Day " + i, "28°C / 32°C", R.drawable.ic_weather_sunny));
        }
        return dailyData;
    }
}
