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

        BottomNavigationView navigation;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, ProfileFragment.newInstance()).commit();
                    //    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:

                    changeFragment(new ChallengeFragment());

                    return true;
                case R.id.navigation_notifications:
                    changeFragment(PlaceFragment.newInstance("3"));
                    return true;
            }
            return false;
        }
    };
    private Toolbar toolbar;
    public BottomNavigationView navigation;


    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
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

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

}
