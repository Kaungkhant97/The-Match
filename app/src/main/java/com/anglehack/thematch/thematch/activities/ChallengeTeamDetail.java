package com.anglehack.thematch.thematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Di.component.DaggerManagerComponent;
import com.anglehack.thematch.thematch.Manager.TeamDetailManager;
import com.anglehack.thematch.thematch.Match;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlayerListAdapter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class ChallengeTeamDetail extends AppCompatActivity {
    public static final String ID = "id";

    int id;
    @BindView(R.id.img_team_logo)
    ImageView imgTeamLogo;
    @BindView(R.id.txt_team_id)
    TextView txtTeamId;
    @BindView(R.id.btn_challenge)
    Button btnChallenge;
    @BindView(R.id.rv_team_player_list)
    RecyclerView rvTeamPlayerList;

    private PlayerListAdapter adapter;

    public static Intent getInstance(int id) {
        Intent in = new Intent(Match.getContext(), ChallengeTeamDetail.class);
        in.putExtra(ID, id);
        return in;
    }

    @Inject
    TeamDetailManager teamDetailManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);
        ButterKnife.bind(this, this);

        DaggerManagerComponent.builder().build().inject(this);

        Intent in = getIntent();
        id = in.getIntExtra(ID, 0);

        rvTeamPlayerList.setLayoutManager(new LinearLayoutManager(this));


        teamDetailManager.getTeamDetail("1").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {

            adapter = new PlayerListAdapter(list.getPlayers());
            rvTeamPlayerList.setAdapter(adapter);

            if(getSupportActionBar() != null)
            {
                getSupportActionBar().setTitle(list.getName());
            }

            Picasso.with(this).load(list.getLogoUrl()).into(imgTeamLogo);
            txtTeamId.setText(String.valueOf(list.getId()));
        });
    }

    @OnClick(R.id.btn_challenge)
    public void onViewClicked() {
    }
}
