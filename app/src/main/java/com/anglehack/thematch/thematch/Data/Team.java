package com.anglehack.thematch.thematch.Data;

import android.support.annotation.IdRes;

public class Team {

    private String name;
    private int profile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile( @IdRes int profile) {
        this.profile = profile;
    }
}
