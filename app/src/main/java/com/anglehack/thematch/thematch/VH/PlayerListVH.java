package com.anglehack.thematch.thematch.VH;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.R;
import com.squareup.picasso.Picasso;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerListVH extends RecyclerView.ViewHolder {
    private final Context context;
    View view;
    public ImageView imgprofile;
    public TextView tvID;
    public TextView tvScore;
    public ImageView addbutton;

    public PlayerListVH(View itemView) {
        super(itemView);

        view = itemView;
        this.context = itemView.getContext();
        init();
    }

    private void init() {
        imgprofile =view.findViewById(R.id.img_player);
        tvScore =view.findViewById(R.id.txt_player_name);
        addbutton = view.findViewById(R.id.add_playerButton);

      //  tvID =view.findViewById(R.id.tv_player_id_row);

    }

    public void bindData(Player player){
       // tvID.setText(player.getpID());

        String imageurl = "https://api.adorable.io/avatars/"+player.getId();
        Log.e( "bindData: ", imageurl);
        Picasso.with(context).load(imageurl).error(R.drawable.ic_people).into(imgprofile);
        if(player.isSelected()){
            addbutton.setImageResource(R.drawable.ic_check);
        }else{
            addbutton.setImageResource(R.drawable.ic_person_add);
        }

        tvScore.setText(player.getScore());
    }

}
