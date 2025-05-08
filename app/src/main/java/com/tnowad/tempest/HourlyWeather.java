package com.tnowad.tempest;

public class HourlyWeather {
    private final String hour;
    private final String temp;
    private final int iconResId;
    private final double windSpeed;
    private final int precipitationProbability;

    public HourlyWeather(String hour, String temp, int iconResId, double windSpeed, int precipitationProbability) {
        this.hour = hour;
        this.temp = temp;
        this.iconResId = iconResId;
        this.windSpeed = windSpeed;
        this.precipitationProbability = precipitationProbability;
    }

    public String getHour() { return hour; }
    public String getTemp() { return temp; }
    public int getIconResId() { return iconResId; }
    public double getWindSpeed() { return windSpeed; }
    public int getPrecipitationProbability() { return precipitationProbability; }
}
