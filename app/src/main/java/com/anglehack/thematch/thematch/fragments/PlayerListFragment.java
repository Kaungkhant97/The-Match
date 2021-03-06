package com.anglehack.thematch.thematch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anglehack.thematch.thematch.Api.RetrofitService;
import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.Di.component.DaggerManagerComponent;
import com.anglehack.thematch.thematch.HomeActivity;
import com.anglehack.thematch.thematch.Manager.PlayerManager;
import com.anglehack.thematch.thematch.Manager.TeamManager;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlayerListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlayerListFragment extends Fragment {

    private static String TEAMNAME="teamname";
    private RecyclerView recyclerview;

    @Inject
    PlayerManager playerManager;

    @Inject
    TeamManager teamManager;
    private PlayerListAdapter adapter;
    private String teamname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //  View view = inflater.inflate(R.layout.fragment_playerlist, container);
        View view = inflater.inflate(R.layout.fragment_playerlist, container, false);
        DaggerManagerComponent.builder().build().inject(this);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyceler_playerlist);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));

        FloatingActionButton fab_create = view.findViewById(R.id.fab_create);
        adapter = new PlayerListAdapter(new ArrayList<>());
        fab_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<String> playerlist = new ArrayList<>();
                for(Player p :adapter.getPlayerList()){
                    if(p.isSelected()){
                        playerlist.add(p.getId());
                    }

                }
                if(teamname == null){
                    teamname= "hihi";
                }
                teamManager.createTeam(new RetrofitService.teamCreate("3", teamname, playerlist)).subscribeOn(Schedulers.io()).subscribe(() -> {
                  getActivity().startActivity(new Intent(getActivity(),HomeActivity.class));
                },e->{

                    Log.e( "onClick: ", e.getLocalizedMessage());
                });
            }
        });
        playerManager.getOtherPlayers("1").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            adapter.addall(list);
            recyclerview.setAdapter(adapter);
        });

        return view;
    }


    public static PlayerListFragment newInstance(String teamname) {
        PlayerListFragment fragment = new PlayerListFragment();
        Bundle args = new Bundle();
        args.putString(TEAMNAME,teamname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        teamname = getArguments().getString(TEAMNAME);
    }
}
