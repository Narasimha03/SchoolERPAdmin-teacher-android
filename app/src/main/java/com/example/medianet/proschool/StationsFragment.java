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
 * Created by JANI on 03-06-2017.
 */

public class StationsFragment extends Fragment{
    RecyclerView stationRecylerView;
    StationAdapter stationAdapter;
    ArrayList<Stations> stationsArrayList = new ArrayList<Stations>();
    //
    FloatingActionButton fab;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    public StationsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View stationsView = inflater.inflate(R.layout.stations_layout, container, false);
        //
        fab = (FloatingActionButton) stationsView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_station_dialog, null);
                dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Create Station");
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
                dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });

        stationRecylerView = (RecyclerView) stationsView.findViewById(R.id.stationRecylerView);

        //Stations stations = new Stations("Station1", "STA-Code1", "11122.356");

        if (stationsArrayList.size() == 0){
            //stationsArrayList.add(stations);
        }

        stationAdapter = new StationAdapter(getActivity(), stationsArrayList);
        stationRecylerView.setAdapter(stationAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        stationRecylerView.setLayoutManager(linearLayoutManager);
        stationAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Stations");

        return stationsView;
    }
}
