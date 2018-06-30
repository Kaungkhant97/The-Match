package com.anglehack.thematch.thematch;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anglehack.thematch.thematch.Manager.ChallengeManager;
import com.anglehack.thematch.thematch.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        ChallengeManager challengeManager;

    }
}
