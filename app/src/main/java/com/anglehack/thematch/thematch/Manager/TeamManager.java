package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.Data.Team;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface TeamManager {
    Completable createTeam();

    Flowable<ArrayList<Team>>  getTeams(String playerId);


    Flowable<ArrayList<Player>>  getTeamplayer(String teamId);


}
