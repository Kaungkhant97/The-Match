package com.anglehack.thematch.thematch.Di.component;

import android.support.v4.app.Fragment;

import com.anglehack.thematch.thematch.Di.module.NetworkModule;
import com.anglehack.thematch.thematch.Di.module.PlaceModule;
import com.anglehack.thematch.thematch.Di.module.PlayerModule;
import com.anglehack.thematch.thematch.Di.module.TeamModule;
import com.anglehack.thematch.thematch.HomeActivity;
import com.anglehack.thematch.thematch.MainActivity;
import com.anglehack.thematch.thematch.Manager.PlaceManager;
import com.anglehack.thematch.thematch.Manager.PlayerManager;
import com.anglehack.thematch.thematch.Manager.TeamManager;
import com.anglehack.thematch.thematch.fragments.ChallengeFragment;
import com.anglehack.thematch.thematch.fragments.PlaceFragment;
import com.anglehack.thematch.thematch.fragments.PlayerListFragment;
import com.anglehack.thematch.thematch.fragments.PlayerChallenge;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { NetworkModule.class, TeamModule.class,PlayerModule.class , PlaceModule.class})
public interface ManagerComponent {


    TeamManager teammanager();
    PlayerManager playermanager();
    PlaceManager placemanager();

    void inject(MainActivity mainActivity);
    void inject(HomeActivity homeActivity);

    void inject(ChallengeFragment challengeFragment);
    void inject(PlayerChallenge playerChallenge);

    void inject(PlayerListFragment fragment);

    void inject(PlaceFragment placeFragment);
}
