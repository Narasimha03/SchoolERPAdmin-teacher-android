package com.example.medianet.proschool.suresh.HR;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medianet.proschool.R;

/**
 * Created by Harish on 30-12-2017.
 */

public class HrFragment extends Fragment {

    View HrView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HrView = inflater.inflate(R.layout.hr_coming_soon_layout, container, false);

        return HrView;
    }
}
