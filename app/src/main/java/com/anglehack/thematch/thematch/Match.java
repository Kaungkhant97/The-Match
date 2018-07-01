package com.anglehack.thematch.thematch;

import android.app.Application;
import android.content.Context;

import com.anglehack.thematch.thematch.Data.Challenge;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class Match extends Application {

    private static Context context;
    private static Challenge challenge;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static Challenge getChallenge() {
        return challenge;
    }

    public static void setChallenge(Challenge challenge) {
        Match.challenge = challenge;
    }
}
