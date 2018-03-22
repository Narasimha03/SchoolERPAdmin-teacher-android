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

public class RouteFragment extends Fragment{
    RecyclerView routeRecylerView;
    RoutesAdapter routesAdapter;
    ArrayList<Routes> routesArrayList = new ArrayList<Routes>();
    //FAB...
    FloatingActionButton fab;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    public RouteFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View routeView = inflater.inflate(R.layout.route_layout, container, false);
        //
        fab = (FloatingActionButton) routeView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_route_layout, null);
                dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Create Route");
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

        routeRecylerView = (RecyclerView) routeView.findViewById(R.id.routeRecylerView);
        Routes routes = new Routes("Route1", "AP31DL5566", "V123456", "INR 122.0", "Route1", "AP31DL5566", "V123456", "INR 122.0");
        if (routesArrayList.size() == 0){
            routesArrayList.add(routes);
        }

        routesAdapter = new RoutesAdapter(getActivity(), routesArrayList);
        routeRecylerView.setAdapter(routesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        routeRecylerView.setLayoutManager(linearLayoutManager);
        routesAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Route");
        return routeView;
    }
}
