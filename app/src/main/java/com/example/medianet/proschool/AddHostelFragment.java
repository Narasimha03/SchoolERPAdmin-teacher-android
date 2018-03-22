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

public class AddHostelFragment extends Fragment {
    // Hostel Spinner....
    Spinner hostelTypeSpinner;
    List<String> hostelTypeList;
    ArrayAdapter<String> hostelTypeAdapter;
    //
    RecyclerView hostelListRecylerView;
    HostelAdapter hostelAdapter;
    ArrayList<Hostel> hostelArrayList = new ArrayList<Hostel>();
    //
    FloatingActionButton fab;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    public AddHostelFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addHostelView = inflater.inflate(R.layout.add_hostel_layout, container, false);

        fab = (FloatingActionButton) addHostelView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_hostel_dialog, null);
                // Hostel Type Spinner
                hostelTypeSpinner = (Spinner) alertLayout.findViewById(R.id.hostelTypeSpinner);
                hostelTypeList = new ArrayList<String>();
                hostelTypeList.add("-- Select --");
                hostelTypeList.add("Girls");
                hostelTypeList.add("Boys");
                hostelTypeList.add("Combine");
                // setting adapter for hostel spinner
                hostelTypeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, hostelTypeList);
                hostelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                hostelTypeSpinner.setAdapter(hostelTypeAdapter);
                hostelTypeAdapter.notifyDataSetChanged();

                dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setView(alertLayout);
                dialogBuilder.setTitle("Add Hostel");
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
        //RecyclerView
        hostelListRecylerView = (RecyclerView) addHostelView.findViewById(R.id.hostelListRecylerView);

        Hostel hostel = new Hostel("Moksha Bhavan", "Girls", "2/34", "22");
        if (hostelArrayList.size() == 0){
            hostelArrayList.add(hostel);
        }
        hostelAdapter = new HostelAdapter(getActivity(), hostelArrayList);
        hostelListRecylerView.setAdapter(hostelAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        hostelListRecylerView.setLayoutManager(linearLayoutManager);
        hostelAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Hostel");

        return addHostelView;
    }
}
