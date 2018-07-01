package com.anglehack.thematch.thematch.VH;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.util.Base;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerMemberViewHolder extends Base.RecyclerViewHolder<Player> implements View.OnClickListener
{
    @BindView(R.id.img_player_profile)
    ImageView imgPlayerProfile;
    @BindView(R.id.txt_player_name)
    TextView txtPlayerName;

    private Player player;

    public PlayerMemberViewHolder(View itemView, Context context) {
        super(itemView, context);

        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onBind(ArrayList<Player> itemList)
    {
        player = getItem(itemList);

        String imageurl = "https://api.adorable.io/avatars/"+player.getId();
        Log.e( "bindData: ", imageurl);

        Picasso.with(getContext()).load(imageurl).error(R.drawable.ic_people).into(imgPlayerProfile);
        txtPlayerName.setText(player.getName());
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), player.getId(), Toast.LENGTH_SHORT).show();
    }
}
