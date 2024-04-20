package com.example.harkkatyprojekti;

import java.util.ArrayList;

public class Search {


    private String cityName;

    private String timeStampString;


    public Search(String cityName, String timeStampString) {
        this.cityName = cityName;
        this.timeStampString = timeStampString;
    }


    public String getCityName() {
        return cityName;
    }

    public String getTimeStampString() {
        return timeStampString;
    }

}
