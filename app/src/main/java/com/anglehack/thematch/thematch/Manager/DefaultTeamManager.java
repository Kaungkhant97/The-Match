package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.Data.Team;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class DefaultTeamManager implements TeamManager {

    private final RetrofitService retrofitService;

    public DefaultTeamManager(RetrofitService retrofit) {

        this.retrofitService = retrofit;
    }

    @Override
    public Completable createTeam(RetrofitService.teamCreate team) {
        return retrofitService.createTeam(team);
    }

    @Override
    public Flowable<ArrayList<Team>> getTeams(String playerId) {
        return retrofitService.getChallengeTeams(playerId);
    }

    @Override
    public Flowable<ArrayList<Player>> getTeamplayer(String teamId) {
        return retrofitService.getPlayers(teamId);
    }

    @Override
    public Flowable<ArrayList<Team>> getPlayerTeamList(String playerID) {
        return retrofitService.getPlayerTeamList(playerID);
    }
}
