package com.example.android1016.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android1016.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ViewPagerTwo extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viewpager_two, container, false);
        return root;

    }
}
