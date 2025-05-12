package com.tnowad.tempest;

import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String tileUrl = "https://tile.openweathermap.org/map/clouds_new/{z}/{x}/{y}.png?appid=89f7f2ffd6532a82fdc75a27277084da";

        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                try {
                    return new URL(tileUrl.replace("{z}", "" + zoom)
                            .replace("{x}", "" + x)
                            .replace("{y}", "" + y));
                } catch (MalformedURLException e) {
                    return null;
                }
            }
        };

        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions()
                .tileProvider(tileProvider)
                .zIndex(1);

        googleMap.addTileOverlay(tileOverlayOptions);
    }
}
