package com.example.medianet.proschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JANI on 30-06-2017.
 */

public class ViewPagerPlaceFragment extends Fragment{
    View tabView;

    public ViewPagerPlaceFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tabView = inflater.inflate(R.layout.fragment_view_pager_place, container, false);
        return tabView;
    }
}
