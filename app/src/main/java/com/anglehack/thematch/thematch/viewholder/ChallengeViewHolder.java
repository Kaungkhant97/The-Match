package com.anglehack.thematch.thematch.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.activities.ChallengeTeamDetail;
import com.anglehack.thematch.thematch.util.Base;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ko Oo on 30/6/2018.
 */

public class ChallengeViewHolder extends Base.RecyclerViewHolder<Team> implements View.OnClickListener
{
    @BindView(R.id.img_team)
    ImageView imgTeam;
    @BindView(R.id.txt_team_name)
    TextView txtTeamName;

    private Team team;

    public ChallengeViewHolder(View itemView, Context context) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onBind(ArrayList<Team> itemList) {
        team = getItem(itemList);

        Picasso.with(getContext()).load(team.getLogoUrl()).into(imgTeam);
        txtTeamName.setText(team.getName());
    }

    @Override
    public void onClick(View v) {

        getContext().startActivity(ChallengeTeamDetail.getInstance(team.getId()));
    }
}
