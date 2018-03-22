package com.example.medianet.proschool.suresh.studentprofile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
;import com.example.medianet.proschool.R;

/**
 * Created by harish on 12/27/2017.
 */

public class StudentProfileAcadamicFragment extends Fragment {

    Context mContext;
    View studentAcademicView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        studentAcademicView = inflater.inflate(R.layout.student_profile_academic_layout, container, false);
        mContext = getActivity();

        return studentAcademicView;
    }
}
