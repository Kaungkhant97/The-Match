package com.anglehack.thematch.thematch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.fragments.PlayerListFragment;

public class TeamCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, PlayerListFragment.newInstance()).commit();
    }
}
