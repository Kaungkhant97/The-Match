package com.anglehack.thematch.thematch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.util.Base;
import com.anglehack.thematch.thematch.viewholder.ChallengeViewHolder;

/**
 * Created by Ko Oo on 30/6/2018.
 */

public class ChallengeAdapter extends Base.RecyclerAdapter<ChallengeViewHolder, Team>
{

    public ChallengeAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {

    }
}
