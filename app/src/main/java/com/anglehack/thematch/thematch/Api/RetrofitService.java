package com.anglehack.thematch.thematch.Api;

import com.anglehack.thematch.thematch.Data.Place;
import com.anglehack.thematch.thematch.Data.Team;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {

    @GET("")
    Flowable<Team> getTeam();

    @GET("")
    Flowable<Place> getPlace();

    @POST("")
    Completable createTeam();
}
