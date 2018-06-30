package com.anglehack.thematch.thematch.Data;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class Player {
    String name;
    String pID;
    String score;
    boolean selected;

    public Player(String name, String pID, String score) {
        this.name = name;
        this.pID = pID;
        this.score = score;
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
