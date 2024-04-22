    package com.example.harkkatyprojekti;

    import android.os.Bundle;

    import androidx.fragment.app.Fragment;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;



    public class FragmentPerustiedot extends Fragment {
        private TextView textViewPopulation;
        private TextView textViewWeather;

        // new
        private TextView textViewPopulationChanges;

        // new 2

        private TextView textViewEmploymentRate;

        // new 3
        private TextView textViewJobSelf;


        private TextView textMunicipalityName;

        private TabActivity activity;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_perus_tiedot, container, false);





            TabActivity activity = (TabActivity) getActivity();
            String municipalityData = activity.sendMpData();
            String weatherData = activity.sendWeatherData();
            // new
            String changesData = activity.sendChangesData();

            // new2
            String employmentData = activity.sendEmploymentData();

            // new3
            String jobselfData = activity.sendJobSelfData();

            textViewPopulation = view.findViewById(R.id.textPopulation);
            textViewPopulation.setText(municipalityData);


            textMunicipalityName = view.findViewById(R.id.txtMunicipalityName);

            textViewWeather = view.findViewById(R.id.textWeather);
            textViewWeather.setText(weatherData);

            // new
            textViewPopulationChanges = view.findViewById(R.id.populationChanges);
            textViewPopulationChanges.setText(changesData);

            // new2
            textViewEmploymentRate = view.findViewById(R.id.textEmploymentRate);
            textViewEmploymentRate.setText(employmentData);

            // new 3
            textViewJobSelf = view.findViewById(R.id.textJobSufficiency);
            textViewJobSelf.setText(jobselfData);


            activity = (TabActivity) getActivity();
            if (activity != null) {
                String mName = activity.getMunicipalityName();
                textViewPopulation.setText(activity.sendMpData());
                textViewWeather.setText(activity.sendWeatherData());
                textMunicipalityName.setText(activity.getMunicipalityName());
                // new
                textViewPopulationChanges.setText(activity.sendChangesData());
                // new 2
                textViewEmploymentRate.setText(activity.sendEmploymentData());

                // new 3
                textViewJobSelf.setText(activity.sendJobSelfData());

                Log.d("FragmentPerustiedot", "Activity and data found: " + mName);
            } else {
                Log.d("FragmentPerustiedot", "Activity is null");

            }

            return view;




        }

    }