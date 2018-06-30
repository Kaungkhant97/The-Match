package com.anglehack.thematch.thematch;

import android.app.Application;
import android.content.Context;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class Match extends Application {

    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
