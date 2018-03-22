package com.example.medianet.proschool.suresh.feemodule;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.medianet.proschool.AddVehicleBackTask;
import com.example.medianet.proschool.AllVehicleBackTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.VehicleAdapter;
import com.example.medianet.proschool.Vehicles;
import com.example.medianet.proschool.suresh.examination.AddExamPaperBackTask;
import com.example.medianet.proschool.suresh.feemodule.feemaster.AllFeeMasterFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by USER on 11/13/2017.
 */
/**
 * Created by JANI on 24-06-2017.
 */

public class FeeTypeFragmentMain extends Fragment implements AllFeeTypeBackTask.AllFeeType {

    View addVehicleView;
    //
    EditText editCode, editNumber;
    //
    RecyclerView recycler_view;
    ArrayList<FeeType> listVehicles = new ArrayList<FeeType>();
    FeeTypeAdapter feeTypeAdapter;
    //
    AlertDialog.Builder builder;
    AlertDialog dialog;
    String feeCategoryKey, selectedFeeCatogery;
    Spinner feeCategorySpinner;
    Map<String, String> feeCategoryMap = new LinkedHashMap<String, String>();
    ArrayList<String> feeCategorySpinnerList;
    ArrayAdapter<String> feeCategorySpinnerAdapter;
    ArrayList<String> feeCategoryList;


    FloatingActionButton fab, fab1, fab2, fab3,fab4,fab5,fab6;
    LinearLayout fabLayout1, fabLayout2,fabLayout3, fabLayout4,fabLayout5,fabLayout6;
    View fabBGLayout;
    boolean isFABOpen=false;
    Context mcontext;
    String schoolId;
    String role;

    public FeeTypeFragmentMain() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }







    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addVehicleView = inflater.inflate(R.layout.fee_type_layout, container, false);
mcontext=getActivity();

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        new AllFeeTypeBackTask(getActivity(), FeeTypeFragmentMain.this).execute(schoolId);



        fabLayout1= (LinearLayout) addVehicleView. findViewById(R.id.fabLayout1);

        fab = (FloatingActionButton) addVehicleView.  findViewById(R.id.fab);
        fab1 = (FloatingActionButton) addVehicleView.  findViewById(R.id.fab1);






        fabBGLayout= addVehicleView. findViewById(R.id.fabBGLayout);

        if (role.equals("admin")) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //    animateFAB();
                    if (!isFABOpen) {
                        showFABMenu();
                    } else {
                        closeFABMenu();
                    }
                }
            });
        }
        else  if (role.equals("teacher")) {
            fab.hide();
        }


        /*fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(this, FabMainActivity.class));
            }
        });*/


        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                //animateFAB();
            }
        });



        //   fab3 = (FloatingActionButton) addVehicleView.findViewById(R.id.fab);
        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_fee_type_dialog, null);


                // section spinner....
                feeCategorySpinner = (Spinner) alertLayout.findViewById(R.id.fee_category);
                feeCategoryMap.put("", "-- select --");
                feeCategoryMap.put("Annual Fee", "Annaul Fee");
                feeCategoryMap.put("Monthly Fee", "Monthly Fee");
                feeCategoryMap.put("Quarterly Fee", "Quarterly Fee");
                feeCategoryMap.put("Half-yearly Fee", "Half-yearly Fee");
                feeCategoryMap.put("Extra Fee", "Extra Fee");


                feeCategoryList = new ArrayList<String>(feeCategoryMap.values());

                feeCategorySpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, feeCategoryList);
                feeCategorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                feeCategorySpinner.setAdapter(feeCategorySpinnerAdapter);
                feeCategorySpinnerAdapter.notifyDataSetChanged();
                feeCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedFeeCatogery = feeCategorySpinner.getSelectedItem().toString();
                        feeCategoryKey = (String) getKeyFromValue(feeCategoryMap, selectedFeeCatogery);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

              //  editCode = (EditText) alertLayout.findViewById(R.id.editCode);
                editNumber = (EditText) alertLayout.findViewById(R.id.fee_type);
                builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Add Fee", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject addObject = new JSONObject();
                        try {
                            addObject.put("fee_category", feeCategoryKey);
                            addObject.put("fee_type", editNumber.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new AddFeeTypeBackTask(getActivity()).execute(String.valueOf(addObject), schoolId);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(alertLayout);
                dialog = builder.create();
                dialog.show();
            }
        });
        //
        recycler_view = (RecyclerView) addVehicleView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Fee");

        return addVehicleView;
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                // .addToBackStack(null)
                .commit();
    }


    private void showFABMenu(){
        isFABOpen=true;
        fabLayout1.setVisibility(View.VISIBLE);
       // fabLayout2.setVisibility(View.VISIBLE);
      //  fabLayout3.setVisibility(View.VISIBLE);


        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
     //   fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
     //   fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));


    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
       // fabLayout1.animate().translationY(0);
       // fabLayout2.animate().translationY(0);
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayout1.setVisibility(View.GONE);
                   // fabLayout2.setVisibility(View.GONE);
                  //  fabLayout3.setVisibility(View.GONE);

                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void allVehicle(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listVehicles.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("feeType"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("feetypes");
            System.out.println("feeTypelist"+jsonArray);

            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject feeTypeObject = jsonArray.getJSONObject(count);

                    FeeType feeType = new FeeType(feeTypeObject.getString("fee_type"), feeTypeObject.getString("fee_category"),
                            feeTypeObject.getString("_id"));
                    listVehicles.add(feeType);
                    count++;
                }

                feeTypeAdapter = new FeeTypeAdapter(getActivity(), listVehicles);
                recycler_view.setAdapter(feeTypeAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                feeTypeAdapter.notifyDataSetChanged();
            } else {
                recycler_view.setVisibility(View.GONE);
            }
        }

    }
    /*
    "{
    ""vehicles"": [
        {
            ""_id"": ""594a453183c11c17246a66b0"",
            ""vehicle_code"": ""1234"",
            ""vehicle_name"": ""test"",
            ""school_id"": ""SCH-9273"",
            ""status"": 1
        }
    ]
}"
     */
}
