package com.tnowad.tempest;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastActivity extends AppCompatActivity {

    private RecyclerView recyclerHourly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);

        recyclerHourly = findViewById(R.id.recycler_hourly);

        // Set up RecyclerView with adapter
        recyclerHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Assuming you have a WeatherAdapter class for the data
        recyclerHourly.setAdapter(new WeatherAdapter(getHourlyData()));
    }

    // Mock data for demonstration
    private List<Weather> getHourlyData() {
        List<Weather> hourlyData = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hourlyData.add(new Weather("Hour " + i, "30°C", R.drawable.ic_weather_sunny));
        }
        return hourlyData;
    }
}
