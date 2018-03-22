package com.example.medianet.proschool.suresh.employeeattendancetabs.dailyadapterandbacktask;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.StudentAttandenceModel;

import com.example.medianet.proschool.suresh.attendancemodule.daywisereport.AllAttendanceReportByDayAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.daywisereport.AllAttendanceReportByDayBackTask;
import com.example.medianet.proschool.suresh.employeeattendancetabs.EmpAttModel;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 17-06-2017.
 */

public class EmpAttendanceByDayFragment extends Fragment implements EmpAttDailyBackTask.AllAttendanceByday

       {

    View examEvalView;
    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout exam_layout;
    TextView textStdCount;
    ImageView imgLines;
    // Class Spinner....
    String classKey;



    // Type Spinner....
    String typeKey, selectedType;
    Spinner typeSpinner;
    Map<String, String> typeMap = new LinkedHashMap<String, String>();
    ArrayList<String> typeList;
    ArrayAdapter<String> typeAdapter;
    // Gender Spinner....
    String genderKey, genderSection;
    Spinner genderSpinner;
    Map<String, String> genderMap = new LinkedHashMap<String, String>();
    ArrayList<String> genderList;
    ArrayAdapter<String> genderAdapter;



    //
    FloatingActionButton select;
    //date
    EditText editAdmDate;
    //
    RecyclerView recycler_view;
    ArrayList<EmpAttModel> listMarks = new ArrayList<EmpAttModel>();
    EmpAttDailyAdapter empAttDailyAdapter;
    Context mContext;
    String schoolId;

    public EmpAttendanceByDayFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  new AllAttendanceReportsMonthBackTask(getActivity(), AttendanceReportsByMonth.this).execute(Constants.schoolId);

        //  new AllExamSchBackTask(getActivity(), AllFeeCollectionFragment.this).execute(Constants.schoolId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        examEvalView = inflater.inflate(R.layout.emp_all_attendance_day_layout, container, false);
        mContext=getActivity();
        sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examEvalView.findViewById(R.id.std_layout);
        textStdCount = (TextView) examEvalView.findViewById(R.id.textStdCount);


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");



        //admission date
        editAdmDate = (EditText) examEvalView.findViewById(R.id.editAdmDate);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        editAdmDate.setText(date);
        editAdmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerFragement2(), years, month, day);
                datePickerDialog.show();
            }
        });


        typeSpinner = (Spinner) examEvalView.findViewById(R.id.typeSpinner);
        typeMap.put("", "-- Select --");
        //typeMap.put("All", "All");

        typeMap.put("teaching", "Teaching");
        typeMap.put("non-teaching", "Non Teaching");
        typeMap.put("administrative", "Administrative");

        typeList = new ArrayList<String>(typeMap.values());

        typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeAdapter.notifyDataSetChanged();
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = typeSpinner.getSelectedItem().toString();
                typeKey = (String) getKeyFromValue(typeMap, selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // gender spinner....
        /*genderSpinner = (Spinner) examEvalView.findViewById(R.id.genderSpinner);
        genderMap.put("", "-- select --");
        genderMap.put("Male", "Male");
        genderMap.put("Female", "Female");
        genderList = new ArrayList<String>(genderMap.values());

        genderAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderAdapter.notifyDataSetChanged();
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderSection = genderSpinner.getSelectedItem().toString();
                genderKey = (String) getKeyFromValue(genderMap, genderSection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/





        //
        select = examEvalView.findViewById(R.id.search);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDate="2017-12-02";
                listMarks.clear();
                if (typeKey != null) {
                    new EmpAttDailyBackTask(getActivity(), EmpAttendanceByDayFragment.this).execute(typeKey,editAdmDate.getText().toString(),schoolId);
                } else {
                    Toast.makeText(getActivity(), "Please select STUDENT", Toast.LENGTH_LONG).show();
                }
            }
        });
        //
        recycler_view = (RecyclerView) examEvalView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Attebdance Reports");


        return examEvalView;
    }




    @Override
    public void allAttendanceByday(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            String empName = "";
            String gender="";
            String empFirstName="";
            String empLastName ="";


            String employeeNameL;
            String genderL;


            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("attandance report by day" + jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("employeeAttendence");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("all attendance:" + jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                    //   stdName = markObject.getString("first_name") + markObject.getString("last_name");
                    //studentImage
                  //  if (!jsonObject.isNull("employee_doc")) {
                    JSONArray nameArray = markObject.getJSONArray("employee_doc");
                        System.out.println("employee json "+nameArray);
                        int p = 0;



                 /*   if (!jsonObject.isNull("studentImage")) {
                        JSONArray imageArray = jsonObject.getJSONArray("studentImage");*/
                        //int p = 0;
                     /*   while (p < nameArray.length()) {
                            JSONObject parentObject = nameArray.getJSONObject(p);
                            empFirstName=parentObject.getString("first_name");
                            empLastName=parentObject.getString("last_name");
                            gender=parentObject.getString("gender");

                            p++;
                        }*/







/*
                        for(p=0;p<nameArray.length();p++)
                        {
                            empFirstName=nameArray.getJSONObject(p).getString("first_name");
                            empLastName=nameArray.getJSONObject(p).getString("first_name");

                            gender=nameArray.getJSONObject(p).getString("gender");

                            System.out.println("employee name "+empName);

                        }*/
                       while (p < nameArray.length()) {
                            JSONObject parentObject = nameArray.getJSONObject(p);
                            empName = parentObject.getString("first_name") + parentObject.getString("last_name");
                            gender = parentObject.getString("gender");
                            System.out.println("employee name "+empName);

                            p++;
                        }
                  //  }
                    employeeNameL=empName;
                    genderL=gender;
                    System.out.println("employee name "+employeeNameL);

                   // genderL=gender;

                    EmpAttModel empAttModel = new EmpAttModel(employeeNameL, markObject.getString("employee_id"), markObject.getString("category"), genderL, markObject.getString("status"));
                    listMarks.add(empAttModel);
                    count++;


                    //Count....
                    String taskCount = "Attendance Records (" + String.valueOf(listMarks.size()) + ")";
                     textStdCount.setText(taskCount);
                    //
                    recycler_view.setVisibility(View.VISIBLE);

                    empAttDailyAdapter = new EmpAttDailyAdapter(getActivity(), listMarks);
                    recycler_view.setAdapter(empAttDailyAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recycler_view.setLayoutManager(linearLayoutManager);
                    empAttDailyAdapter.notifyDataSetChanged();
                    //
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);
                }
            }else {
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }

    }


    public class DatePickerFragement2 implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editAdmDate.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(""));
        }
    }

}
    /*
    "{
  ""SCH-9273-EXM_SCH-3-SCH-9273-CL-2-SEC-1-SUB-1-EXM-5-SCH-9273-CL-2-STD-5"": [
    {
      ""_id"": ""592bf135501c1018082fd58e"",
      ""paper_result_id"": ""SCH-9273-EXM_SCH-3-SCH-9273-CL-2-SEC-1-SUB-1-EXM-5-SCH-9273-CL-2-STD-5-EVAL-3"",
      ""exam_paper_id"": ""SCH-9273-EXM_SCH-3-SCH-9273-CL-2-SEC-1-SUB-1-EXM-5"",
      ""student_id"": ""SCH-9273-CL-2-STD-5"",
      ""marks"": ""80"",
      ""percentage"": ""80"",
      ""conduct"": ""conduct"",
      ""comment"": ""test comment"",
      ""date"": ""2017-05-29T10:00:21.645Z"",
      ""status"": 1
    }
  ]
}"
     */

