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
import androidx.lifecycle.Lifecycle;

import java.util.List;

public abstract class LazyFragment extends Fragment {

    protected int index = 0;
    private boolean rootViewCreate = false;


    public LazyFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        index = arguments.getInt("index");
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onCreate=" + index);
    }

    @Nullable
    @Override
    @CallSuper
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onCreateView=" + index);
        View rootView = createView(inflater, container, savedInstanceState);
        rootViewCreate = true;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onViewCreated=" + index);
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.setUserVisibleHint(getUserVisibleHint());
        }
    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void dispatchVisibleChange(boolean hidden) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (!hidden) {
            Log.e("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#对用户可见=" + index);
        } else {
            Log.e("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#对用户不可见=" + index);
        }
        for (Fragment fragment : fragments) {
            if (fragment instanceof LazyFragment) {
                if (fragment.getLifecycle().getCurrentState().ordinal() >= Lifecycle.State.RESUMED.ordinal()) {
                    ((LazyFragment) fragment).dispatchVisibleChange(hidden);
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onResume=" + index);

        if (getUserVisibleHint()) {
            dispatchVisibleChange(false);
        }
    }


    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onPause=" + index);
        dispatchVisibleChange(true);
    }

    @CallSuper
    @Override
    public void onStop() {
        super.onStop();
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onStop=" + index);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onStop=" + index);
        if (rootViewCreate) {
            dispatchVisibleChange(hidden);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#setUserVisibleHint(" + isVisibleToUser + ")=" + index);
        if (rootViewCreate) {
            dispatchVisibleChange(!isVisibleToUser);
        }
    }
}
