package com.anglehack.thematch.thematch.VH;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Data.Challenge;
import com.anglehack.thematch.thematch.Data.Player;
import com.anglehack.thematch.thematch.Data.Team;
import com.anglehack.thematch.thematch.R;

/**
 * Created by IN-3442 on 30-Jun-18.
 */

public class PlayerChallengeListVH extends RecyclerView.ViewHolder {
    View view;
    TextView tvTeam1Name, tvTeam2Name, tvDate, tvTime, tvPlace;

    public PlayerChallengeListVH(View itemView) {
        super(itemView);

        view = itemView;
        init();
    }

    private void init() {
        tvTeam1Name = view.findViewById(R.id.tv_team1_name);
        tvTeam2Name = view.findViewById(R.id.tv_team2_name);
        tvDate = view.findViewById(R.id.tv_chalenge_date);
        tvTime = view.findViewById(R.id.tv_chalenge_time);
        tvPlace = view.findViewById(R.id.tv_chalenge_place);
    }

    public void bindData(Challenge challenge){
        tvTeam1Name.setText(challenge.getAcceptedTeamName());
        tvTeam2Name.setText(challenge.getChallengerTeamName());
        tvDate.setText(challenge.getReservedTime());
        tvPlace.setText(challenge.getPlaceID() + "");
    }

}
