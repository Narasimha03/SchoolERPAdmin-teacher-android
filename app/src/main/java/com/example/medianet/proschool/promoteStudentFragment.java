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
 * Created by JANI on 08-05-2017.
 */

public class promoteStudentFragment extends Fragment{
    // Class Spinner....
    Spinner classSpinner;
    List<String> classList;
    ArrayAdapter<String> classAdapter;
    // Section Spinner....
    Spinner sectionSpinner;
    List<String> sectionList;
    ArrayAdapter<String> sectionAdapter;

    public promoteStudentFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View promoteStudentView = inflater.inflate(R.layout.promote_student_layout, container, false);
        // class spinner....
        classSpinner = (Spinner) promoteStudentView.findViewById(R.id.classSpinner);
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
        sectionSpinner = (Spinner) promoteStudentView.findViewById(R.id.sectionSpinner);
        sectionList = new ArrayList<String>();
        sectionList.add("-- Select --");
        sectionList.add("Section A");
        sectionList.add("Section B");
        sectionList.add("Section C");
        //
        sectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sectionList);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectionSpinner.setAdapter(sectionAdapter);
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Select Criteria");

        return promoteStudentView;
    }
}
