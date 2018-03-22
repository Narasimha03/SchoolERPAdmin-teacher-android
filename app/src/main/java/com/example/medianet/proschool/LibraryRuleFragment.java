package com.example.medianet.proschool;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JANI on 18-06-2017.
 */

public class LibraryRuleFragment extends Fragment {
    //
    Context mContext;
    //
    View libraryRule;
    //
    FloatingActionButton fab;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    public LibraryRuleFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        libraryRule = inflater.inflate(R.layout.library_rule_layout, container, false);
        fab = (FloatingActionButton) libraryRule.findViewById(R.id.fab);
        //
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_library_rule_dialog, null);
                builder = new AlertDialog.Builder(mContext);
                builder.setView(alertLayout);
                builder.setPositiveButton("Add Rule", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Library Rules");

        return libraryRule;
    }
}
