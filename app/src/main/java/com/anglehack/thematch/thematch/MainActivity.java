package com.anglehack.thematch.thematch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anglehack.thematch.thematch.Manager.TeamManager;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    TeamManager manager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DaggerTeamComponent.builder().build().inject(this);



    }
}
