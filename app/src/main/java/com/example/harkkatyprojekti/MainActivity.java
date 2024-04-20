package com.example.harkkatyprojekti;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements RecentSearchesAdapter.OnItemClickListener{
    private EditText txtKunta;
    private RecyclerView recyclerView;
    private RecentSearchesAdapter adapter; // Ensure this is the adapter you use everywhere in this class

    private String timeStampString;

    private String location ;
    private RecentSearchesData recentSearchesData;
    private Button buttonSearch;
    private String searchTerm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtKunta = findViewById(R.id.txtKunta);
        recyclerView = findViewById(R.id.rvViimeksiHaetut);

        buttonSearch = findViewById(R.id.btnHaeTiedot);



        updateRecentSearches();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Nappia painettu!", Toast.LENGTH_SHORT).show();
                switchToTabactivity();
            }
        });


    }

    public void switchToTabactivity() {


        // dadada
        Context context = this;

        MunicipalityDataRetriever mr = new MunicipalityDataRetriever();
        WeatherDataRetriever wr = new WeatherDataRetriever();

         location = txtKunta.getText().toString().trim();

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

        searchTerm = txtKunta.getText().toString().trim();
        if (!searchTerm.isEmpty()) {

            SimpleDateFormat sDF = new SimpleDateFormat("hh:mm");
            timeStampString = sDF.format(new Date());


            Search search = new Search(location, timeStampString);
            RecentSearchesData.getInstance().addSearch(search);


            updateRecentSearches();
            Intent intent = new Intent(this, TabActivity.class);
            intent.putExtra("SEARCH_TERM", searchTerm);
            startActivity(intent);
        } else {
            txtKunta.setError("Please enter a search term");
        }
    }
    @Override
    public void onItemClick(String cityName) {
        location = cityName;
        searchTerm = cityName;

        switchToTabactivity();
    }
    private void updateRecentSearches() {


        if (recyclerView != null) {
            recentSearchesData = RecentSearchesData.getInstance();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new RecentSearchesAdapter(this, recentSearchesData.getSearches(), this));
        }
    }


}
