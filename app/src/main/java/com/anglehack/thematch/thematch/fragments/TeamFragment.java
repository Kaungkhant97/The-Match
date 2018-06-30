package com.anglehack.thematch.thematch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlayerFragmentPagerAdapter;
import com.anglehack.thematch.thematch.adapters.PlayerListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class TeamFragment extends Fragment {
    RecyclerView rvPlayer;
    PlayerListAdapter mPlayerListAdapter;

    private List<Player> mTeamPlayerList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        if(mTeamPlayerList.size() == 0){
            mTeamPlayerList.add(new Player("Nyi", "NN-1", "23"));
            mTeamPlayerList.add(new Player("Nyi", "NN-1", "23"));
            mTeamPlayerList.add(new Player("Nyi", "NN-1", "23"));
            mTeamPlayerList.add(new Player("Nyi", "NN-1", "23"));
            mTeamPlayerList.add(new Player("Nyi", "NN-1", "23"));
        }
        mPlayerListAdapter = new PlayerListAdapter(getLayoutInflater(), mTeamPlayerList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        rvPlayer = view.findViewById(R.id.rv_team_player_list);
        rvPlayer.setAdapter(mPlayerListAdapter);
        rvPlayer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }
}
