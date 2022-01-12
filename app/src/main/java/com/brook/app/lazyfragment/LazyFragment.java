package com.brook.app.lazyfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public abstract class LazyFragment extends Fragment {

    protected int index = 0;
    private boolean rootViewCreate = false;


    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        index = arguments.getInt("index");
        View rootView = createView(inflater, container, savedInstanceState);
        rootViewCreate = true;
        Log.d("Brook", this.getClass().getName() + "#onCreateView=" + index);
        return rootView;
    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void dispatchVisibleChange(boolean hidden) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (!hidden) {
            Log.e("Brook", this.getClass().getName() + "#开始加载数据=" + index);
            for (Fragment fragment : fragments) {
                if (fragment instanceof LazyFragment) {
                    ((LazyFragment) fragment).dispatchVisibleChange(false);
                }
            }
        } else {
            Log.e("Brook", this.getClass().getName() + "#停止加载数据=" + index);
            for (Fragment fragment : fragments) {
                if (fragment instanceof LazyFragment) {
                    ((LazyFragment) fragment).dispatchVisibleChange(true);
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("Brook", this.getClass().getName() + "#onResume=" + index);

        dispatchVisibleChange(false);
    }


    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
        Log.d("Brook", this.getClass().getName() + "#onPause=" + index);
        dispatchVisibleChange(true);
    }

    @CallSuper
    @Override
    public void onStop() {
        super.onStop();
        Log.d("Brook", this.getClass().getName() + "#onStop=" + index);
    }

    @CallSuper
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("Brook", this.getClass().getName() + "#onHiddenChanged==" + index);
        dispatchVisibleChange(hidden);
    }
}
