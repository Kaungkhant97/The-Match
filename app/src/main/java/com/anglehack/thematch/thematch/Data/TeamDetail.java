package com.anglehack.thematch.thematch.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ko Oo on 1/7/2018.
 */

public class TeamDetail
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo_url")
    @Expose
    private String logoUrl;
    @SerializedName("matching_status")
    @Expose
    private Integer matchingStatus;
    @SerializedName("team_status")
    @Expose
    private Integer teamStatus;
    @SerializedName("leader")
    @Expose
    private String leader;
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("players")
    @Expose
    private ArrayList<Player> players;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getMatchingStatus() {
        return matchingStatus;
    }

    public void setMatchingStatus(Integer matchingStatus) {
        this.matchingStatus = matchingStatus;
    }

    public Integer getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(Integer teamStatus) {
        this.teamStatus = teamStatus;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
