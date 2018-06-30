package com.anglehack.thematch.thematch.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anglehack.thematch.thematch.R;
import com.anglehack.thematch.thematch.adapters.PlayerFragmentPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    /*private ViewPager viewPager;
    private TabLayout tabLayout;
    PlayerFragmentPagerAdapter mPlayerFragmentPagerAdapter;*/

    @BindView(R.id.btn_pending)
    Button btnPadding;

    @BindView(R.id.btn_accepted)
    Button btnAccepted;

    @BindView(R.id.btn_hostory)
    Button btnHistory;


    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        /*viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.sliding_tab);

        viewPager.setAdapter(new PlayerFragmentPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);*/

        btnAccepted.setBackgroundResource(R.drawable.btn_select_touch_left);
        changeFragment(0);

        btnAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPadding.setBackgroundResource(R.drawable.btn_select_center);
                btnAccepted.setBackgroundResource(R.drawable.btn_select_touch_left);
                btnHistory.setBackgroundResource(R.drawable.btn_select_right);

                changeFragment(0);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHistory.setBackgroundResource(R.drawable.btn_select_touch_right);
                btnPadding.setBackgroundResource(R.drawable.btn_select_center);
                btnAccepted.setBackgroundResource(R.drawable.btn_select_left);

                changeFragment(1);
            }
        });

        btnPadding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPadding.setBackgroundResource(R.drawable.btn_select);
                btnHistory.setBackgroundResource(R.drawable.btn_select_right);
                btnAccepted.setBackgroundResource(R.drawable.btn_select_left);

                changeFragment(2);
            }
        });

        return view;
    }

    private void changeFragment(int id){
        getFragmentManager().beginTransaction().replace(R.id.frame_layout, PlayerChallenge.newInstance(id)).commit();
    }

}
