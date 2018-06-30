package com.anglehack.thematch.thematch.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.util.Base;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ko Oo on 30/6/2018.
 */

public class ChallengeViewHolder extends Base.RecyclerViewHolder<Team>
{
    @BindView(R.id.img_team)
    ImageView imgTeam;
    @BindView(R.id.txt_team_name)
    TextView txtTeamName;

    public ChallengeViewHolder(View itemView, Context context) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBind(ArrayList<Team> itemList) {
        Team team = getItem(itemList);

        Picasso.with(getContext()).load(team.getLogoUrl())
                .centerCrop().resize(100, 100).into(imgTeam);
        txtTeamName.setText(team.getName());
    }
}
