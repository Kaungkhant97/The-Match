package com.anglehack.thematch.thematch.Data;

import com.google.gson.annotations.SerializedName;

public class Challenge {

    @SerializedName("id")
    int id;

    @SerializedName("challenger_team_name")
    String challengerTeamName;

    @SerializedName("challenger_team_logo")
    String challengerTeamLogo;

    @SerializedName("accepted_team_logo")
    String acceptedTeamLogo;

    @SerializedName("accepted_team_name")
    String acceptedTeamName;

    @SerializedName("place_id")
    int placeID;

    @SerializedName("place")
    String place;

    @SerializedName("reserved_time")
    String reservedTime;

    @SerializedName("status")
    int status;

    int point;

    int result_id;

    public Challenge(int id, String challengerTeamName, String acceptedTeamName, int placeID, String reservedTime, int status, int point, int result_id) {
        this.id = id;
        this.challengerTeamName = challengerTeamName;
        this.acceptedTeamName = acceptedTeamName;
        this.placeID = placeID;
        this.reservedTime = reservedTime;
        this.status = status;
        this.point = point;
        this.result_id = result_id;
    }

    public int getId() {
        return id;
    }

    public String getChallengerTeamName() {
        return challengerTeamName;
    }

    public String getAcceptedTeamName() {
        return acceptedTeamName;
    }

    public int getPlaceID() {
        return placeID;
    }

    public String getReservedTime() {
        return reservedTime;
    }

    public int getStatus() {
        return status;
    }

    public int getPoint() {
        return point;
    }

    public int getResult_id() {
        return result_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChallengerTeamName(String challengerTeamName) {
        this.challengerTeamName = challengerTeamName;
    }

    public void setAcceptedTeamName(String acceptedTeamName) {
        this.acceptedTeamName = acceptedTeamName;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public void setReservedTime(String reservedTime) {
        this.reservedTime = reservedTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getChallengerTeamLogo() {
        return challengerTeamLogo;
    }

    public String getAcceptedTeamLogo() {
        return acceptedTeamLogo;
    }

    public void setChallengerTeamLogo(String challengerTeamLogo) {
        this.challengerTeamLogo = challengerTeamLogo;
    }

    public void setAcceptedTeamLogo(String acceptedTeamLogo) {
        this.acceptedTeamLogo = acceptedTeamLogo;
    }
}
