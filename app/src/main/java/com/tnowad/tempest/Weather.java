package com.tnowad.tempest;

public class Weather {
    private String hour;
    private String temperature;
    private int iconResId;

    public Weather(String hour, String temperature, int iconResId) {
        this.hour = hour;
        this.temperature = temperature;
        this.iconResId = iconResId;
    }

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
