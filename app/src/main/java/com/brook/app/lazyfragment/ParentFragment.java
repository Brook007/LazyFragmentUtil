package com.brook.app.lazyfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ParentFragment extends Fragment {


    private int index;

    public static Fragment newInstance(int index) {
        ParentFragment parentFragment = new ParentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);

        parentFragment.setArguments(bundle);
        return parentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        index = arguments.getInt("index");
        Log.d("Brook", this.getClass().getName() + "#onCreateView=" + index);
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

    private void loadData() {
        Log.d("Brook", this.getClass().getName() + "#loadData=" + index);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Brook", this.getClass().getName() + "#onResume=" + index);
        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Brook", this.getClass().getName() + "#onPause=" + index);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Brook", this.getClass().getName() + "#onStop=" + index);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("Brook", this.getClass().getName() + "#onHiddenChanged==" + index);
        if (!hidden) {
            loadData();
        }
    }
}
