package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.Data.Team;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface TeamManager {
    Completable createTeam(RetrofitService.teamCreate team);

    Flowable<ArrayList<Team>>  getTeams(String teamId);


    Flowable<ArrayList<Player>>  getTeamplayer(String teamId);

    Flowable<ArrayList<Team>> getPlayerTeamList(String plyerId);

    Completable postChallenge(RetrofitService.challengeBody challengeBody);


}
