package com.brook.app.lazyfragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import java.util.List;


public class FragmentUserVisibleController {

    private final Fragment mTargetFragment;
    private volatile Boolean mLastVisibleState = null;
    private volatile boolean mIsViewCreated = false;

    public FragmentUserVisibleController(Fragment fragment) {
        this.mTargetFragment = fragment;
    }

    public void onResume() {
        if (mTargetFragment.getUserVisibleHint()) {
            if (mTargetFragment.getParentFragment() == null || mTargetFragment.getParentFragment().getUserVisibleHint()) {
                dispatchVisibleChange(false);
            }
        }
    }


    public void dispatchVisibleChange(boolean hidden) {
        if (mLastVisibleState != null && mLastVisibleState == hidden) {
            return;
        }
        if (!mIsViewCreated) {
            return;
        }
        mLastVisibleState = hidden;

        String objectName = getObjectName();
        if (!hidden) {
            Log.e("Brook", objectName + "#对用户可见=" + mTargetFragment.toString());
        } else {
            Log.e("Brook", objectName + "#对用户不可见=" + mTargetFragment.toString());
        }

        if (!mTargetFragment.isAdded()) {
            return;
        }

        FragmentManager childFragmentManager = mTargetFragment.getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof UserVisibleControllerOwner) {
                if (fragment.getLifecycle().getCurrentState().ordinal() >= Lifecycle.State.RESUMED.ordinal()) {
                    if (fragment.getUserVisibleHint() != hidden) {
                        ((UserVisibleControllerOwner) fragment).getController().dispatchVisibleChange(hidden);
                    }
                }
            }
        }
    }

    @NonNull
    private String getObjectName() {
        return mTargetFragment.getClass().getSimpleName() + "#" + ((LazyFragment) mTargetFragment).getUid();
    }


    public void onCreateView() {
        mIsViewCreated = true;
        FragmentManager childFragmentManager = mTargetFragment.getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.setUserVisibleHint(mTargetFragment.getUserVisibleHint());
        }
    }

    public void onPause() {
        dispatchVisibleChange(true);
    }

    public void onHiddenChanged(boolean hidden) {
        dispatchVisibleChange(hidden);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        dispatchVisibleChange(!isVisibleToUser);
    }

    public interface UserVisibleControllerOwner {
        @NonNull
        FragmentUserVisibleController getController();
    }
}