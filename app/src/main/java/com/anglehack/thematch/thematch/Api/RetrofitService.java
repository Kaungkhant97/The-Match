package com.anglehack.thematch.thematch.Api;

import com.anglehack.thematch.thematch.Data.Challenge;
import com.anglehack.thematch.thematch.Data.Place;
import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.Data.TeamDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("challenge/team")
    Flowable<ArrayList<Team>> getChallengeTeams(@Query("teamId") String teamId);

    @GET("team")
    Flowable<TeamDetail> getTeamDetail(@Query("teamId") String teamId);

    @POST("challenge/send")
    Completable requestChallege(@Body()challengeBody  challengeBody);

    @GET("team/players")
    Flowable<ArrayList<Player>> getPlayers(@Query("teamId") String teamId);

    @GET("player/others")
    Flowable<ArrayList<Player>> getotherPlayer(@Query("playerId") String playerId);

    @GET("places")
    Flowable<ArrayList<Place>> getPlace();

    @POST("team/create")
    Completable createTeam(@Body teamCreate teamCreate);

    @GET("team/challenge/accept")
    Flowable<ArrayList<Challenge>> getaccepted(@Query("teamId") String teamId);

    @GET("team/challenge/pending")
    Flowable<ArrayList<Challenge>> getPending(@Query("teamId") String teamId);

    @GET("team/challenge/history")
    Flowable<ArrayList<Challenge>> getHistory(@Query("teamId") String teamId);

    public static class teamCreate{
        public teamCreate(String leadername, String teamname, List<String> playerlist) {
            this.leaderId = leadername;
            this.teamname = teamname;
            this.playerlist = playerlist;
        }

        @SerializedName("leader")
        String leaderId;

        @SerializedName("teamName")
        String teamname;

        @SerializedName("players")
        List<String> playerlist;
    }


    public static class challengeBody{

        public challengeBody(String team1, String team2, String date, String placeId) {
            this.team1 = team1;
            this.team2 = team2;
            this.date = date;
            this.placeId = placeId;
        }

        @SerializedName("team1")
        String team1;

        @SerializedName("team2")
        String team2;

        @SerializedName("date")
        String date;

        @SerializedName("place_id")
        String placeId;
    }


    @GET("api/player/team")
    Flowable<ArrayList<Team>> getPlayerTeamList(@Query("playerId") String playerId);

}
