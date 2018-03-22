package com.example.medianet.proschool;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.medianet.proschool.suresh.checkboxs.CustomAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 06-06-2017.
 */

public class StudentAttendenceFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
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
    Button search,submit,holiday;
    //
    RecyclerView recycler_view;
    RecyclerView.Adapter mAdapter;

    StudentAttendanceAdapter studentAttendanceAdapter;
    ArrayList<StudentAttandenceModel> listStudents = new ArrayList<StudentAttandenceModel>();
  //  private  String[] animallist = new String[]{"Present1", "Present2", "Present3", "Present4"};
  List<StudentAttandenceModel> stList1;
    private ArrayList<StudentAttandenceModel> modelArrayList;
    private String studentArray[];
    Context mContext;


    public StudentAttendenceFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ClassBackGroundTask(getActivity(), StudentAttendenceFragment.this).execute(Constants.schoolId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        studentAttendanceView = inflater.inflate(R.layout.student_attendence_layout, container, false);
       mContext=getActivity();

        //
        sliding_layout = (SlidingUpPanelLayout) studentAttendanceView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        std_layout = (LinearLayout) studentAttendanceView.findViewById(R.id.std_layout);
        textStdCount = (TextView) studentAttendanceView.findViewById(R.id.textStdCount);
        //
       // editSearch = (EditText) studentAttendanceView.findViewById(R.id.editSearch);
       // imgSearch = (ImageView) studentAttendanceView.findViewById(R.id.imgSearch);
      //  modelArrayList = getModel(false);

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
                    new SectionBackGroundTask(getActivity(), StudentAttendenceFragment.this).execute(classKey);
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
        search = (Button) studentAttendanceView.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listStudents.clear();
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new AllStudentsBackTask(getActivity(), StudentAttendenceFragment.this).execute(sectionKey);
                } else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        submit = (Button) studentAttendanceView.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          String data ="";
                                          String present="";
                                          String absent="";
                                          List<StudentAttandenceModel> stList = ((StudentAttendanceAdapter) studentAttendanceAdapter)
                                                  .getStudentsDetailsList();

                                                  System.out.println("student ids:" + data);
                                                  System.out.println("student status:" + present);
                                                  System.out.println("student status:" + absent);

                                                  JSONArray jsonArray = new JSONArray();
                                                  JSONObject cartItemsObjedct;
                                                  for (int n = 0; n < stList.size(); n++) {
                                                      StudentAttandenceModel singleStudent1 = stList.get(n);

                                                     if (singleStudent1.isSelected() == true) {

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
        });


        holiday = (Button) studentAttendanceView.findViewById(R.id.holiday);
        holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // listStudents.clear();


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
            }
        });

        //
        recycler_view = (RecyclerView) studentAttendanceView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
     //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Student Attendance");

        return studentAttendanceView;
    }

    private ArrayList<StudentAttandenceModel> getModel(boolean isSelect){

        System.out.println("method");

      //  ArrayList<StudentAttandenceModel> list = new ArrayList<>();
       // int n=listStudents.size();
        System.out.println("student name"+listStudents);


        //  List<StudentAttandenceModel> stList1 = ((StudentAttendanceAdapter) studentAttendanceAdapter)
         //       .getStudentsDetailsList();
      //  System.out.println("size of students"+stList1);
        for(int i = 0; i <listStudents.size(); i++){
            System.out.println("loop"+i);


            StudentAttandenceModel model = new StudentAttandenceModel();

           ;
            model.setSelectedPresent(isSelect);
           // studentArray=model.getStdId();
         //   model.setStdId(String.valueOf(studentArray));
            System.out.println("looping data name"+studentArray);




        //    model.setStudentName(listStudents);
            listStudents.add(model);
        }
        return listStudents;
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

                    StudentAttandenceModel studentsDetails = new StudentAttandenceModel(jsonObject.getString("student_id"), jsonObject.getString("admission_no"),
                            stdName, jsonObject.getString("class_id"), parentName, jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("category"), jsonObject.getString("phone"), jsonObject.getString("phone"),
                            jsonObject.getString("roll_no"),false,false,false);
                    listStudents.add(studentsDetails);

                    count++;
                }
                //Count....
                String taskCount = "Students (" + String.valueOf(listStudents.size()) + ")";
             //   int taskCount1 = listStudents.size();

                textStdCount.setText(taskCount);
                //
                studentAttendanceAdapter = new StudentAttendanceAdapter(getActivity(), listStudents);
                recycler_view.setAdapter(studentAttendanceAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();
                //Sliding Panel
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(300);
            } else {
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
