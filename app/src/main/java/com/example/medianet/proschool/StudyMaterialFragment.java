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

public class StudyMaterialFragment extends Fragment {
    RecyclerView studyMaterialRecylerView;
    AssignmentAdapter studyMaterialAdapter;
    ArrayList<Assignments> liststudyMaterial = new ArrayList<Assignments>();

    public StudyMaterialFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View studymaterialview = inflater.inflate(R.layout.study_material_layout, container, false);
        studyMaterialRecylerView = (RecyclerView) studymaterialview.findViewById(R.id.studyMaterialRecylerView);

        Assignments assignments = new Assignments("AdminFlowChart", "Assignments", "07-04-2017", "All Classes", "AdminFlowChart", "Assignments", "07-04-2017");

        if (liststudyMaterial.size() == 0){
            liststudyMaterial.add(assignments);
        }

        studyMaterialAdapter = new AssignmentAdapter(getActivity(), liststudyMaterial);
        studyMaterialRecylerView.setAdapter(studyMaterialAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        studyMaterialRecylerView.setLayoutManager(linearLayoutManager);
        studyMaterialAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Study Material");

        return studymaterialview;
    }
}
