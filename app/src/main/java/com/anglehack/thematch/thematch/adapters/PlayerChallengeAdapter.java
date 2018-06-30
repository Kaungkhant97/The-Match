package com.anglehack.thematch.thematch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.Data.Challenge;
import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.VH.PlayerChallengeListVH;
import com.anglehack.thematch.thematch.VH.PlayerListVH;

import java.util.List;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerChallengeAdapter extends RecyclerView.Adapter<PlayerChallengeListVH> {
    LayoutInflater mInflater;
    List<Challenge> challengeList;

    public PlayerChallengeAdapter(LayoutInflater mInflater, List<Challenge> challenges) {
        this.mInflater = mInflater;
        this.challengeList = challenges;
    }

    @NonNull
    @Override
    public PlayerChallengeListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_view_player_chalenge_list, parent, false);

        return new PlayerChallengeListVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerChallengeListVH holder, int position) {
        holder.bindData(challengeList.get(position));
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }
}
