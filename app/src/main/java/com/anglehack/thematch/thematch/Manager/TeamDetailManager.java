package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Data.TeamDetail;

import io.reactivex.Flowable;

/**
 * Created by Ko Oo on 1/7/2018.
 */

public interface TeamDetailManager {
    Flowable<TeamDetail> getTeamDetail(String teamId);
}
