package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.Team;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class DefaultTeamManager implements TeamManager{

    private final RetrofitService retrofitService;

    public DefaultTeamManager(RetrofitService retrofit) {

      this.retrofitService = retrofit;
    }

    @Override
    public Completable createTeam() {
        return null;
    }

    @Override
    public Flowable<ArrayList<Team>> getTeams(String playerId) {
        return Flowable.just(new ArrayList<Team>()) ;
    }
}
