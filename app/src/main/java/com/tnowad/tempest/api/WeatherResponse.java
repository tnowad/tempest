package com.tnowad.tempest.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {

    @SerializedName("latitude")
    public double latitude;

    @SerializedName("longitude")
    public double longitude;

    @SerializedName("current_weather")
    public CurrentWeather currentWeather;

    @SerializedName("hourly")
    public Hourly hourly;

    @SerializedName("daily")
    public Daily daily;

    public static class CurrentWeather {
        @SerializedName("temperature")
        public double temperature;

        @SerializedName("windspeed")
        public double windspeed;

        @SerializedName("weathercode")
        public int weathercode;

        @SerializedName("time")
        public String time;
    }

    public static class Hourly {
        @SerializedName("time")
        public List<String> time;

        @SerializedName("temperature_2m")
        public List<Double> temperature;

        @SerializedName("precipitation_probability")
        public List<Integer> precipitationProbability;

        @SerializedName("weathercode")
        public List<Integer> weatherCode;

        @SerializedName("windspeed_10m")
        public List<Double> windSpeed;
    }

    public static class Daily {
        @SerializedName("temperature_2m_max")
        public List<Double> maxTemp;

        @SerializedName("temperature_2m_min")
        public List<Double> minTemp;

        @SerializedName("precipitation_sum")
        public List<Double> precipitationSum;

        @SerializedName("time")
        public List<String> date;
    }
}
