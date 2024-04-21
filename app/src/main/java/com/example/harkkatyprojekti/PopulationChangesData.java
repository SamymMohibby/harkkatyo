package com.example.harkkatyprojekti;

public class PopulationChangesData {
    private int year;
    private int population;

    public PopulationChangesData(int y, int p) {
        year = y;
        population = p;
    }


    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getPopulation() {
        return population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

}
