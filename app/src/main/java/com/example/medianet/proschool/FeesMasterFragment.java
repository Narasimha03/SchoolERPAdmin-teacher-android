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
import android.widget.ScrollView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by JANI on 09-05-2017.
 */

public class FeesMasterFragment extends Fragment {
    //Recyclerview....
    RecyclerView recycler_view;
    ArrayList<FeeMaster> feeMasterArrayList = new ArrayList<FeeMaster>();
    FeeMasterAdapter feeMasterAdapter;
    //FloatingActionButton
    FloatingActionButton fab;
    AlertDialog.Builder addFeeMasterBuilder;
    AlertDialog addFeeMasterDialog;
    Context mcontext;
    public FeesMasterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View feeMasterView = inflater.inflate(R.layout.fees_master_fragment, container, false);
        mcontext=getActivity();

        //RecyclerView
        recycler_view = (RecyclerView) feeMasterView.findViewById(R.id.recycler_view);
        FeeMaster feeMaster = new FeeMaster("1", "Class1", "Hostel", "INR 3000");
        FeeMaster feeMaster1 = new FeeMaster("2", "Class2", "Academic", "INR 3000");
        FeeMaster feeMaster2 = new FeeMaster("3", "Class3", "School", "INR 3000");
        FeeMaster feeMaster3 = new FeeMaster("4", "Class4", "Transport", "INR 3000");
        FeeMaster feeMaster4 = new FeeMaster("5", "Class5", "Tution Fee", "INR 3000");
        FeeMaster feeMaster5 = new FeeMaster("6", "Class6", "Hostel", "INR 3000");
        FeeMaster feeMaster6 = new FeeMaster("7", "Class7", "School", "INR 3000");

        feeMasterArrayList.add(feeMaster);
        feeMasterArrayList.add(feeMaster1);
        feeMasterArrayList.add(feeMaster2);
        feeMasterArrayList.add(feeMaster3);
        feeMasterArrayList.add(feeMaster4);
        feeMasterArrayList.add(feeMaster5);
        feeMasterArrayList.add(feeMaster6);

        feeMasterAdapter = new FeeMasterAdapter(getActivity(), feeMasterArrayList);
        recycler_view.setAdapter(feeMasterAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        feeMasterAdapter.notifyDataSetChanged();
        //Floating action button
        fab = (FloatingActionButton) feeMasterView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFeeMasterBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_fee_master_layout, null);
                //Fee Category Spinner
                Spinner feeCategorySpinner = (Spinner) alertLayout.findViewById(R.id.feeCategorySpinner);
                ArrayList<String> feeCategoryList = new ArrayList<String>();
                feeCategoryList.add("-- Select --");
                feeCategoryList.add("Monthly Fee");
                feeCategoryList.add("Academic Fee");
                feeCategoryList.add("Extra Fee");
                ArrayAdapter<String> feeCategoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, feeCategoryList);
                feeCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                feeCategorySpinner.setAdapter(feeCategoryAdapter);
                //Fee Type Spinner
                Spinner feeTypeSpinner = (Spinner) alertLayout.findViewById(R.id.feeTypeSpinner);
                ArrayList<String> feeTypeList = new ArrayList<String>();
                feeTypeList.add("-- Select --");
                feeTypeList.add("School");
                feeTypeList.add("Hostel");
                feeTypeList.add("Transport");
                feeTypeList.add("Academic");
                feeTypeList.add("Tution Fee");
                feeTypeList.add("Extra Curricular");
                ArrayAdapter<String> feeTypeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, feeTypeList);
                feeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                feeTypeSpinner.setAdapter(feeTypeAdapter);
                //Class Spinner
                Spinner feeClassSpinner = (Spinner) alertLayout.findViewById(R.id.feeClassSpinner);
                ArrayList<String> classList = new ArrayList<String>();
                classList.add("-- Select --");
                classList.add("Class 1");
                classList.add("Class 2");
                classList.add("Class 3");
                classList.add("Class 4");
                classList.add("Class 5");
                classList.add("Class 6");
                classList.add("Class 7");
                classList.add("Class 8");
                classList.add("Class 9");
                classList.add("Class 10");
                classList.add("Class 11");
                classList.add("Class 12");
                classList.add("Pre KG");
                classList.add("UKG");
                classList.add("LKG");
                // Creating adapter for spinner
                ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, classList);
                classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                feeClassSpinner.setAdapter(classAdapter);
                //Scroll View
                ScrollView scrollView = new ScrollView(getActivity());
                scrollView.addView(alertLayout);
                scrollView.setFillViewport(true);
                addFeeMasterBuilder.setView(scrollView);
                addFeeMasterBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                addFeeMasterBuilder.setNegativeButton("RESET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                addFeeMasterDialog = addFeeMasterBuilder.create();
                addFeeMasterDialog.show();
            }
        });
        // to set action bar title....
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Fees Master");

        return feeMasterView;
    }
}
