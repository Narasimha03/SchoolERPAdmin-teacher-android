package com.example.medianet.proschool.suresh.timetable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medianet.proschool.R;

;

/**
 * Created by harish on 12/27/2017.
 */


public class TimetableNoticeFragment extends Fragment {

    Context mContext;
    View timetableNotice;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        timetableNotice = inflater.inflate(R.layout.timetable_notice_layout, container, false);
        mContext = getActivity();

        return timetableNotice;
    }
}
