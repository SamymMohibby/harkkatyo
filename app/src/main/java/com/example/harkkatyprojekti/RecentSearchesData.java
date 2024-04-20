package com.example.harkkatyprojekti;

import java.util.ArrayList;

public class RecentSearchesData {


    private ArrayList<Search> searches = new ArrayList<>();

    private static RecentSearchesData recentSearchesData = null;

    public static RecentSearchesData getInstance(){
        if(recentSearchesData == null){
            recentSearchesData = new RecentSearchesData();
        }
        return recentSearchesData;
    }
    public void addSearch(Search search){
        searches.add(0,search);
    }

    public ArrayList<Search> getSearches() {
        return searches;
    }
}
