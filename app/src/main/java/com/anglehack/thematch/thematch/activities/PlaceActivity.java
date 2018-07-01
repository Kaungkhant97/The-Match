package com.anglehack.thematch.thematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.fragments.PlaceFragment;

/**
 * Created by Ko Oo on 1/7/2018.
 */

public class PlaceActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_place,PlaceFragment.newInstance(String.valueOf(id)))
                .commit();
    }
}
