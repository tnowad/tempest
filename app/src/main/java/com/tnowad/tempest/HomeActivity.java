package com.tnowad.tempest;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.tnowad.tempest.api.RetrofitInstance;
import com.tnowad.tempest.api.WeatherApi;
import com.tnowad.tempest.api.WeatherResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private WeatherResponse weatherData;
    private TextView tvCity, tvDate, tvTemp, tvHumidity, tvWind, tvPrecip;
    private ImageView imgWeather;
    private MaterialButton btnHourlyForecast, btnDailyForecast;

    private FusedLocationProviderClient fusedLocationClient;
    private static final String TAG = "HomeActivity";

    private static final int TEMPERATURE_THRESHOLD = 35;
    private static final int PRECIPITATION_THRESHOLD = 80; // Ngưỡng mưa (phần trăm) để cảnh báo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvCity = findViewById(R.id.tv_city);
        tvDate = findViewById(R.id.tv_date);
        tvTemp = findViewById(R.id.tv_temp);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWind = findViewById(R.id.tv_wind);
        tvPrecip = findViewById(R.id.tv_precip);
        imgWeather = findViewById(R.id.img_weather);
        btnHourlyForecast = findViewById(R.id.btn_hourly_forecast);
        btnDailyForecast = findViewById(R.id.btn_daily_forecast);

        btnHourlyForecast.setEnabled(false);
        btnDailyForecast.setEnabled(false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Log.d(TAG, "onCreate: HomeActivity started");

        setCurrentDate();
        fetchLocationAndWeather();

        btnHourlyForecast.setOnClickListener(v -> {
            if (weatherData != null) {
                Intent intent = new Intent(HomeActivity.this, HourlyForecastActivity.class);
                intent.putExtra("weatherData", new Gson().toJson(weatherData));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Weather data not ready", Toast.LENGTH_SHORT).show();
            }
        });

        btnDailyForecast.setOnClickListener(v -> {
            if (weatherData != null) {
                Intent intent = new Intent(HomeActivity.this, DailyForecastActivity.class);
                intent.putExtra("weatherData", new Gson().toJson(weatherData));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Weather data not ready", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCurrentDate() {
        String currentDate = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(new Date());
        tvDate.setText(currentDate);
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
                    weatherData = response.body();

                    btnHourlyForecast.setEnabled(true);
                    btnDailyForecast.setEnabled(true);

                    checkWeatherConditions(weather); // Kiểm tra điều kiện thời tiết
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

        tvCity.setText(String.format(Locale.getDefault(), "Lat: %.2f, Lon: %.2f", weather.latitude, weather.longitude));
        tvTemp.setText(Math.round(weather.currentWeather.temperature) + "°C");
        tvHumidity.setText("Humidity: --%");
        tvPrecip.setText("Precipitation: " + weather.hourly.precipitationProbability.get(0) + "%");
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

    private void checkWeatherConditions(WeatherResponse weather) {
        var temperature = Math.round(weather.currentWeather.temperature);
        int precipitation = weather.hourly.precipitationProbability.get(0); // Giả sử lấy xác suất mưa trong giờ đầu tiên

        if (temperature > TEMPERATURE_THRESHOLD) {
            sendNotification("High Temperature Alert", "The temperature is " + temperature + "°C. Stay cool!");
        }

        if (precipitation > PRECIPITATION_THRESHOLD) {
            sendNotification("Heavy Rain Alert", "There is a " + precipitation + "% chance of rain. Carry an umbrella!");
        }
    }

    @SuppressLint("NewApi")
    private void sendNotification(String title, String content) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("weather_alerts", "Weather Alerts", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new Notification.Builder(this, "weather_alerts")
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_weather_alert)
                .setPriority(Notification.PRIORITY_HIGH)
                .build();

        notificationManager.notify(1, notification);
    }
}
