package com.anglehack.thematch.thematch.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.util.Base;
import com.anglehack.thematch.thematch.viewholder.ChallengeViewHolder;

import java.util.ArrayList;

/**
 * Created by Ko Oo on 30/6/2018.
 */

public class ChallengeAdapter extends Base.RecyclerAdapter<ChallengeViewHolder, Team>
{


    public ChallengeAdapter(Context context, ArrayList<Team> itemList) {
        super(context, itemList);
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_challenge, parent, false);

        return new ChallengeViewHolder(view, getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, int position) {
        holder.onBind(getItemList());
    }
}
