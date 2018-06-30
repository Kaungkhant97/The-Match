package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Data.Player;

import java.util.ArrayList;

import io.reactivex.Flowable;

public interface PlayerManager {

    Flowable<ArrayList<Player>> getOtherPlayer(String teamid);





}
