package com.example.medianet.proschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by JANI on 03-06-2017.
 */

public class SyllabusFragment extends Fragment {

    RecyclerView syllabusRecylerView;
    AssignmentAdapter syllabusAdapter;
    ArrayList<Assignments> listsyllabus = new ArrayList<Assignments>();

    public SyllabusFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View syllabusView = inflater.inflate(R.layout.syllabus_layout, container, false);
        syllabusRecylerView = (RecyclerView) syllabusView.findViewById(R.id.syllabusRecylerView);

        Assignments assignments = new Assignments("AdminFlowChart", "Assignments", "07-04-2017", "All Classes", "AdminFlowChart", "Assignments", "07-04-2017");

        if (listsyllabus.size() == 0){
            listsyllabus.add(assignments);
        }

        syllabusAdapter = new AssignmentAdapter(getActivity(), listsyllabus);
        syllabusRecylerView.setAdapter(syllabusAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        syllabusRecylerView.setLayoutManager(linearLayoutManager);
        syllabusAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Syllabus List");

        return syllabusView;
    }
}
