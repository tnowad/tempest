package com.tnowad.tempest;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView tvCity, tvTemp, tvHumidity, tvWind;
    private ImageView imgWeather;
    private MaterialButton btnForecast;

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

        // Set static data for demonstration (replace with real data from API)
        tvCity.setText("Hanoi");
        tvTemp.setText("28°C");
        tvHumidity.setText("Humidity: 60%");
        tvWind.setText("Wind: 10 km/h");
        imgWeather.setImageResource(R.drawable.ic_weather_sunny);

        btnForecast.setOnClickListener(v -> {
            // Open hourly/daily forecast
            startActivity(new Intent(HomeActivity.this, HourlyForecastActivity.class));
        });
    }
}
