package com.anglehack.thematch.thematch.Data;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class Player {
    String name;
    String pID;
    String id;
    String score;
    boolean selected;
    String profile_pic;

    public Player(String name, String pID, String score,String id) {
        this.name = name;
        this.pID = pID;
        this.id =  id;
        this.score = score;
        this.profile_pic = profile_pic;
    }
    public Player(String name, String pID, String score) {
        this.name = name;
        this.pID = pID;
        this.id =  id;
        this.score = score;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public Player() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getpID() {
        return pID;
    }

    public String getScore() {
        return score;
    }
}
