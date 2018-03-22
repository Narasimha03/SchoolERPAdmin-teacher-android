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
import java.util.List;

/**
 * Created by JANI on 03-06-2017.
 */

public class HostelRoomFragment extends Fragment {
    // Hostel Spinner....
    Spinner hostelSpinner;
    List<String> hostelList;
    ArrayAdapter<String> hostelAdapter;
    // Room Type Spinner....
    Spinner roomTypeSpinner;
    List<String> roomTypeList;
    ArrayAdapter<String> roomTypeAdapter;
    // RecylerView
    RecyclerView hostelRecylerView;
    HostelRoomAdapter hostelRoomAdapter;
    ArrayList<HostelRoom> hostelRoomArrayList = new ArrayList<HostelRoom>();
    //
    FloatingActionButton fab;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    public HostelRoomFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View hostelRoomView = inflater.inflate(R.layout.hostel_room_layout, container, false);

        fab = (FloatingActionButton) hostelRoomView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_hostel_room_dialog, null);
                // hostel spinner
                hostelSpinner = (Spinner) alertLayout.findViewById(R.id.hostelSpinner);
                hostelList = new ArrayList<String>();
                hostelList.add("-- Select --");
                hostelList.add("Moksha Bhavan");
                // setting adapter for hostel spinner
                hostelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, hostelList);
                hostelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                hostelSpinner.setAdapter(hostelAdapter);
                hostelAdapter.notifyDataSetChanged();
                // room type spinner
                roomTypeSpinner = (Spinner) alertLayout.findViewById(R.id.roomTypeSpinner);
                roomTypeList = new ArrayList<String>();
                roomTypeList.add("-- Select --");
                roomTypeList.add("Single Bed");
                // setting adapter for room type spinner
                roomTypeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, roomTypeList);
                roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                roomTypeSpinner.setAdapter(roomTypeAdapter);
                roomTypeAdapter.notifyDataSetChanged();
                dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Add Hostel Room");
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
                dialogBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
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
        //RecyclerView
        hostelRecylerView = (RecyclerView) hostelRoomView.findViewById(R.id.hostelRecylerView);

        HostelRoom hostelRoom = new HostelRoom("gu", "moksha bhavan", "single bed", "1", "INR200.00");
        if (hostelRoomArrayList.size() == 0){
            hostelRoomArrayList.add(hostelRoom);
        }
        hostelRoomAdapter = new HostelRoomAdapter(getActivity(), hostelRoomArrayList);
        hostelRecylerView.setAdapter(hostelRoomAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        hostelRecylerView.setLayoutManager(linearLayoutManager);
        hostelRoomAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Hostel Room");

        return hostelRoomView;
    }
}
