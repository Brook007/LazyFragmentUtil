package com.brook.app.lazyfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChildFragment extends LazyFragment {


    public static Fragment newInstance(int index) {
        ChildFragment parentFragment = new ChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);

        parentFragment.setArguments(bundle);
        return parentFragment;
    }


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView viewById = view.findViewById(R.id.child_tv);
        viewById.setText("子Fragment:" + index);
    }

}
