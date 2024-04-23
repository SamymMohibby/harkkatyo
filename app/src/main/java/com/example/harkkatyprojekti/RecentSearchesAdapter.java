package com.example.harkkatyprojekti;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecentSearchesAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private Context context;
    private final ArrayList<Search> recentSearches;
    private String cityName;

    private OnItemClickListener listener;



    public RecentSearchesAdapter(Context context, ArrayList<Search> searches, OnItemClickListener listener) {
        this.context = context;
        this.recentSearches = searches;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String cityName);
    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recent_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {
        final Search search = recentSearches.get(position);
        holder.timeStampTextView.setText(search.getTimeStampString());
        holder.searchTextView.setText(search.getCityName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(recentSearches.get(position).getCityName());
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return recentSearches.size();
    }
}