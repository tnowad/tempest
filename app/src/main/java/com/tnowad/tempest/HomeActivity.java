package com.tnowad.tempest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.tnowad.tempest.api.RetrofitInstance;
import com.tnowad.tempest.api.WeatherApi;
import com.tnowad.tempest.api.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private TextView tvCity, tvTemp, tvHumidity, tvWind;
    private ImageView imgWeather;
    private MaterialButton btnForecast;

    private FusedLocationProviderClient fusedLocationClient;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvCity = findViewById(R.id.tv_city);
        tvTemp = findViewById(R.id.tv_temp);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWind = findViewById(R.id.tv_wind);
        imgWeather = findViewById(R.id.img_weather);
        btnForecast = findViewById(R.id.btn_forecast);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Log.d(TAG, "onCreate: HomeActivity started");

        fetchLocationAndWeather();

        btnForecast.setOnClickListener(v -> {
            Log.d(TAG, "Forecast button clicked");
            startActivity(new Intent(HomeActivity.this, HourlyForecastActivity.class));
        });
    }

    @SuppressLint("MissingPermission")
    private void fetchLocationAndWeather() {
        Log.d(TAG, "fetchLocationAndWeather: Attempting to get location");

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        Log.d(TAG, "Location fetched: " + lat + ", " + lon);
                        fetchWeather(lat, lon);
                    } else {
                        Log.w(TAG, "Location is null");
                        Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching location: " + e.getMessage());
                    Toast.makeText(this, "Location error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void fetchWeather(double lat, double lon) {
        Log.d(TAG, "fetchWeather: Fetching weather data for Lat: " + lat + ", Lon: " + lon);

        WeatherApi api = RetrofitInstance.getInstance().create(WeatherApi.class);
        Call<WeatherResponse> call = api.getForecast(
                lat,
                lon,
                "temperature_2m,precipitation_probability,weathercode,windspeed_10m",
                "temperature_2m_max,temperature_2m_min,precipitation_sum",
                true,
                "auto"
        );

        Log.d(TAG, "Request URL: " + call.request().url());

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    Log.d(TAG, "Weather data fetched successfully");
                    updateUI(weather);
                } else {
                    Log.e(TAG, "API error: " + response.message());
                    Toast.makeText(HomeActivity.this, "API error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
                Toast.makeText(HomeActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(WeatherResponse weather) {
        Log.d(TAG, "updateUI: Updating UI with Open-Meteo data");

        tvCity.setText("Lat: " + weather.latitude + ", Lon: " + weather.longitude);
        tvTemp.setText(Math.round(weather.currentWeather.temperature) + "°C");
        tvHumidity.setText("Precip: " + weather.hourly.precipitationProbability.get(0) + "%");
        tvWind.setText("Wind: " + weather.currentWeather.windspeed + " km/h");

        int code = weather.currentWeather.weathercode;
        int iconRes = R.drawable.ic_weather_sunny;

        if (code >= 51 && code <= 67) iconRes = R.drawable.ic_weather_rain;
        else if (code >= 71 && code <= 86) iconRes = R.drawable.ic_weather_snow;
        else if (code >= 95) iconRes = R.drawable.ic_weather_storm;
        else if (code >= 1 && code <= 3) iconRes = R.drawable.ic_weather_cloudy;

        imgWeather.setImageResource(iconRes);
        Log.d(TAG, "Weather icon set based on code: " + code);
    }
}
