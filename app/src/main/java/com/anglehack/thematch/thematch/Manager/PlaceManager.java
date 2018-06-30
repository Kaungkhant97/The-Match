package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Data.Place;

import java.util.ArrayList;

import io.reactivex.Flowable;

public interface PlaceManager {

    Flowable<Place>  getplace();


}
