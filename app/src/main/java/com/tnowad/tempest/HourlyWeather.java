package com.tnowad.tempest;

public class HourlyWeather {
    private String hour;
    private String temperature;
    private int iconResId;

    // Constructor for HourlyWeather
    public HourlyWeather(String hour, String temperature, int iconResId) {
        this.hour = hour;
        this.temperature = temperature;
        this.iconResId = iconResId;
    }

    // Getter methods
    public String getHour() {
        return hour;
    }

    public String getTemperature() {
        return temperature;
    }

    public int getIconResId() {
        return iconResId;
    }
}
