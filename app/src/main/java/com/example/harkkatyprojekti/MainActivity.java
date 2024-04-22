package com.example.harkkatyprojekti;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecentSearchesAdapter.OnItemClickListener{
    private EditText txtMunicipality;
    private RecyclerView recyclerView;
    private String timeStampString;
    private String location;
    private RecentSearchesData recentSearchesData;
    private Button buttonSearch;
    private String searchTerm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMunicipality = findViewById(R.id.txtMunicipality);
        recyclerView = findViewById(R.id.rvRecentSearches);
        buttonSearch = findViewById(R.id.btnHaeTiedot);
        updateRecentSearches();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToTabactivity();
            }
        });


    }
    public void switchToTabactivity() {
        Intent intent = new Intent(this, TabActivity.class);
        Context context = this;
        MunicipalityDataRetriever mr = new MunicipalityDataRetriever();
        WeatherDataRetriever wr = new WeatherDataRetriever();
        // new
        PopulationChangesDataRetriever pcd = new PopulationChangesDataRetriever();

        // new2
        EmploymentRateDataRetriever erd = new EmploymentRateDataRetriever();

        // new3
        JobSelfSufficiencyRetriever jsf = new JobSelfSufficiencyRetriever();

        location = txtMunicipality.getText().toString().trim();
        intent.putExtra("MUNICIPALITY_NAME", location);
        Log.d("MainActivity", "Municipality Name to pass: " + location);
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<MunicipalityData> populationData = mr.getData(context, location);


                // new
                ArrayList<PopulationChangesData> populationChangesData = pcd.getData(context, location);

                // new2
                ArrayList<EmploymentRateData> employmentRateData = erd.getData(context, location);

                // new3
                ArrayList<JobSelfSufficiency> jobSelfSufficiencyData = jsf.getData(context, location);

                WeatherData weatherData = wr.getWeatherData(location);

                if (populationData == null) {
                    return;
                }
                // new
                if (populationChangesData == null) {
                    return;
                }

                // new 2
                if (employmentRateData == null) {
                    return;
                }

                // new 3
                if (jobSelfSufficiencyData == null) {
                    return;
                }


                // Sort the population data in descending order by year
                Collections.sort(populationData, new Comparator<MunicipalityData>() {
                    @Override
                    public int compare(MunicipalityData md1, MunicipalityData md2) {
                        return Integer.compare(md2.getYear(), md1.getYear()); // Note the order of md2 and md1 is switched. This is cuz, we want to list the more relevant years (the latest ones).
                        //Otherwise the list datalist would be too long.
                    }
                });
                Collections.sort(populationChangesData, new Comparator<PopulationChangesData>() {
                    @Override
                    public int compare(PopulationChangesData pcd1, PopulationChangesData pcd2) {
                        return Integer.compare(pcd2.getYear(), pcd1.getYear()); // Descending order here as well
                    }
                });
                // new
                Collections.sort(jobSelfSufficiencyData, new Comparator<JobSelfSufficiency>() {
                    @Override
                    public int compare(JobSelfSufficiency js1, JobSelfSufficiency js2) {
                        // Descending order
                        return Integer.compare(js2.getYear(), js1.getYear());
                    }
                });
                Collections.sort(employmentRateData, new Comparator<EmploymentRateData>() {
                    @Override
                    public int compare(EmploymentRateData er1, EmploymentRateData er2) {
                        // Descending order
                        return Integer.compare(er2.getYear(), er1.getYear());
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder mpData = new StringBuilder();
                        for (MunicipalityData data : populationData) {
                            mpData.append(data.getYear()).append(": ").append(data.getPopulation()).append("\n");
                        }

                        // new
                        StringBuilder pcData = new StringBuilder();
                        for (PopulationChangesData data : populationChangesData) {
                            pcData.append(data.getYear()).append(": ").append(data.getPopulation()).append("\n");
                        }

                        // new2

                        StringBuilder erData = new StringBuilder();
                        for (EmploymentRateData data : employmentRateData) {
                            erData.append(data.getYear()).append(": ").append(data.getPopulation()).append("\n");
                        }

                        // new 3

                        StringBuilder jsData = new StringBuilder();
                        for (JobSelfSufficiency data : jobSelfSufficiencyData) {
                            jsData.append(data.getYear()).append(": ").append(data.getPopulation()).append("\n");
                        }



                        String weather = weatherData.getName() + "\n" +
                                "Sää nyt: " + weatherData.getMain() + " (" + weatherData.getDescription() + ")\n" +
                                "Lämpötila: " + weatherData.getTemperature() + " K\n" +
                                "Tuulennopeus: " + weatherData.getWindSpeed() + " m/s\n";


                        intent.putExtra("population", mpData.toString());
                        intent.putExtra("weatherData", weather);
                        // new
                        intent.putExtra("populationChanges", pcData.toString());

                        // new 2
                        intent.putExtra("employmentRate", erData.toString());

                        // new 3
                        intent.putExtra("jobSelfSufficiency", jsData.toString());

                        startActivity(intent);
                    }
                });
            }
        });

        searchTerm = txtMunicipality.getText().toString().trim();
        if (!searchTerm.isEmpty()) {

            SimpleDateFormat sDF = new SimpleDateFormat("HH:mm", Locale.getDefault());
            timeStampString = sDF.format(new Date());


            Search search = new Search(location, timeStampString);
            RecentSearchesData.getInstance().addSearch(search);


            updateRecentSearches();
        } else {
            txtMunicipality.setError("Please enter a search term");
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
