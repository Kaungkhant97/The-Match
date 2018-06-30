package com.anglehack.thematch.thematch.Di.module;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Manager.DefaultTeamDetailManager;
import com.anglehack.thematch.thematch.Manager.TeamDetailManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ko Oo on 1/7/2018.
 */

@Module
public class TeamDetailModule
{
    @Provides
    @Singleton
    TeamDetailManager provideTeamDetailManager(RetrofitService retrofitService){

        return new DefaultTeamDetailManager(retrofitService);
    }
}
