package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.Player;

import java.util.ArrayList;

import io.reactivex.Flowable;

public class DefaultPlayerManager implements PlayerManager {
    private final RetrofitService retrofitService;

    public DefaultPlayerManager(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }


    @Override
    public Flowable<ArrayList<Player>> getOtherPlayer(String currentPlayerid) {
        return retrofitService.getotherPlayer(currentPlayerid);
    }
}
