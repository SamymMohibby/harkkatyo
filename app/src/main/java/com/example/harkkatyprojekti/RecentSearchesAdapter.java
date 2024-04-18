package com.example.harkkatyprojekti;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecentSearchesAdapter extends RecyclerView.Adapter<RecentSearchesAdapter.SearchViewHolder> {
    private Context context;
    private final ArrayList<String> recentSearches;
    private final LayoutInflater mInflater;

    public RecentSearchesAdapter(Context context, List<String> recentSearches) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.recentSearches = (ArrayList<String>) recentSearches;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_recent_search, parent, false);
        return new SearchViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        String searchTerm = recentSearches.get(position);
        Log.d("RecentSearchAdapter", "Binding view holder for position " + position + " with search term: " + searchTerm);
        holder.searchTextView.setText(searchTerm);
    }

    @Override
    public int getItemCount() {
        return recentSearches.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView searchTextView;

        SearchViewHolder(View itemView) {
            super(itemView);
            searchTextView = itemView.findViewById(R.id.txtRecentSearch);
        }
    }
}