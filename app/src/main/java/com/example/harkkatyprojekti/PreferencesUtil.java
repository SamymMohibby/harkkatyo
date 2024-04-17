package com.example.harkkatyprojekti;

import android.content.Context;
import android.content.SharedPreferences;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreferencesUtil {

    private static final String PREFS_NAME = "com.example.harkkatyprojekti";
    private static final String RECENT_SEARCHES_KEY = "recent_searches";

    public static void saveRecentSearches(Context context, List<String> searches) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(searches);
            editor.putString(RECENT_SEARCHES_KEY, json);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getRecentSearches(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(RECENT_SEARCHES_KEY, null);
        if (json == null) {
            return new ArrayList<>();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
