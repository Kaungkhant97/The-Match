package com.anglehack.thematch.thematch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.Data.Place;
import com.anglehack.thematch.thematch.R;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

    private final List<Place> placelist;
   private OnClickListner clickListner;

    public PlaceAdapter(List<Place> placeList) {
        this.placelist = placeList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_layout, parent, false);
        return new PlaceViewHolder(v);
    }

    public void setClickListner(OnClickListner clickListner) {
        this.clickListner = clickListner;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.textview.setText(placelist.get(position).getName());
        holder.textviewAddress.setText(placelist.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            clickListner.onClick(placelist.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return placelist.size();
    }

    public void addAll(ArrayList<Place> list) {
        this.placelist.clear();
        this.placelist.addAll(list);
    }

    public interface OnClickListner {
        void onClick(Place place);

    }
}
