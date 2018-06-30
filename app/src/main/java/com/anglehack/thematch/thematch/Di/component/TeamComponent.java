package com.anglehack.thematch.thematch.Di.component;

import com.anglehack.thematch.thematch.Di.module.NetworkModule;
import com.anglehack.thematch.thematch.Di.module.TeamModule;
import com.anglehack.thematch.thematch.HomeActivity;
import com.anglehack.thematch.thematch.MainActivity;
import com.anglehack.thematch.thematch.Manager.TeamManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { NetworkModule.class, TeamModule.class })
public interface TeamComponent {


    TeamManager teammanager();

    void inject(MainActivity mainActivity);
    void inject(HomeActivity homeActivity);
}
