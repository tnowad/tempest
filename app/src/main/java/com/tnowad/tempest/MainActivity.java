package com.tnowad.tempest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> permissionRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionRequest = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // Location permission granted, proceed
                startHomeActivity();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
            }
        });

        // Check for permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            startHomeActivity();
        }
    }

    private void startHomeActivity() {
        // Proceed to Home Activity if permission is granted
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();
    }
}