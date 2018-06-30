package com.anglehack.thematch.thematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.Match;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlayerListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class ChallengeTeamDetail extends AppCompatActivity{
    public static final String ID = "id";

    RecyclerView rvPlayer;
    PlayerListAdapter mPlayerListAdapter;
    Button btnChallenge;

    int id ;

    private List<Player> mTeamPlayerList = new ArrayList<>();

    public static Intent getInstance(int id){
        Intent in = new Intent(Match.getContext(), ChallengeTeamDetail.class);
        in.putExtra(ID, id);
        return in;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);

        Intent in = getIntent();
        id = in.getIntExtra(ID, 0);

        //Get data

        rvPlayer = findViewById(R.id.rv_team_player_list);
        btnChallenge = findViewById(R.id.btn_challenge);
        rvPlayer.setAdapter(mPlayerListAdapter);
        rvPlayer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        btnChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
