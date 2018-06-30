package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.TeamDetail;

import io.reactivex.Flowable;

/**
 * Created by Ko Oo on 1/7/2018.
 */

public class DefaultTeamDetailManager implements TeamDetailManager
{
    private final RetrofitService retrofitService;

    public DefaultTeamDetailManager(RetrofitService retrofit) {
        this.retrofitService = retrofit;
    }

    @Override
    public Flowable<TeamDetail> getTeamDetail(String teamId) {
        return retrofitService.getTeamDetail(teamId);
    }
}
