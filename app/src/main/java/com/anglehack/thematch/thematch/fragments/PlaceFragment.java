package com.anglehack.thematch.thematch.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.Data.Place;
import com.anglehack.thematch.thematch.Di.component.DaggerManagerComponent;
import com.anglehack.thematch.thematch.Manager.PlaceManager;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlaceAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlaceFragment extends Fragment implements PlaceAdapter.OnClickListner{

    private RecyclerView recycler_places;

    @Inject
    PlaceManager placeManager;
    private PlaceAdapter placeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.place_fragment,container,false);
        recycler_places = v.findViewById(R.id.recycler_place);
        this.placeAdapter = new PlaceAdapter(new ArrayList<>());
        recycler_places.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_places.setAdapter(placeAdapter);

        DaggerManagerComponent.builder().build().inject(this);
        placeManager.getplace().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list ->{
                    placeAdapter.addAll(list);
                    placeAdapter.notifyDataSetChanged();
                });
        return  v ;
    }

    public static Fragment newInstance() {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(Place place) {

    }
}
