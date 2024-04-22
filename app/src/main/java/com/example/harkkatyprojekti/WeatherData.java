package com.example.harkkatyprojekti;

import android.util.Log;

import java.util.Locale;

public class WeatherData {
    private String name;
    private String main;
    private String description;
    private String temperature;
    private String windSpeed;

    public WeatherData(String n, String m, String d, String t, String w) {
        name = n;
        main = m;
        description = d;
        temperature = t;
        windSpeed = w;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureCelsius() {
        try {
            // Switches from kelvin to celsius
            double kelvin = Double.parseDouble(temperature);
            double celsius = kelvin - 273.15;
            // Rounds it and changes it to String
            return String.format(Locale.getDefault(), "%.2f", celsius);
        } catch (NumberFormatException e) {
            Log.e("WeatherData", "Failed to parse temperature", e);
            return "N/A"; //
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }


    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
}