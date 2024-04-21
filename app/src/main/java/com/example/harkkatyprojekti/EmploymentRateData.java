package com.example.harkkatyprojekti;

public class EmploymentRateData {
    private int year;
    private double population; // Using double to handle decimal values

    public EmploymentRateData(int y, double p) {
        year = y;
        population = p;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

}

