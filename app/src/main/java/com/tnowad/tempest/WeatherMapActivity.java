package com.tnowad.tempest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private TileOverlay currentOverlay;
    private final String apiKey = "89f7f2ffd6532a82fdc75a27277084da";
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_map);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Spinner spinner = findViewById(R.id.layer_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (map != null) {
                    String layer = switch (position) {
                        case 0 -> "clouds_new";
                        case 1 -> "temp_new";
                        case 2 -> "precipitation_new";
                        default -> "clouds_new";
                    };
                    updateTileOverlay(layer);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        map.setMyLocationEnabled(true);
        showCurrentLocation();

        updateTileOverlay("clouds_new"); // Default layer
    }

    private void updateTileOverlay(String layerType) {
        if (currentOverlay != null) {
            currentOverlay.remove();
        }

        String tileUrl = "https://tile.openweathermap.org/map/" + layerType + "/{z}/{x}/{y}.png?appid=" + apiKey;

        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                try {
                    return new URL(tileUrl
                            .replace("{z}", String.valueOf(zoom))
                            .replace("{x}", String.valueOf(x))
                            .replace("{y}", String.valueOf(y)));
                } catch (MalformedURLException e) {
                    return null;
                }
            }
        };

        currentOverlay = map.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider)
                .zIndex(1));
    }

    @SuppressLint("MissingPermission")
    private void showCurrentLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10));
            }
        });
    }
}
