package com.brook.app.lazyfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ParentFragment extends LazyFragment {


    private int index;

    public static Fragment newInstance(int index) {
        ParentFragment parentFragment = new ParentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);

        parentFragment.setArguments(bundle);
        return parentFragment;
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btn = view.findViewById(R.id.parent_btn);

        btn.setText("父Fragment:" + index);

        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.parent_fragment_container, ChildFragment.newInstance(index));
        fragmentTransaction.commitNowAllowingStateLoss();
    }

}
