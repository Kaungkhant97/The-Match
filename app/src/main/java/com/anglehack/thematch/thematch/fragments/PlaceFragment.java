package com.anglehack.thematch.thematch.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.Place;
import com.anglehack.thematch.thematch.Di.component.DaggerManagerComponent;
import com.anglehack.thematch.thematch.HomeActivity;
import com.anglehack.thematch.thematch.Manager.PlaceManager;
import com.anglehack.thematch.thematch.Manager.TeamManager;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlaceAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlaceFragment extends Fragment implements PlaceAdapter.OnClickListner {

    private static final String TEAMNAME = "teamName";
    private static String CHALLENGETEAM;
    private RecyclerView recycler_places;

    @Inject
    PlaceManager placeManager;

    @Inject
    TeamManager teamManager;


    private PlaceAdapter placeAdapter;
    private String challengedTeam;
    private String teamname = "";
    private String message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        challengedTeam = getArguments().getString(CHALLENGETEAM);
        teamname = getArguments().getString(TEAMNAME);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.place_fragment, container, false);
        recycler_places = v.findViewById(R.id.recycler_place);
        this.placeAdapter = new PlaceAdapter(new ArrayList<>());
        recycler_places.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_places.setAdapter(placeAdapter);

        placeAdapter.setClickListner(this);
        DaggerManagerComponent.builder().build().inject(this);
        placeManager.getplace().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    placeAdapter.addAll(list);
                    placeAdapter.notifyDataSetChanged();
                });
        return v;
    }

    public static Fragment newInstance(String challengedTeam,String teamname) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putString(CHALLENGETEAM, teamname);
        args.putString(TEAMNAME, challengedTeam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(Place place) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date time = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String realdate = dateFormat.format(time);
        RetrofitService.challengeBody body = new RetrofitService.challengeBody("3", challengedTeam, realdate, String.valueOf(place.getId()));

        if (challengedTeam.equals("3")) {
            message = "You are about to make a challenge in this stadium";
        } else {
            message = "You are about to make a challenge with this" + teamname + "in this " + place.getName();


        }
        new AlertDialog.Builder(getContext()).setTitle("Challange")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        teamManager.postChallenge(body)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    getActivity().startActivity(new Intent(getContext(), HomeActivity.class));
                                }, e -> {
                                    Log.e("onClick: ", e.getLocalizedMessage());
                                });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();

    }
}
