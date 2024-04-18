package com.example.harkkatyprojekti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

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
