package com.tnowad.tempest;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial switchUnits, switchAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchUnits = findViewById(R.id.switch_units);
        switchAlerts = findViewById(R.id.switch_alerts);

        // Set initial state (for demo purposes, you would usually get these from shared preferences)
        switchUnits.setChecked(true);  // °F by default
        switchAlerts.setChecked(true);  // Alerts enabled by default

        switchUnits.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save unit change (°C or °F)
            // For now, it's a simple toggle
        });

        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Enable/disable alerts based on user preference
        });
    }
}
