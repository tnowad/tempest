package com.tnowad.tempest;

public class DailyWeather {
    private final String day;
    private final String tempRange;
    private final int iconResId;
    private final double precipitation;

    public DailyWeather(String day, String tempRange, int iconResId, double precipitation) {
        this.day = day;
        this.tempRange = tempRange;
        this.iconResId = iconResId;
        this.precipitation = precipitation;
    }

    public String getDay() {
        return day;
    }

    public String getTempRange() {
        return tempRange;
    }

    public int getIconResId() {
        return iconResId;
    }

    public double getPrecipitation() {
        return precipitation;
    }
}
