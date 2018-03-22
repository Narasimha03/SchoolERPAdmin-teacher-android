package com.example.medianet.proschool.suresh.checkboxs;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AddStudentBulkAttendence;
import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.StudentAttandenceModel;
import com.example.medianet.proschool.StudentAttendanceAdapter;
import com.example.medianet.proschool.StudentAttendenceFragment;
import com.example.medianet.proschool.suresh.checkboxs.CustomAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 06-06-2017.
 */

public class DemoCheckBoxFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        AllStudentsBackTask.AllStudents {

    View studentAttendanceView;
    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout std_layout;
    TextView textStdCount;
    // Edit Search....
    EditText editSearch;
    ImageView imgSearch;
    // Class Spinner....
    String classKey, selectedClass;
    Spinner classSpinner;
    Map<String, String> classMap = new LinkedHashMap<String, String>();
    ArrayList<String> classList;
    ArrayAdapter<String> classAdapter;
    // Section Spinner....
    String sectionKey, selectedSection;
    Spinner sectionSpinner;
    Map<String, String> sectionMap = new LinkedHashMap<String, String>();
    ArrayList<String> sectionList;
    ArrayAdapter<String> sectionAdapter;
    //
    Button submit, holiday, selectAllPresent, selectAllAbsent, selectAllLeave, deselectAll, btnDemo;
    FloatingActionButton search;
    //
    RecyclerView recycler_view;
    RecyclerView.Adapter mAdapter;

    DemoCheckAdapter studentAttendanceAdapter;
    ArrayList<DemoCheckBox> listStudents = new ArrayList<DemoCheckBox>();
    //  private  String[] animallist = new String[]{"Present1", "Present2", "Present3", "Present4"};
    List<DemoCheckBox> stList1 = null;
    private ArrayList<DemoCheckBox> modelArrayList;
    private String studentArray[];
    Context mContext;

    String schoolId;


    public DemoCheckBoxFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        studentAttendanceView = inflater.inflate(R.layout.student_check_attendence_layout, container, false);
        mContext = getActivity();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new ClassBackGroundTask(getActivity(), DemoCheckBoxFragment.this).execute(schoolId);

        //
        sliding_layout = (SlidingUpPanelLayout) studentAttendanceView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        std_layout = (LinearLayout) studentAttendanceView.findViewById(R.id.std_layout);
        textStdCount = (TextView) studentAttendanceView.findViewById(R.id.textStdCount);
        //
        // editSearch = (EditText) studentAttendanceView.findViewById(R.id.editSearch);
        // imgSearch = (ImageView) studentAttendanceView.findViewById(R.id.imgSearch);

        listStudents = getModel(false, false, false, false);
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();*/

        // class spinner....
        classSpinner = (Spinner) studentAttendanceView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        //listStudents = getModel(false);


        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), DemoCheckBoxFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) studentAttendanceView.findViewById(R.id.sectionSpinner);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
        search = (FloatingActionButton) studentAttendanceView.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listStudents.clear();
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new AllStudentsBackTask(getActivity(), DemoCheckBoxFragment.this).execute(sectionKey);
                } else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });



      /*  submit = (Button) studentAttendanceView.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data ="";
                String present="";
                String absent="";
                List<DemoCheckBox> stList = ((DemoCheckAdapter) studentAttendanceAdapter)
                        .getStudentsDetailsList();

                System.out.println("student ids:" + data);
                System.out.println("student status:" + present);
                System.out.println("student status:" + absent);

                JSONArray jsonArray = new JSONArray();
                JSONObject cartItemsObjedct;
                for (int n = 0; n < stList.size(); n++) {
                    DemoCheckBox singleStudent1 = stList.get(n);

                    if (singleStudent1.getDemoSelected() == true||singleStudent1.getAbsentSelected()==true||singleStudent1.getLeaveSelected()==true) {


                        // if (singleStudent1.getDemoSelected() == true) {

                        data = singleStudent1.getStdId();

                        present = singleStudent1.getPresentStatus();

                        System.out.println("present data" + present + "" + data);




                        if (data != null && present!=null) {


                            cartItemsObjedct = new JSONObject();
                            try {

                                cartItemsObjedct.putOpt("student_id",
                                        data);

                                cartItemsObjedct.putOpt("status", present);
                                jsonArray.put(cartItemsObjedct);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject singleObject = new JSONObject();
                            try {
                                singleObject.put("students", jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String str = singleObject.toString();
                            System.out.println("data is:" + str);


                            AddStudentBulkAttendence addStudentBulkAttendence = new AddStudentBulkAttendence(getActivity());

                            addStudentBulkAttendence.execute(String.valueOf(singleObject), classKey, sectionKey, Constants.schoolId);
                        }
                        else
                        {

                            Toast.makeText(getActivity(),
                                    "Please Select Atleast one student: " + singleStudent1.getPresentStatus(), Toast.LENGTH_LONG).show();


                        }
                    }

                    else
                    {

                        Toast.makeText(getActivity(),
                                "Please Select students " + singleStudent1.getPresentStatus(), Toast.LENGTH_LONG).show();

                    }

                }
            }
            //   }
        });*/


        btnDemo = (Button) studentAttendanceView.findViewById(R.id.btnDemo);
        btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sectionKey != null && !sectionKey.isEmpty()) {

                    listStudents = getModel(true, false, false, false);
                    //  listStudents = getModel1(false);
                    //   listStudents = getModel2(false);

                    studentAttendanceAdapter = new DemoCheckAdapter(getActivity(), listStudents);
                    recycler_view.setAdapter(studentAttendanceAdapter);
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);
                    //studentAttendanceAdapter.notifyDataSetChanged();
             /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();*/

                } else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        selectAllPresent = (Button) studentAttendanceView.findViewById(R.id.btnPresent);
        selectAllPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sectionKey != null && !sectionKey.isEmpty()) {

                    //listStudents = getModel2(true);
                    listStudents = getModel(false, true, false, false);
                    //   listStudents = getModel1(false);

                    studentAttendanceAdapter = new DemoCheckAdapter(getActivity(), listStudents);
                    recycler_view.setAdapter(studentAttendanceAdapter);
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);
                    //    studentAttendanceAdapter.notifyDataSetChanged();

              /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();*/

                } else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        selectAllAbsent = (Button) studentAttendanceView.findViewById(R.id.btnAbsent);
        selectAllAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // listStudents = getModel1(true);
                if (sectionKey != null && !sectionKey.isEmpty()) {

                    listStudents = getModel(false, false, true, false);
                    // listStudents = getModel2(false);

                    studentAttendanceAdapter = new DemoCheckAdapter(getActivity(), listStudents);
                    recycler_view.setAdapter(studentAttendanceAdapter);
                    //  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    // recycler_view.setLayoutManager(linearLayoutManager);
                    //  studentAttendanceAdapter.notifyDataSetChanged();
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);

                } else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectAllLeave = (Button) studentAttendanceView.findViewById(R.id.btnLeave);
        selectAllLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sectionKey != null && !sectionKey.isEmpty()) {

                    //listStudents = getModel2(true);
                    listStudents = getModel(false, false, false, true);
                    //   listStudents = getModel1(false);

                    studentAttendanceAdapter = new DemoCheckAdapter(getActivity(), listStudents);
                    recycler_view.setAdapter(studentAttendanceAdapter);
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);
                    // studentAttendanceAdapter.notifyDataSetChanged();

              /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();*/

                } else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deselectAll = (Button) studentAttendanceView.findViewById(R.id.btnDeselect);
        deselectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (sectionKey != null && !sectionKey.isEmpty()) {


                    listStudents = getModel(false, false, false, false);
                    //  listStudents = getModel1(false);
                    //  listStudents = getModel2(false);


                    studentAttendanceAdapter = new DemoCheckAdapter(getActivity(), listStudents);
                    recycler_view.setAdapter(studentAttendanceAdapter);
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);
                    // studentAttendanceAdapter.notifyDataSetChanged();
              /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();*/
                } else {
                    Toast.makeText(getActivity(), "Please Select AtLeast One Student To Deselect...!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holiday = (Button) studentAttendanceView.findViewById(R.id.holiday);
        holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String first = null;
               /* String presentSend = null;
                String absentSend = null;
                String leaveSend = null;*/
                String statusSend = "";
                JSONArray jsonArray = new JSONArray();
                JSONObject cartItemsObjedct;
                String present = "Present";
                String absent = "Absent";
                String onLeave = "On Leave";
                String demo = "Demo";

                // stList1 = DemoCheckAdapter.studentsDetailsArrayList;
               /* List<DemoCheckBox> stList1 = ((DemoCheckAdapter) studentAttendanceAdapter)
                        .getStudentsDetailsList();*/
                //  if (stList1.size() > 0) {
                for (int i = 0; i < DemoCheckAdapter.studentsDetailsList.size(); i++) {
                    //   DemoCheckBox singleStudent1 = stList1.get(i);

                    DemoCheckBox singleStudent1 = DemoCheckAdapter.studentsDetailsList.get(i);

                    if (singleStudent1.getSelected()) {

                        first = singleStudent1.getStdId();

                        //presentSend = singleStudent1.getPresentStatus();
                        statusSend = demo;
                        //   absentSend=absentSend=singleStudent1.getAbsentStatus();
                        // leaveSend=leaveSend=singleStudent1.getOnLeaveStatus();


                        System.out.println("Check Latest Demo data" + first + "" + statusSend);


                    } else if (singleStudent1.getPresentSelected()) {
                        first = singleStudent1.getStdId();

                        //   absentSend = singleStudent1.getAbsentStatus();
                        statusSend = present;


                        System.out.println("Check Latest Present data" + first + "" + statusSend);


                    } else if (singleStudent1.getAbsentSelected()) {
                        first = singleStudent1.getStdId();

                        //   absentSend = singleStudent1.getAbsentStatus();
                        statusSend = absent;


                        System.out.println("Check Latest Absent data" + first + "" + statusSend);


                    } else if (singleStudent1.getLeaveSelected()) {
                        first = singleStudent1.getStdId();

                        //  leaveSend = singleStudent1.getOnLeaveStatus();
                        statusSend = onLeave;


                        System.out.println("Check Latest on leave  data" + first + "" + statusSend);

                    } else {

                    }

                    System.out.println("Check Latest on final array  data" + first + "" + statusSend);


                    if (singleStudent1.getSelected() || singleStudent1.getPresentSelected() || singleStudent1.getAbsentSelected() || singleStudent1.getLeaveSelected() == true) {


                        cartItemsObjedct = new JSONObject();
                        try {


                            cartItemsObjedct.putOpt("student_id",
                                    first);

                            cartItemsObjedct.putOpt("status", statusSend);
                            jsonArray.put(cartItemsObjedct);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONObject singleObject = new JSONObject();
                        try {
                            singleObject.put("students", jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String str = singleObject.toString();
                        System.out.println("data is:" + str);


                        AddStudentBulkAttendence addStudentBulkAttendence = new AddStudentBulkAttendence(getActivity());

                        addStudentBulkAttendence.execute(String.valueOf(singleObject), classKey, sectionKey, schoolId);
                    } else {

                        Toast.makeText(getActivity(),
                                "Please Select Atleast one student Status ", Toast.LENGTH_SHORT).show();


                    }
                }


/*

                listStudents = getModel(true);






               studentAttendanceAdapter = new StudentAttendanceAdapter(getActivity(),listStudents);
                recycler_view.setAdapter(studentAttendanceAdapter);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();
                //Sliding Panel
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(120);*/

/*
                stList1 = ((StudentAttendanceAdapter) studentAttendanceAdapter)
                        .getStudentsDetailsList();
                stList1 = getModel(true);*/

                //  studentAttendanceAdapter = new StudentAttendanceAdapter(getActivity(),stList1);
                // recycler_view.setAdapter(studentAttendanceAdapter);


                //   studentAttendanceAdapter = new StudentAttendanceAdapter(getActivity(),listStudents);
                //  recycler_view.setAdapter(studentAttendanceAdapter);
                //   List<StudentAttandenceModel> stList1 = ((StudentAttendanceAdapter) studentAttendanceAdapter)
                //     .getStudentsDetailsList();

/*
                for (int n = 0; n < stList1.size(); n++) {
                    StudentAttandenceModel singleStudent1 = stList1;
                    singleStudent1.isSelectedPresent(true);
                }*/




                   /* if (singleStudent1.isSelected() == true) {




                        System.out.println("present data" + present + "" + data);*/


              /*  listStudents = getModel(true);
                listStudents=

               studentAttendanceAdapter = new StudentAttendanceAdapter(getActivity(),listStudents);
                recycler_view.setAdapter(studentAttendanceAdapter);*/
                //   }
            }
        });

        //
        recycler_view = (RecyclerView) studentAttendanceView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Student Attendance");

        return studentAttendanceView;
    }

    private ArrayList<DemoCheckBox> getModel(boolean isDemo, boolean isPresent, boolean isAbsent, boolean isLeave) {

        System.out.println("method");

        ArrayList<DemoCheckBox> list = new ArrayList<>();

        String check;
        boolean status;


        for (int i = 0; i < DemoCheckAdapter.studentsDetailsList.size(); i++) {
            System.out.println("loop" + i);

            DemoCheckBox model1 = DemoCheckAdapter.studentsDetailsList.get(i);
            check = model1.getStdName();
            isDemo = model1.getSelected();
            System.out.println("status" + isDemo);
            status = isDemo;
            model1.setSelected(status);

            model1.setPresentSelected(isPresent);

            model1.setAbsentSelected(isAbsent);
            model1.setLeaveSelected(isLeave);

            model1.setStdName(check);


            System.out.println("looping data name" + model1);
            list.add(model1);
        }
        return list;
    }


    @Override
    public void onClassResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            try {
                JSONObject classObject = new JSONObject(response);
                JSONArray classArray = classObject.getJSONArray("school_classes");
                int count = 0;
                while (count < classArray.length()) {
                    JSONObject cObject = classArray.getJSONObject(count);
                    classMap.put(cObject.getString("class_id"), cObject.getString("name"));
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            classList = new ArrayList<String>(classMap.values());
            // Creating adapter for spinner
            classAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, classList);
            classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            classSpinner.setAdapter(classAdapter);
            classAdapter.notifyDataSetChanged();
            classSpinner.destroyDrawingCache();
        }
    }

    @Override
    public void onSectionResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            sectionMap.clear();
            sectionMap.put("", "-- select --");
            JSONObject secObject = new JSONObject(response);
            JSONArray secArray = secObject.getJSONArray("class_sections");
            int count = 0;
            while (count < secArray.length()) {
                JSONObject jsonObject = secArray.getJSONObject(count);
                sectionMap.put(jsonObject.getString("section_id"), jsonObject.getString("name"));
                count++;
            }
            sectionList = new ArrayList<String>(sectionMap.values());
            // Creating adapter for student spinner
            sectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sectionList);
            sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sectionSpinner.setAdapter(sectionAdapter);
            sectionAdapter.notifyDataSetChanged();
            sectionSpinner.destroyDrawingCache();
        }
    }

    @Override
    public void allStudents(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listStudents.clear();
            String stdName;
            String parentName = "";
            JSONObject stdObject = new JSONObject(result);
            JSONArray jsonArray = stdObject.getJSONArray("students");
            if (stdObject.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    stdName = jsonObject.getString("first_name") + jsonObject.getString("last_name");
                    // Parent Array....
                    if (!jsonObject.isNull("parent")) {
                        JSONArray parentArray = jsonObject.getJSONArray("parent");
                        int p = 0;
                        while (p < parentArray.length()) {
                            JSONObject parentObject = parentArray.getJSONObject(p);
                            parentName = parentObject.getString("parent_name");
                            p++;
                        }
                    }
                    String present = "Present";
                    String absent = "Absent";
                    String onLeave = "On Leave";
                    String demo = "Demo";


                    DemoCheckBox studentsDetails = new DemoCheckBox(jsonObject.getString("student_id"), jsonObject.getString("admission_no"),
                            stdName, jsonObject.getString("class_id"), parentName, jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("category"), jsonObject.getString("phone"), jsonObject.getString("phone"),
                            jsonObject.getString("roll_no"), demo, present, absent, onLeave);
                    listStudents.add(studentsDetails);

                    count++;
                }
                //Count....
                String taskCount = "Students (" + String.valueOf(listStudents.size()) + ")";
                //   int taskCount1 = listStudents.size();

                textStdCount.setText(taskCount);
                //
                studentAttendanceAdapter = new DemoCheckAdapter(getActivity(), listStudents);
                recycler_view.setAdapter(studentAttendanceAdapter);
                // recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();
                recycler_view.destroyDrawingCache();
                //Sliding Panel
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(120);
            } else {
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();

        }
    }

    public SlidingUpPanelLayout getSliding_layout() {
        return sliding_layout;
    }*/
}
