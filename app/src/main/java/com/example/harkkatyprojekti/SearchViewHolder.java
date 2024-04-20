package com.example.harkkatyprojekti;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView searchTextView, timeStampTextView;


        SearchViewHolder(View itemView) {
            super(itemView);
            searchTextView = itemView.findViewById(R.id.textLocation);
            timeStampTextView = itemView.findViewById(R.id.textTimeStamp);
        }
    }


