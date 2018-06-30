package com.anglehack.thematch.thematch.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapter.ChallengeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_challenge, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerChallenge.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ChallengeAdapter adapter = new ChallengeAdapter(getContext(), );
        recyclerChallenge.setAdapter(adapter);

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
