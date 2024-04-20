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

    private TabActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perus_tiedot, container, false);



        TabActivity activity = (TabActivity) getActivity();
        String municipalityData = activity.sendMpData();

        textViewPopulation = view.findViewById(R.id.textPopulation);
        textViewPopulation.setText(municipalityData);


        return view;
    }

}