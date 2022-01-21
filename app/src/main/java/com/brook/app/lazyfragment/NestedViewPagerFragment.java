package com.brook.app.lazyfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

public class NestedViewPagerFragment extends LazyFragment {

    @NotNull
    public static Fragment newInstance() {
        return new NestedViewPagerFragment();
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_viewpager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewpager = view.findViewById(R.id.parent_fragment_viewpager);
        viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return 6;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ViewPagerFragment.newInstance(position * 100);
            }
        });
    }
}
