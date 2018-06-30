package com.anglehack.thematch.thematch.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlayerListAdapter;

import java.util.ArrayList;

public class PlayerListFragment extends Fragment {

    private RecyclerView recyclerview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  View view = inflater.inflate(R.layout.fragment_playerlist, container);
        View view = inflater.inflate(R.layout.fragment_playerlist, container);
         recyclerview = (RecyclerView)view.findViewById(R.id.recyceler_playerlist);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(new PlayerListAdapter(new ArrayList<>()));
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
