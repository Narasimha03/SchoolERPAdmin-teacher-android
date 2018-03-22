package com.example.medianet.proschool;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by JANI on 07-06-2017.
 */

public class TeacherFragment extends Fragment{
    RecyclerView teacherRecyclerView;
    TeacherAdapter teacherAdapter;
    ArrayList<Teacher> teacherArrayList = new ArrayList<Teacher>();
    //
    FloatingActionButton fab;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    //
    Spinner genderSpinner;
    ArrayList<String> genderList = new ArrayList<String>();
    ArrayAdapter<String> genderAdapter;

    public TeacherFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View teacherView = inflater.inflate(R.layout.teachers_layout, container, false);
        //
        fab = (FloatingActionButton) teacherView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_teacher_dialog, null);
                genderSpinner = (Spinner) alertLayout.findViewById(R.id.genderSpinner);
                genderList.add("-- Select --");
                genderList.add("Male");
                genderList.add("Female");
                genderAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, genderList);
                genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                genderSpinner.setAdapter(genderAdapter);

                dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Add Teacher");
                dialogBuilder.setView(alertLayout);
                dialogBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogBuilder.setNegativeButton("RESET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogBuilder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });

        teacherRecyclerView = (RecyclerView) teacherView.findViewById(R.id.teacherRecyclerView);
        Teacher teacher = new Teacher("JANI", "12-07-92", "shaik@gmail.com", "9966004466");
        if (teacherArrayList.size() == 0){
            teacherArrayList.add(teacher);
        }
        teacherAdapter = new TeacherAdapter(getActivity(), teacherArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        teacherRecyclerView.setLayoutManager(linearLayoutManager);
        teacherRecyclerView.setAdapter(teacherAdapter);
        teacherAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Teacher");

        return teacherView;
    }
}
