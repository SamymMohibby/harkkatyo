package com.example.harkkatyprojekti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentPerustiedot extends Fragment {
    private TextView textViewPopulation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perus_tiedot, container, false);
        textViewPopulation = view.findViewById(R.id.textPopulation);
        return view;
    }

    public void updatePopulationData(String data) {
        if (textViewPopulation != null) {
            textViewPopulation.setText(data);
        }
    }




}