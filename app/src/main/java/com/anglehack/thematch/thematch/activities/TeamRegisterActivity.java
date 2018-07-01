package com.anglehack.thematch.thematch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anglehack.thematch.thematch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IN-3442 on 01-Jul-18.
 */

public class TeamRegisterActivity extends AppCompatActivity{

    @BindView(R.id.btn_contiue)
    Button btn_continue;

    @BindView(R.id.edtxt_input)
    EditText edtxt_input;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_register);
        ButterKnife.bind(this);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamRegisterActivity.this,TeamCreateActivity.class);

                intent.putExtra("TEAMNAME",edtxt_input.getText().toString());
                startActivity(intent);
            }
        });

    }
}
