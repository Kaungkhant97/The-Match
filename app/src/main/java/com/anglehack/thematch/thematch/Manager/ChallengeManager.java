package com.anglehack.thematch.thematch.Manager;

import com.anglehack.thematch.thematch.Data.Challenge;

import io.reactivex.Flowable;


public interface ChallengeManager {
    Flowable<Challenge> getChallenges();
}
