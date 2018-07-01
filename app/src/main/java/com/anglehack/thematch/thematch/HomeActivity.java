package com.anglehack.thematch.thematch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.anglehack.thematch.thematch.Di.component.DaggerManagerComponent;
import com.anglehack.thematch.thematch.Manager.TeamManager;
import com.anglehack.thematch.thematch.fragments.PlaceFragment;
import com.anglehack.thematch.thematch.fragments.PlayerListFragment;
import com.anglehack.thematch.thematch.fragments.ChallengeFragment;
import com.anglehack.thematch.thematch.fragments.ProfileFragment;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    @Inject
    TeamManager teamManager;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,ProfileFragment.newInstance()).commit();
                //    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
<<<<<<< HEAD
                   changeFragment(PlaceFragment.newInstance("1"));
=======
                   changeFragment(new ChallengeFragment());
>>>>>>> 6cc313492a2a8c961c748ef904f129b488ce474a
                    return true;
                case R.id.navigation_notifications:
                    changeFragment(PlayerListFragment.newInstance());
                    return true;
            }
            return false;
        }
    };
    private Toolbar toolbar;

    private void changeFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DaggerManagerComponent.builder().build().inject(this);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

}
