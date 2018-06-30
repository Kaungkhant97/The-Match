package com.anglehack.thematch.thematch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.Data.Challenge;
import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlayerChallengeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerChallenge extends Fragment {
    private RecyclerView rvPlayerChallenge;
    PlayerChallengeAdapter playerChallengeAdapter;
    List<Challenge> challengeList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        challengeList.add(new Challenge(1, "Team Lin", "Team Nyi", 1, "2018.6.13 9:00pm", 1, 50, 1));
        challengeList.add(new Challenge(1, "Team Lin", "Team Nyi", 1, "2018.6.13 9:00pm", 1, 50, 1));
        challengeList.add(new Challenge(1, "Team Lin", "Team Nyi", 1, "2018.6.13 9:00pm", 1, 50, 1));

        playerChallengeAdapter = new PlayerChallengeAdapter(getLayoutInflater(), challengeList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_challenge, container, false);
        rvPlayerChallenge = view.findViewById(R.id.rv_team_player_challenge_list);
        rvPlayerChallenge.setAdapter(playerChallengeAdapter);
        rvPlayerChallenge.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }
}
