package com.anglehack.thematch.thematch.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.VH.PlayerMemberViewHolder;
import com.anglehack.thematch.thematch.util.Base;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerMemberAdapter extends Base.RecyclerAdapter<PlayerMemberViewHolder, Player>
{


    public PlayerMemberAdapter(Context context, ArrayList<Player> itemList) {
        super(context, itemList);
    }

    @NonNull
    @Override
    public PlayerMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_player_member, parent, false);

        return new PlayerMemberViewHolder(itemView, getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerMemberViewHolder holder, int position) {
        holder.onBind(getItemList());
    }

    public void addall(List<Player> players) {
        getItemList().addAll(players);
        notifyDataSetChanged();
    }
}
