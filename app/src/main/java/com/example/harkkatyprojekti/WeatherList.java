package com.example.harkkatyprojekti;

import java.util.ArrayList;

public class WeatherList {

    private ArrayList<WeatherData> weather = new ArrayList<>();

    private static WeatherList weatherDataStorage = null;

    public static WeatherList getInstance() {
        if (weatherDataStorage == null) {
            weatherDataStorage = new WeatherList();
        }
        return weatherDataStorage;
    }
    public void addWeatherData(WeatherData weatherData) {
        weather.add(weatherData);
    }

    public ArrayList<WeatherData> getWeathers() {
        return weather;
    }
}
