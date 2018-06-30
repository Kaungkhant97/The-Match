package com.anglehack.thematch.thematch.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.anglehack.thematch.thematch.R;

import butterknife.BindView;

/**
 * Created by IN-3442 on 01-Jul-18.
 */

public class TeamListActivity extends AppCompatActivity {

    @BindView(R.id.rv_team_list)
    RecyclerView rvTeamList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
    }
}
