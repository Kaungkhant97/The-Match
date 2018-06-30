package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.Place;

import io.reactivex.Flowable;

public class DefaultPlaceManager implements PlaceManager {


    private final RetrofitService retrofitService;

    public DefaultPlaceManager(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    @Override
    public Flowable<Place> getplace() {
        return Flowable.just(new Place());
    }
}
