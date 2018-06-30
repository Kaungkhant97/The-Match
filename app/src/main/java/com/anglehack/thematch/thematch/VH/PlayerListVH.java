package com.anglehack.thematch.thematch.VH;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.R;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerListVH extends RecyclerView.ViewHolder {
    View view;
    private TextView tvName;
    private TextView tvID;
    private TextView tvScore;

    public PlayerListVH(View itemView) {
        super(itemView);

        view = itemView;
        init();
    }

    private void init() {
        tvName =view.findViewById(R.id.tv_player_name_row);
        tvScore =view.findViewById(R.id.tv_player_point_row);
        tvID =view.findViewById(R.id.tv_player_id_row);
    }

    public void bindData(Player player){
        tvID.setText(player.getpID());
        tvName.setText(player.getName());
        tvScore.setText(player.getScore());
    }

}
