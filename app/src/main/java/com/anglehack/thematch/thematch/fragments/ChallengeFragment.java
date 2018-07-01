package com.anglehack.thematch.thematch.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anglehack.thematch.thematch.Di.component.DaggerManagerComponent;
import com.anglehack.thematch.thematch.Manager.TeamManager;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.ChallengeAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ko Oo on 30/6/2018.
 */

public class ChallengeFragment extends Fragment
{
    @BindView(R.id.btn_find_match)
    Button btnFindMatch;
    @BindView(R.id.recycler_challenge)
    RecyclerView recyclerChallenge;
    Unbinder unbinder;

    @Inject
    TeamManager teamManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        DaggerManagerComponent.builder().build().inject(this);

        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        unbinder = ButterKnife.bind(this, view);


        recyclerChallenge.setLayoutManager(new GridLayoutManager(getContext(), 3));

        teamManager.getTeams("1").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(list->{
            ChallengeAdapter adapter = new ChallengeAdapter(getContext(), list);
            recyclerChallenge.setAdapter(adapter);
        },e->{
            Log.e("onCreateView: ",e.getLocalizedMessage());
        });


        return view;
    }

    @OnClick(R.id.btn_find_match)
    public void onViewClicked() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
