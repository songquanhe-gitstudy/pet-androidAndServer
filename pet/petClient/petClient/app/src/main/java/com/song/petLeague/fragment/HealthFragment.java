package com.song.petLeague.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.petLeague.R;

/**
 * Created by song on 2017/3/20.
 */

public class HealthFragment extends Fragment {

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_health, container, false);

        return view;
    }
}




















