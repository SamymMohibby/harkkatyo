package com.example.harkkatyprojekti;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private EditText txtKunta;
    private RecyclerView recyclerView;
    private RecentSearchesAdapter adapter; // Ensure this is the adapter you use everywhere in this class
        private List<String> recentSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtKunta = findViewById(R.id.txtKunta);
        recyclerView = findViewById(R.id.rvViimeksiHaetut);

        // First, initialize the recentSearchList
        recentSearchList = PreferencesUtil.getRecentSearches(this);
        if (recentSearchList == null) {
            recentSearchList = new ArrayList<>(); // Initialize as empty if null
        }

        // Then create the adapter with the (now initialized) list
        adapter = new RecentSearchesAdapter(this, recentSearchList);
        recyclerView.setAdapter(adapter);
    }

    public void switchToTabactivity(View view) {
        Context context = this;

        MunicipalityDataRetriever mr = new MunicipalityDataRetriever();
        WeatherDataRetriever wr = new WeatherDataRetriever();

        String location = txtKunta.getText().toString(); // Make sure this refers to your EditText where you enter the location

        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(() -> {
            ArrayList<MunicipalityData> populationData = mr.getData(context, location);
            WeatherData weatherData = wr.getWeatherData(location);

            if (populationData == null) {
                return;
            }
            runOnUiThread(() -> {
                StringBuilder stringBuilder = new StringBuilder();
                for (MunicipalityData data : populationData) {
                    stringBuilder.append(data.getYear()).append(": ").append(data.getPopulation()).append("\n");
                }
                FragmentPerustiedot fragment = (FragmentPerustiedot) getSupportFragmentManager().findFragmentById(R.id.fragmentPerustiedot);
                if (fragment != null) {
                    fragment.updatePopulationData(stringBuilder.toString());
                }
            });
        });

        String searchTerm = txtKunta.getText().toString().trim();
        if (!searchTerm.isEmpty()) {
            updateRecentSearches(searchTerm);
            Intent intent = new Intent(this, TabActivity.class);
            intent.putExtra("SEARCH_TERM", searchTerm);
            startActivity(intent);
        }
    }

    private void updateRecentSearches(String searchTerm) {
        recentSearchList.add(0, searchTerm);

        if (recentSearchList.size() > 5) {
            recentSearchList.remove(5);
        }
        PreferencesUtil.saveRecentSearches(this, recentSearchList);
        adapter.notifyDataSetChanged();
    }
}
