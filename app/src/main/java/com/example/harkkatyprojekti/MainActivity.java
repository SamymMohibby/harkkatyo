package com.example.harkkatyprojekti;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // TODO vaihoin edittextin id: aluksi oli txtKunta nyt on txtMunicipality
    // TODO koska kaikki koodit pitää olla kai kirjoitettu englanniksi
    private EditText txtMunicipality;
    private RecyclerView recyclerView;
    private RecentSearchesAdapter adapter; // Ensure this is the adapter you use everywhere in this class
    private List<String> recentSearchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMunicipality = findViewById(R.id.txtMunicipality);
        recyclerView = findViewById(R.id.rvViimeksiHaetut);
        adapter = new RecentSearchesAdapter(this, recentSearchList);
        recyclerView.setAdapter(adapter);


        // Initialize the recentSearchList from SharedPreferences
        recentSearchList = PreferencesUtil.getRecentSearches(this);
        if (recentSearchList == null) {
            recentSearchList = new ArrayList<>(); // Initialize as empty if null
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new RecentSearchesAdapter(this, recentSearchList);
        recyclerView.setAdapter(adapter);
    }

    public void switchToTabactivity(View view) {
        // vaihtaa aktiviteetti tabaktiviteetille
        Intent intent = new Intent(this, TabActivity.class);

        Context context = this;

        MunicipalityDataRetriever mr = new MunicipalityDataRetriever();
        WeatherDataRetriever wr = new WeatherDataRetriever();

        // hakee käyttäjän syöttämä kunta
        String location = txtMunicipality.getText().toString();


        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(new Runnable() {
            @Override
            public void run() {

                // luodaan olion ja samalla välitetään tämän luokan context ja käyttäjän haluama kunta
                ArrayList<MunicipalityData> populationData = mr.getData(context, location);
                WeatherData weatherData = wr.getWeatherData(location);

                // jos käyttäjän syöttämä kunta on tuntematon, ohjelma kaatu
                if (populationData == null) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String mpData = "";
                        for (MunicipalityData data : populationData) {
                            mpData =  mpData + data.getYear() + ": " + data.getPopulation() + "\n";
                        }

                        Bundle bundle = new Bundle();

                        // lisätään bundleen, municipalitydata ja weatherdata
                        bundle.putString("population", mpData);

                        // välitetään tiedot toiselle aktiviteetille
                        intent.putExtra("population", mpData);


                        startActivity(intent);


                    }
                });



            }
        });




        // pääty tähän

       /* String searchTerm = txtMunicipality.getText().toString().trim();
        if (!searchTerm.isEmpty()) {
            updateRecentSearches(searchTerm);
            Intent intent = new Intent(this, TabActivity.class);
            intent.putExtra("SEARCH_TERM", searchTerm);
            startActivity(intent);
        } else {
            txtMunicipality.setError("Please enter a search term");
        } */

    }

    /* private void updateRecentSearches(String searchTerm) {
        recentSearchList.add(0, searchTerm);

        // Limit to any number of recent searches you prefer, here no limit
        if (recentSearchList.size() > 10) {
            recentSearchList.remove(10);
        }
        PreferencesUtil.saveRecentSearches(this, recentSearchList);
        adapter.notifyDataSetChanged();
    } */

}
