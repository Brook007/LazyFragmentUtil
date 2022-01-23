package com.brook.app.lazyutil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import java.util.List;

/**
 * Fragment懒加载帮助工具，支持ViewPager嵌套
 *
 * @author brook
 * @time 2022年01月23日16:03:22
 */
public class FragmentUserVisibilityController {

    private final Fragment mTargetFragment;
    private UserVisibilityListener mUserVisibilityListener = null;

    private volatile Boolean mLastVisibleState = null;
    private volatile boolean mIsViewCreated = false;

    public FragmentUserVisibilityController(@NonNull Fragment fragment) {
        this.mTargetFragment = fragment;
    }

    public FragmentUserVisibilityController(@NonNull Fragment fragment, @Nullable UserVisibilityListener listener) {
        this.mTargetFragment = fragment;
        this.mUserVisibilityListener = listener;
    }

    public void setUserVisibleListener(@Nullable UserVisibilityListener listener) {
        this.mUserVisibilityListener = listener;
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

        if (mUserVisibilityListener != null) {
            if (!hidden) {
                mUserVisibilityListener.onUserVisible();
            } else {
                mUserVisibilityListener.onUserInvisible();
            }
        }

        if (!mTargetFragment.isAdded()) {
            return;
        }

        FragmentManager childFragmentManager = mTargetFragment.getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof UserVisibilityControllerOwner) {
                if (fragment.getLifecycle().getCurrentState().ordinal() >= Lifecycle.State.RESUMED.ordinal()) {
                    if (fragment.getUserVisibleHint() != hidden) {
                        ((UserVisibilityControllerOwner) fragment).getController().dispatchVisibleChange(hidden);
                    }
                }
            }
        }
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

    public interface UserVisibilityControllerOwner {
        @NonNull
        FragmentUserVisibilityController getController();
    }

    public interface UserVisibilityListener {

        /**
         * 当Fragment对用户可见时回调
         */
        void onUserVisible();

        /**
         * 当Fragment对用户不可见时回调
         */
        void onUserInvisible();
    }
}