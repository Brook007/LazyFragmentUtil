package com.brook.app.lazyfragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public abstract class LazyFragment extends Fragment implements FragmentUserVisibleController.UserVisibleControllerOwner {

    private final FragmentUserVisibleController mFragmentController;
    protected int index = 0;

    protected String uid;


    public LazyFragment() {
        mFragmentController = new FragmentUserVisibleController(this);
        uid = UUID.randomUUID().toString().substring(0, 8);
    }

    @NonNull
    @Override
    public FragmentUserVisibleController getController() {
        return mFragmentController;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            index = arguments.getInt("index");
        }
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onCreate=" + index);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onAttach=" + index);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onAttachFragment=" + index);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onDetach=" + index);
    }

    @Nullable
    @Override
    @CallSuper
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onCreateView=" + index);

        mFragmentController.onCreateView();

        View rootView = createView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onViewCreated=" + index);

    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    @Override
    public void onResume() {
        super.onResume();
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onResume=" + index);

        mFragmentController.onResume();
    }


    @CallSuper
    @Override
    public void onPause() {
        super.onPause();
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onPause=" + index);
        mFragmentController.onPause();
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
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#onHiddenChanged=" + index);

        mFragmentController.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("Brook", this.getClass().getSimpleName() + "#" + this.hashCode() + "#setUserVisibleHint(" + isVisibleToUser + ")=" + index);

        mFragmentController.setUserVisibleHint(isVisibleToUser);
    }

    public String getUid() {
        if (getParentFragment() instanceof LazyFragment) {
            return ((LazyFragment) getParentFragment()).getUid() + "#" + uid;
        }
        return uid;
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }
}
