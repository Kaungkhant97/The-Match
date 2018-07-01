package com.anglehack.thematch.thematch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anglehack.thematch.thematch.R;

class PlaceViewHolder extends RecyclerView.ViewHolder {

     final TextView textview;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        textview = itemView.findViewById(R.id.txt_place_name);
    }
}
