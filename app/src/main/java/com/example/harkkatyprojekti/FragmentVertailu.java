package com.example.harkkatyprojekti;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FragmentVertailu extends Fragment {
    private TextView txtPopulationData1, txtWeatherData1, txtPopulationChange1;
    private TextView txtPopulationData2, txtWeatherData2, txtPopulationChange2;
    private EditText editTextLocation1, editTextLocation2;
    private Button btnCompare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vertailu, container, false);


        txtPopulationData1 = view.findViewById(R.id.txtPopulationCompare1);
        txtWeatherData1 = view.findViewById(R.id.txtWeatherCompare1);
        txtPopulationChange1 = view.findViewById(R.id.txtPopulationChangeCompare1);


        txtPopulationData2 = view.findViewById(R.id.txtPopulationCompare2);
        txtWeatherData2 = view.findViewById(R.id.txtWeatherCompare2);
        txtPopulationChange2 = view.findViewById(R.id.txtPopulationChangeCompare2);


        editTextLocation1 = view.findViewById(R.id.txtCompare);
        editTextLocation2 = view.findViewById(R.id.txtCompare2);

        btnCompare = view.findViewById(R.id.btnCompare);

        btnCompare.setOnClickListener(v -> performComparison());

        return view;
    }

    private void performComparison() {
        Context context = getContext();
        String location1 = editTextLocation1.getText().toString().trim();
        String location2 = editTextLocation2.getText().toString().trim();

        if (location1.isEmpty() || location2.isEmpty()) {
            if (location1.isEmpty()) editTextLocation1.setError("Please enter a location");
            if (location2.isEmpty()) editTextLocation2.setError("Please enter a location");
            return;
        }

        MunicipalityDataRetriever mr = new MunicipalityDataRetriever();
        WeatherDataRetriever wr = new WeatherDataRetriever();
        PopulationChangesDataRetriever pc = new PopulationChangesDataRetriever();

        ExecutorService service = Executors.newFixedThreadPool(2); // Use two threads for simultaneous fetches

        // eka kunta
        service.execute(() -> fetchData(context, location1, txtPopulationData1, txtWeatherData1, txtPopulationChange1, mr, wr, pc));

        // toka kunta
        service.execute(() -> fetchData(context, location2, txtPopulationData2, txtWeatherData2, txtPopulationChange2, mr, wr, pc));
    }

    private void fetchData(Context context, String location, TextView populationView, TextView weatherView, TextView populationChangeView,
                           MunicipalityDataRetriever mr, WeatherDataRetriever wr, PopulationChangesDataRetriever pc) {
        ArrayList<MunicipalityData> populationData = mr.getData(context, location);
        ArrayList<PopulationChangesData> populationChangesData = pc.getData(context, location);
        WeatherData weatherData = wr.getWeatherData(location);

        if (populationData == null || weatherData == null || populationChangesData == null) {
            getActivity().runOnUiThread(() -> Toast.makeText(context, "Failed to fetch data for " + location, Toast.LENGTH_SHORT).show());
            return;
        }

        // Sort data by year in descending order
        Collections.sort(populationData, (md1, md2) -> Integer.compare(md2.getYear(), md1.getYear()));
        Collections.sort(populationChangesData, (pcd1, pcd2) -> Integer.compare(pcd2.getYear(), pcd1.getYear()));

        getActivity().runOnUiThread(() -> {
            StringBuilder populationStringBuilder = new StringBuilder();
            for (MunicipalityData data : populationData) {
                populationStringBuilder.append(data.getYear()).append(": ").append(data.getPopulation()).append("\n");
            }
            populationView.setText(populationStringBuilder.toString());

            StringBuilder populationChangesStringBuilder = new StringBuilder();
            for (PopulationChangesData data : populationChangesData) {
                populationChangesStringBuilder.append(data.getYear()).append(": ").append(data.getPopulation()).append("\n");
            }
            populationChangeView.setText(populationChangesStringBuilder.toString());

            String weatherString = String.format("%s\nSää nyt: %s (%s)\nLämpötila: %s K\nTuulennopeus: %s m/s",
                    weatherData.getName(), weatherData.getMain(), weatherData.getDescription(),
                    weatherData.getTemperature(), weatherData.getWindSpeed());
                weatherView.setText(weatherString);

        });
    }
}
