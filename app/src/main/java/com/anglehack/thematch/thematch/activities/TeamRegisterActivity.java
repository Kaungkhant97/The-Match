package com.anglehack.thematch.thematch.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anglehack.thematch.thematch.R;

import butterknife.ButterKnife;

/**
 * Created by IN-3442 on 01-Jul-18.
 */

public class TeamRegisterActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_register);
        ButterKnife.bind(this);
    }
}
