package com.brook.app.lazyfragment;

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
public class FragmentUserVisibleController {

    private final Fragment mTargetFragment;
    private UserVisibleListener mUserVisibleListener = null;

    private volatile Boolean mLastVisibleState = null;
    private volatile boolean mIsViewCreated = false;

    public FragmentUserVisibleController(@NonNull Fragment fragment) {
        this.mTargetFragment = fragment;
    }

    public FragmentUserVisibleController(@NonNull Fragment fragment, @Nullable UserVisibleListener listener) {
        this.mTargetFragment = fragment;
        this.mUserVisibleListener = listener;
    }

    public void setUserVisibleListener(@Nullable UserVisibleListener listener) {
        this.mUserVisibleListener = listener;
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

        if (mUserVisibleListener != null) {
            if (!hidden) {
                mUserVisibleListener.onUserVisible();
            } else {
                mUserVisibleListener.onUserInvisible();
            }
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

    public interface UserVisibleListener {

        void onUserVisible();

        void onUserInvisible();
    }
}