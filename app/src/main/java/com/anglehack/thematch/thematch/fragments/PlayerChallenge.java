package com.anglehack.thematch.thematch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.Challenge;
import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.Di.component.DaggerManagerComponent;
import com.anglehack.thematch.thematch.Di.module.NetworkModule;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlayerChallengeAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerChallenge extends Fragment {
    public static final String ID = "id";
    int id;

    @Inject
    RetrofitService retrofitService;

    private RecyclerView rvPlayerChallenge;
    PlayerChallengeAdapter playerChallengeAdapter;
    List<Challenge> challengeList = new ArrayList<>();

    public PlayerChallenge() {
        // Required empty public constructor
    }


    public static PlayerChallenge newInstance(int id) {
        PlayerChallenge fragment = new PlayerChallenge();
        Bundle args = new Bundle();
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ID);
        }

        //challengeList.add(new Challenge(1, "Team Lin", "Team Nyi", 1, "2018.6.13 9:00pm", 1, 50, 1));
        //challengeList.add(new Challenge(1, "Team Lin", "Team Nyi", 1, "2018.6.13 9:00pm", 1, 50, 1));
        //challengeList.add(new Challenge(1, "Team Lin", "Team Nyi", 1, "2018.6.13 9:00pm", 1, 50, 1));

        playerChallengeAdapter = new PlayerChallengeAdapter(getLayoutInflater(), challengeList);

        DaggerManagerComponent.builder().build().inject(this);
        networkCall();
    }

    private void networkCall() {
        if(id == 0 ){
            retrofitService.getaccepted("3").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(List->{
                        playerChallengeAdapter.setChallengeList(List);

                    });

        }else if(id ==1){
            retrofitService.getHistory("3").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(List->{
                        playerChallengeAdapter.setChallengeList(List);

                    });
        }else{
            retrofitService.getPending("3").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(List->{
                        playerChallengeAdapter.setChallengeList(List);

                    });

        }

        retrofitService.getPending("3").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(List->{
                    playerChallengeAdapter.setChallengeList(List);

                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_challenge, container, false);
        rvPlayerChallenge = view.findViewById(R.id.rv_team_player_challenge_list);
        rvPlayerChallenge.setAdapter(playerChallengeAdapter);
        rvPlayerChallenge.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        return view;
    }


}
