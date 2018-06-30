package com.anglehack.thematch.thematch.Di.component;

import com.anglehack.thematch.thematch.Di.module.NetworkModule;
import com.anglehack.thematch.thematch.Di.module.PlayerModule;
import com.anglehack.thematch.thematch.Di.module.TeamModule;
import com.anglehack.thematch.thematch.HomeActivity;
import com.anglehack.thematch.thematch.MainActivity;
import com.anglehack.thematch.thematch.Manager.PlayerManager;
import com.anglehack.thematch.thematch.Manager.TeamManager;
import com.anglehack.thematch.thematch.fragments.PlayerListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { NetworkModule.class, TeamModule.class,PlayerModule.class })
public interface ManagerComponent {


    TeamManager teammanager();
    PlayerManager playermanager();

    void inject(MainActivity mainActivity);
    void inject(HomeActivity homeActivity);
    void inject(PlayerListFragment fragment);
}
