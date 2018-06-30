package com.anglehack.thematch.thematch.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anglehack.thematch.thematch.Manager.ChallengeManager;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.fragments.ProfileFragment;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class LoginActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ChallengeManager challengeManager;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_1, ProfileFragment.newInstance(), "aa")
                .commit();

    }

}
