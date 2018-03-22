package com.example.medianet.proschool.suresh.feemodule.feemaster;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.medianet.proschool.AddVehicleBackTask;
import com.example.medianet.proschool.AllExamPaperBackTask;
import com.example.medianet.proschool.AllVehicleBackTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.FeeMaster;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.StudentDetailsFragment;
import com.example.medianet.proschool.VehicleAdapter;
import com.example.medianet.proschool.Vehicles;
import com.example.medianet.proschool.suresh.examination.AddExamPaperBackTask;
import com.example.medianet.proschool.suresh.examination.ExamEvaluationFragment;
import com.example.medianet.proschool.suresh.feemodule.AddFeeMasterBackTask;
import com.example.medianet.proschool.suresh.feemodule.AddFeeTypeBackTask;
import com.example.medianet.proschool.suresh.feemodule.AllFeeTypeBackTask;
import com.example.medianet.proschool.suresh.feemodule.FeeType;
import com.example.medianet.proschool.suresh.feemodule.FeeTypeAdapter;
import com.example.medianet.proschool.suresh.feemodule.feecollection.AllFeeCollectionFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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

public class AllFeeMasterFragment extends Fragment implements AllFeeMasterBackTask.AllFeeMaster {

    View addVehicleView;
    //
    EditText editCode, editNumber;
    //
    RecyclerView recycler_view;
    ArrayList<FeeType> listVehicles = new ArrayList<FeeType>();
    FeeTypeAdapter feeTypeAdapter;
    //
    SlidingUpPanelLayout sliding_layout;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    String feeMasterKey, selectedFeeMaster;
    Spinner feeMasterSpinner;
    Map<String, String> feeMasterMap = new LinkedHashMap<String, String>();
    ArrayList<String> feeMasterSpinnerList;
    ArrayAdapter<String> feeMasterSpinnerAdapter;
    ArrayList<String> feeMasterList;
    FrameLayout frameLayout;
    // Paper Spinner.... studentSpinner
    String paperkey, selectedPaper;
    Spinner paperSpinner;
    Map<String, String> paperMap = new LinkedHashMap<String, String>();
    ArrayList<String> paperList;
    ArrayAdapter<String> paperAdapter;


    //feetype
    String conductkey,feeTypeKey, selectedConduct,selectedConduct1;
    Spinner conductSpinner,feeTypeSpinner;
    Map<String, String> conductMap = new LinkedHashMap<String, String>();
    Map<String, String> conductMap1 = new LinkedHashMap<String, String>();

    ArrayList<String> conductList,conductList1;
    ArrayAdapter<String> conductAdapter,conductAdapter1;
    //
    Button addMarks;
FloatingActionButton select;
    FloatingActionButton fab, fab1, fab2, fab3, fab4, fab5, fab6;
    LinearLayout fabLayout1, fabLayout2, fabLayout3, fabLayout4, fabLayout5, fabLayout6;
    View fabBGLayout;
    boolean isFABOpen = false;
    Context mcontext;
    String schoolId;
    String role;

    public AllFeeMasterFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   new AllFeeTypeBackTask(getActivity(), AllFeeMasterFragment.this).execute(Constants.schoolId);
     //   new AllFeeTypeBackTask(getActivity(), AllFeeMasterFragment.this).execute(Constants.schoolId);

        //   new AllFeeTypeBackTask(getActivity(), AllFeeMasterFragment.this).execute(Constants.schoolId);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addVehicleView = inflater.inflate(R.layout.all_fee_master_layout_two, container, false);
        mcontext = getActivity();

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        new AllFeeMasterBackTask(getActivity(), AllFeeMasterFragment.this).execute(schoolId);

        /*sliding_layout = (SlidingUpPanelLayout) addVehicleView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);*/

        frameLayout = (FrameLayout) addVehicleView.findViewById(R.id.frameLayout);

        fabLayout1 = (LinearLayout) addVehicleView.findViewById(R.id.fabLayout1);
        /*fabLayout2 = (LinearLayout) addVehicleView.findViewById(R.id.fabLayout2);
        fabLayout3 = (LinearLayout) addVehicleView.findViewById(R.id.fabLayout3);*/
        fab = (FloatingActionButton) addVehicleView.findViewById(R.id.fab);
        /*fab1 = (FloatingActionButton) addVehicleView.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) addVehicleView.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) addVehicleView.findViewById(R.id.fab3);*/


        fabBGLayout = addVehicleView.findViewById(R.id.fabBGLayout);

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



        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();

            }
        });


        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_fee_master_layout_dialog, null);



             /*   // Student Spinner...
                feeTypeSpinner = (Spinner) alertLayout.findViewById(R.id.fee_category);
                feeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedConduct1 = feeTypeSpinner.getSelectedItem().toString();
                        feeTypeKey = (String) getKeyFromValue(conductMap1, selectedConduct1);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/

                /*// section spinner....
                feeMasterSpinner = (Spinner) alertLayout.findViewById(R.id.fee_category);
                feeMasterMap.put("", "-- select --");
               // feeMasterMap.put("Monthly Fee", "Monthly Fee");
              //  feeMasterMap.put("Annual Fee", "Annaul Fee");
                feeMasterList = new ArrayList<String>( feeMasterMap.values());

                feeMasterSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,  feeMasterList);
                feeMasterSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                feeMasterSpinner.setAdapter( feeMasterSpinnerAdapter);
                feeMasterSpinnerAdapter.notifyDataSetChanged();
                feeMasterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedFeeMaster =  feeMasterSpinner.getSelectedItem().toString();
                        feeMasterKey = (String) getKeyFromValue( feeMasterMap, selectedFeeMaster);
                       *//* if (feeMasterMap != null && !feeMasterMap.isEmpty()) {
                            new AllFeeTypeBackTask(getActivity(), AllFeeMasterFragment.this).execute(Constants.schoolId);

                        }*//*
                    }

*/


            /*   paperSpinner = (Spinner) alertLayout.findViewById(R.id.fee_category);
                paperSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedPaper = paperSpinner.getSelectedItem().toString();
                        paperkey = (String) getKeyFromValue(paperMap, selectedPaper);
*//*
                        if (paperkey != null && !paperkey.isEmpty()) {
                            //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                            //new AllStudentsBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                            new AllFeeTypeBackTask(getActivity(), AllFeeMasterFragment.this).execute(paperkey);

                        } else {
                            Toast.makeText(getActivity(), "Please Select Section.", Toast.LENGTH_LONG).show();
                        }*//*
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
*/

               /* feeMasterSpinner = (Spinner) alertLayout.findViewById(R.id.fee_category);
                feeMasterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedFeeMaster = feeMasterSpinner.getSelectedItem().toString();
                        feeMasterKey = (String) getKeyFromValue(feeMasterMap, selectedFeeMaster);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/


                //  editCode = (EditText) alertLayout.findViewById(R.id.editCode);
                editNumber = (EditText) alertLayout.findViewById(R.id.fee_amount);
                builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Add Fee Master", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject addObject = new JSONObject();
                        try {
                         //   addObject.put("fee_type",  feeTypeKey);
                            addObject.put("fee_amount", editNumber.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (addObject.length() > 0) {
                            if (editNumber != null) {
                                new AddFeeMasterBackTask(getActivity()).execute(String.valueOf(addObject),schoolId);
                            } else {
                                Toast.makeText(getActivity(), "Please select PAPER and STUDENT", Toast.LENGTH_LONG).show();
                            }
                        }
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
        //    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Fee");

        return addVehicleView;
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        /*fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);*/


        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        /*fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));*/


    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        /*fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);*/
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    /*fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);*/

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

/*
    @Override
    public void allVehicle(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            paperMap.clear();
            paperMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("feetypes");

//System.out.println("evalpapercheck"+jsonArray);
            // JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("evalpapercheck" + jsonArray);

            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject examObject = jsonArray.getJSONObject(count);
                    paperMap.put(examObject.getString("_id"), examObject.getString("fee_type"));
                    count++;
                }
                paperList = new ArrayList<String>(paperMap.values());
                //
                paperAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paperList);
                paperAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                paperSpinner.setAdapter(paperAdapter);
                paperAdapter.notifyDataSetChanged();
            }
        }
    }*/

        @Override
        public void allFeeMaster (String result) throws JSONException {

            if (result != null && !result.isEmpty()) {
                listVehicles.clear();
                JSONObject jsonObject = new JSONObject(result);
                System.out.println("feemaster" + jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("feemaster");
                System.out.println("feeTypelist" + jsonArray);

                if (jsonArray.length() > 0) {
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject feeTypeObject = jsonArray.getJSONObject(count);

                        FeeType feeType = new FeeType(feeTypeObject.getString("class_name"), feeTypeObject.getString("fee_type"),
                                feeTypeObject.getString("fee_amount"));
                        listVehicles.add(feeType);
                        count++;
                    }

                    feeTypeAdapter = new FeeTypeAdapter(getActivity(), listVehicles);
                    recycler_view.setAdapter(feeTypeAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recycler_view.setLayoutManager(linearLayoutManager);
                    feeTypeAdapter.notifyDataSetChanged();
                    /*sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);*/
                } else {
                    /*sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(0);*/
                    recycler_view.setVisibility(View.GONE);
                }
            }


        }

  /*  @Override
    public void allVehicle(String result) throws JSONException {


        if (result != null && !result.isEmpty()) {
            conductMap1.clear();
            conductMap1.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("feetypes");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject stdObject = jsonArray.getJSONObject(count);
                // JSONObject examObject = jsonArray.getJSONObject(count);


                conductMap1.put(stdObject.getString("fee_types_id"), stdObject.getString("fee_type"));


                // String name = stdObject.getString("first_name") + " " + stdObject.getString("last_name");
                //  conductMap1.put(stdObject.getString("student_id"), name);
                count++;
            }
            conductList1 = new ArrayList<String>(conductMap1.values());
            //
            conductAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, conductList1);
            conductAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            feeTypeSpinner.setAdapter(conductAdapter1);
            conductAdapter.notifyDataSetChanged();
        }


    }*/
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

