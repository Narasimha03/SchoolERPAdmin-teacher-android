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

public class OtherDownloadFragment extends Fragment {

    RecyclerView otherDownloadRecylerView;
    AssignmentAdapter otherDownloadAdapter;
    ArrayList<Assignments> listotherDownload = new ArrayList<Assignments>();

    public OtherDownloadFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View otherDownloadList = inflater.inflate(R.layout.other_download_layout, container, false);
        otherDownloadRecylerView = (RecyclerView) otherDownloadList.findViewById(R.id.otherDownloadRecylerView);

        Assignments assignments = new Assignments("AdminFlowChart", "Assignments", "07-04-2017", "All Classes", "AdminFlowChart", "Assignments", "07-04-2017");

        if (listotherDownload.size() == 0){
            listotherDownload.add(assignments);
        }

        otherDownloadAdapter = new AssignmentAdapter(getActivity(), listotherDownload);
        otherDownloadRecylerView.setAdapter(otherDownloadAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        otherDownloadRecylerView.setLayoutManager(linearLayoutManager);
        otherDownloadAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Other Download List");

        return otherDownloadList;
    }
}
