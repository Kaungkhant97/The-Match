package com.anglehack.thematch.thematch.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anglehack.thematch.thematch.fragments.PlayerChallenge;
import com.anglehack.thematch.thematch.fragments.TeamFragment;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerFragmentPagerAdapter extends FragmentPagerAdapter {

    public PlayerFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new PlayerChallenge();
            case 1: return new PlayerChallenge();
            case 2: return new PlayerChallenge();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Accepted";
            case 1: return "Pending";
            case 2: return "History";
        }

        return super.getPageTitle(position);
    }
}
