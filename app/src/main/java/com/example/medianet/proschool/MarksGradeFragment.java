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

import java.util.ArrayList;

/**
 * Created by JANI on 07-06-2017.
 */

public class MarksGradeFragment extends Fragment {
    RecyclerView gradeRecylerView;
    MarksGradeAdapter marksGradeAdapter;
    ArrayList<MarksGrade> marksGradeArrayList = new ArrayList<MarksGrade>();
    //
    FloatingActionButton fab;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    public MarksGradeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View marksGradeView = inflater.inflate(R.layout.marks_grade_layout, container, false);
        //
        fab = (FloatingActionButton) marksGradeView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_grade_dialog, null);
                dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Add Grade");
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

        gradeRecylerView = (RecyclerView) marksGradeView.findViewById(R.id.gradeRecylerView);
        MarksGrade marksGrade = new MarksGrade("A", "90", "100");

        if (marksGradeArrayList.size() == 0){
            marksGradeArrayList.add(marksGrade);
        }

        marksGradeAdapter = new MarksGradeAdapter(getActivity(), marksGradeArrayList);
        gradeRecylerView.setAdapter(marksGradeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        gradeRecylerView.setLayoutManager(linearLayoutManager);
        marksGradeAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Marks Grade");

        return marksGradeView;
    }
}
