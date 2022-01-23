package com.brook.app.lazyfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ViewPagerWithTabLayoutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tablayout_viewpager);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewpager = findViewById(R.id.fragment_viewpager);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 6;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return BottomChildFragment.newInstance(position);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "Tab:" + position;
            }
        });
        tabLayout.setupWithViewPager(viewpager);
    }
}
