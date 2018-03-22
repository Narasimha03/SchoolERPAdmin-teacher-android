package com.example.medianet.proschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 06-06-2017.
 */

public class AttendanceByDateFragment extends Fragment{
    // Class Spinner....
    Spinner classSpinner;
    List<String> classList;
    ArrayAdapter<String> classAdapter;
    // Section Spinner....
    Spinner sectionSpinner;
    List<String> sectionList;
    ArrayAdapter<String> sectionAdapter;

    public AttendanceByDateFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View attendanceView = inflater.inflate(R.layout.attendance_by_date, container, false);
        // class spinner....
        classSpinner = (Spinner) attendanceView.findViewById(R.id.classSpinner);
        classList = new ArrayList<String>();
        classList.add("-- Select --");
        classList.add("Class 1");
        classList.add("Class 2");
        classList.add("Class 3");
        classList.add("Class 4");
        classList.add("Class 5");
        classList.add("Class 6");
        classList.add("Class 7");
        classList.add("Class 8");
        classList.add("Class 9");
        classList.add("Class 10");
        classList.add("Class 11");
        classList.add("Class 12");
        classList.add("Pre KG");
        classList.add("UKG");
        classList.add("LKG");
        // Creating adapter for spinner
        classAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, classList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);
        // section spinner....
        sectionSpinner = (Spinner) attendanceView.findViewById(R.id.sectionSpinner);
        sectionList = new ArrayList<String>();
        sectionList.add("-- Select --");
        sectionList.add("Section A");
        sectionList.add("Section B");
        sectionList.add("Section C");
        // Creating adapter for student spinner
        sectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sectionList);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectionSpinner.setAdapter(sectionAdapter);
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Attendence By Date");

        return attendanceView;
    }
}
