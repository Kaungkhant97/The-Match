package com.anglehack.thematch.thematch.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.Challenge;
import com.anglehack.thematch.thematch.Di.component.DaggerManagerComponent;
import com.anglehack.thematch.thematch.Di.module.NetworkModule;
import com.anglehack.thematch.thematch.Match;
import com.anglehack.thematch.thematch.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.anglehack.thematch.thematch.Match.getContext;

/**
 * Created by IN-3442 on 01-Jul-18.
 */

public class PendingConfirmActivity extends AppCompatActivity {

    @Inject
    RetrofitService retrofitService;

    TextView tvTeam1Name, tvTeam2Name, tvDate, tvTime, tvPlace;
    Button btnComfirmed, btnDecline;
    ImageView ivMyTeam, ivOtherTeam;

    String msg;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_confirm);
        ButterKnife.bind(this);

        DaggerManagerComponent.builder().build().inject(this);


        tvTeam1Name = findViewById(R.id.tv_team1_name);
        tvTeam2Name = findViewById(R.id.tv_team2_name);
        tvDate = findViewById(R.id.tv_chalenge_date);
        tvTime = findViewById(R.id.tv_chalenge_time);
        tvPlace = findViewById(R.id.tv_chalenge_place);
        btnComfirmed = findViewById(R.id.btn_accept);
        btnDecline = findViewById(R.id.btn_decline);

        ivMyTeam = findViewById(R.id.iv_my_team);
        ivOtherTeam = findViewById(R.id.iv_other_team);

        Challenge challenge = Match.getChallenge();
        tvTeam1Name.setText(challenge.getAcceptedTeamName());
        tvTeam2Name.setText(challenge.getChallengerTeamName());
        tvDate.setText(challenge.getReservedTime());
        tvPlace.setText(challenge.getPlace() + "");

        Picasso.with(getContext()).load(challenge.getAcceptedTeamLogo()).into(ivOtherTeam);
        Picasso.with(getContext()).load(challenge.getChallengerTeamLogo()).into(ivMyTeam);

        btnComfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitService.accepted(String.valueOf(challenge.getId()));
                msg = "You accepted the challenge";
                showDialog();
            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitService.decline(String.valueOf(challenge.getId()));
                msg = "You decline the challenge";
                showDialog();
            }
        });
    }

    private void showDialog(){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(msg)
                .setCancelable(false)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
