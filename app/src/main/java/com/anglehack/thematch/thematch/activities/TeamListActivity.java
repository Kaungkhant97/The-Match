package com.anglehack.thematch.thematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.Di.component.DaggerManagerComponent;
import com.anglehack.thematch.thematch.Manager.TeamManager;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.ChallengeAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by IN-3442 on 01-Jul-18.
 */

public class TeamListActivity extends AppCompatActivity {

    @Inject
    TeamManager teamManager;

    @BindView(R.id.rv_team_list)
    RecyclerView rvTeamList;

    @BindView(R.id.btn_team_list)
    Button btnTeamList;

    private ArrayList<Team> teamList = new ArrayList<>();

    String imageurl = "https://api.adorable.io/avatars/3";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        ButterKnife.bind(this);

        //teamList.add(new Team(1, "Team 1", imageurl));
        //teamList.add(new Team(2, "Team 1", imageurl));
        //teamList.add(new Team(3, "Team 1", imageurl));

        DaggerManagerComponent.builder().build().inject(this);
        rvTeamList.setLayoutManager(new GridLayoutManager(this, 3));

        teamManager.getPlayerTeamList("3").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(List->{
                    ChallengeAdapter adapter = new ChallengeAdapter(this, List);

                    rvTeamList.setAdapter(adapter);
                },e->{

                });


        btnTeamList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), TeamRegisterActivity.class);
                startActivity(in);
            }
        });
    }
}
