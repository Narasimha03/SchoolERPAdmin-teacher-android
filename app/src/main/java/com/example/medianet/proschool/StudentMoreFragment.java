package com.example.medianet.proschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JANI on 12-06-2017.
 */

public class StudentMoreFragment extends Fragment{

    public StudentMoreFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View moreStudentView = inflater.inflate(R.layout.students_more_layout, container, false);
        return moreStudentView;
    }
}
