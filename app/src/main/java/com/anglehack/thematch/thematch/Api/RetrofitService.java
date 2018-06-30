package com.anglehack.thematch.thematch.Api;

import com.anglehack.thematch.thematch.Data.Place;
import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.Data.Team;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("team")
    Flowable<ArrayList<Team>> getTeams(@Query("teamId") String teamId);

    @GET("team/players")
    Flowable<ArrayList<Player>> getPlayers(@Query("teamId") String teamId);

    @GET("player/other")
    Flowable<ArrayList<Player>> getotherPlayer(@Query("playerId") String playerId);

    @GET("place")
    Flowable<Place> getPlace();

    @POST("team")
    Completable createTeam();



}
