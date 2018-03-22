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

public class SubjectFragment extends Fragment {
    RecyclerView subjectRecyclerView;
    SubjectAdapter subjectAdapter;
    ArrayList<Subject> subjectArrayList = new ArrayList<Subject>();
    //
    FloatingActionButton fab;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    public SubjectFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View subjectView = inflater.inflate(R.layout.subjects_layout, container, false);
        //
        fab = (FloatingActionButton) subjectView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_subject_dialog, null);
                dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Add Subject");
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

        subjectRecyclerView = (RecyclerView) subjectView.findViewById(R.id.subjectRecyclerView);
        Subject subject = new Subject("Maths","suresh");
        if (subjectArrayList.size() == 0) {
            subjectArrayList.add(subject);
        }
        subjectAdapter = new SubjectAdapter(getActivity(), subjectArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        subjectRecyclerView.setLayoutManager(linearLayoutManager);
        subjectRecyclerView.setAdapter(subjectAdapter);
        subjectAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Subject");

        return subjectView;
    }
}
