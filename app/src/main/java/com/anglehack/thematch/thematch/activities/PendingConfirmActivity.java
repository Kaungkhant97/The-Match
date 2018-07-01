package com.anglehack.thematch.thematch.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Data.Challenge;
import com.anglehack.thematch.thematch.Match;
import com.anglehack.thematch.thematch.R;

import butterknife.ButterKnife;

/**
 * Created by IN-3442 on 01-Jul-18.
 */

public class PendingConfirmActivity extends AppCompatActivity {

    TextView tvTeam1Name, tvTeam2Name, tvDate, tvTime, tvPlace;
    Button btnComfirmed, btnDecline;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_confirm);
        ButterKnife.bind(this);


        tvTeam1Name = findViewById(R.id.tv_team1_name);
        tvTeam2Name = findViewById(R.id.tv_team2_name);
        tvDate = findViewById(R.id.tv_chalenge_date);
        tvTime = findViewById(R.id.tv_chalenge_time);
        tvPlace = findViewById(R.id.tv_chalenge_place);
        btnComfirmed = findViewById(R.id.btn_accept);
        btnDecline = findViewById(R.id.btn_decline);

        Challenge challenge = Match.getChallenge();
        tvTeam1Name.setText(challenge.getAcceptedTeamName());
        tvTeam2Name.setText(challenge.getChallengerTeamName());
        tvDate.setText(challenge.getReservedTime());
        tvPlace.setText(challenge.getPlaceID() + "");

        btnComfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
