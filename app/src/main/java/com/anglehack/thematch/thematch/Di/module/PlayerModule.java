package com.anglehack.thematch.thematch.Di.module;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Manager.DefaultPlaceManager;
import com.anglehack.thematch.thematch.Manager.DefaultTeamManager;
import com.anglehack.thematch.thematch.Manager.PlaceManager;
import com.anglehack.thematch.thematch.Manager.TeamManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PlayerModule {

    @Provides
    @Singleton
    PlaceManager provideTeamManager(RetrofitService retrofitService){

        return new DefaultPlaceManager(retrofitService);
    }
}
