package com.example.harkkatyprojekti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentPerustiedot extends Fragment {
    private TextView textViewPopulation;
    private TextView textViewWeather;

    private TextView textMunicipalityName;

    private TabActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perus_tiedot, container, false);




        TabActivity activity = (TabActivity) getActivity();
        String municipalityData = activity.sendMpData();
        String weatherData = activity.sendWeatherData();

        textViewPopulation = view.findViewById(R.id.textPopulation);
        textViewPopulation.setText(municipalityData);
        textMunicipalityName = view.findViewById(R.id.txtMunicipalityName);
        textViewWeather = view.findViewById(R.id.textWeather);
        textViewWeather.setText(weatherData);
        activity = (TabActivity) getActivity();
        if (activity != null) {
            textViewPopulation.setText(activity.sendMpData());
            textViewWeather.setText(activity.sendWeatherData());
            textMunicipalityName.setText(activity.getMunicipalityName());
        }

        return view;



        /* TODO mieti miten saat tuotu käyttäjän syöttämä municipality tähän ja listaa sen
        textMunicipalityName = view.findViewById(R.id.txtMunicipalityName);
        textMunicipalityName.setText(); */

    }

}