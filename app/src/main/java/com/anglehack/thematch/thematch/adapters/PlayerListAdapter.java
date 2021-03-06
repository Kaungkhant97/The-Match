package com.anglehack.thematch.thematch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.VH.PlayerListVH;

import java.util.List;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListVH> {
    LayoutInflater mInflater;
    List<Player> playerList;

    public PlayerListAdapter( List<Player> playerList) {

        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayerListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_player_list, parent, false);

        return new PlayerListVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerListVH holder, int position) {
        holder.bindData(playerList.get(position));
        holder.addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerList.get(holder.getAdapterPosition()).setSelected(true);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void addall(List<Player> players){
        playerList.addAll(players);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }
}
