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

public class ClassListFragment extends Fragment {
    //
    RecyclerView classListRecyclerView;
    RoomTypeAdapter roomTypeAdapter;
    ArrayList<RoomType> roomTypeArrayList = new ArrayList<RoomType>();
    //
    FloatingActionButton fab;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    public ClassListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View classListView = inflater.inflate(R.layout.class_list_layout, container, false);
        //
        fab = (FloatingActionButton) classListView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_class_dialog, null);
                dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setTitle("Add Class");
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

        classListRecyclerView = (RecyclerView) classListView.findViewById(R.id.classListRecyclerView);
        RoomType roomType = new RoomType("Class 1");

        if (roomTypeArrayList.size() == 0){
            roomTypeArrayList.add(roomType);
        }
        roomTypeAdapter = new RoomTypeAdapter(getActivity(), roomTypeArrayList);
        classListRecyclerView.setAdapter(roomTypeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        classListRecyclerView.setLayoutManager(linearLayoutManager);
        roomTypeAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Class List");

        return classListView;
    }
}
