package com.anglehack.thematch.thematch.Data;

public class Challenge {

    int id;
    String challengerTeamName;
    String acceptedTeamName;
    int placeID;
    String reservedTime;
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
}
