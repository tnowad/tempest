package com.tnowad.tempest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityResultLauncher<String> permissionRequestLocation;
    private ActivityResultLauncher<String> permissionRequestNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: MainActivity launched");

        permissionRequestLocation = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Log.d(TAG, "Location permission granted");
                        checkNotificationPermissionAndStart();
                    } else {
                        Log.w(TAG, "Location permission denied");
                        Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
                    }
                });

        permissionRequestNotification = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Log.d(TAG, "Notification permission granted");
                        startHomeActivity();
                    } else {
                        Log.w(TAG, "Notification permission denied");
                        Toast.makeText(this, "Notification permission required", Toast.LENGTH_SHORT).show();
                    }
                });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Requesting location permission...");
            permissionRequestLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            Log.d(TAG, "Location permission already granted");
            checkNotificationPermissionAndStart();
        }
    }

    private void checkNotificationPermissionAndStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Requesting notification permission...");
                permissionRequestNotification.launch(Manifest.permission.POST_NOTIFICATIONS);
            } else {
                Log.d(TAG, "Notification permission already granted");
                startHomeActivity();
            }
        } else {
            startHomeActivity();
        }
    }

    private void startHomeActivity() {
        Log.d(TAG, "Launching HomeActivity");
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
        finish();
    }
}
