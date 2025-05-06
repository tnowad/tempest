package com.tnowad.tempest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("forecast")
    Call<WeatherResponse> getForecast(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("hourly") String hourly,
            @Query("daily") String daily,
            @Query("current_weather") boolean currentWeather,
            @Query("timezone") String timezone
    );
}
