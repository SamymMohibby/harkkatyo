package com.example.harkkatyprojekti;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;

public class TabActivity extends AppCompatActivity {

    private String municipalityData;
    private String weatherData;
    private String municipalityName;

    // new
    private String changesData;

    // new 2

    private String employmentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        municipalityName = getIntent().getStringExtra("MUNICIPALITY_NAME");
        Log.d("TabActivity", "Received Municipality Name: " + municipalityName);


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 fragmentArea = findViewById(R.id.viewArea);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(this);
        fragmentArea.setAdapter(tabPagerAdapter);

        municipalityData = getIntent().getStringExtra("population");
        weatherData = getIntent().getStringExtra("weatherData");
        // new
        changesData = getIntent().getStringExtra("populationChanges");

        // new 2
        employmentData = getIntent().getStringExtra("employmentRate");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentArea.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fragmentArea.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
    public String getMunicipalityName() {
        return municipalityName;
    }

    public String sendMpData() {
        return municipalityData;
    }

    public String sendWeatherData() {
        return weatherData;
    }
    // new
    public String sendChangesData() {
        return changesData;
    }

    public String sendEmploymentData() {
        return employmentData;
    }

}