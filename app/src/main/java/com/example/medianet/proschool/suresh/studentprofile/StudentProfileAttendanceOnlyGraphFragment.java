package com.example.medianet.proschool.suresh.studentprofile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.StudentAttandenceModel;
import com.example.medianet.proschool.suresh.AllDayStudentMonthAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.AllAttendanceReportsMonthBackTask;
import com.example.medianet.proschool.suresh.attendancemodule.AttendanceReportsByMonthAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.AllAttendanceReportsMonthBackTaskForGraph;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.AllStudentMonthGraphAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.AllStudentMonthGraphAdapterForSingleStudent;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.StudentAttendanceGraphReportByMonth;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.StudentGraphModel;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.StudentGraphModelOnlyForSingleStudent;
import com.example.medianet.proschool.suresh.studentprofile.backtasks.StudentProfileBackTask;
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
 * Created by JANI on 17-06-2017.
 */

public class StudentProfileAttendanceOnlyGraphFragment extends Fragment implements

        AllAttendanceReportsMonthBackTaskForGraph.AllFeeCollection {

    View examEvalView;
    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout exam_layout;
    TextView textExamCount;
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
    // Exam Schedule Spinner...
    String examScheduleKey, selectedExamSchedule;
    Spinner examScheduleSpinner;
    Map<String, String> examScheduleMap = new LinkedHashMap<String, String>();
    ArrayList<String> examScheduleList;
    ArrayAdapter<String> examScheduleAdapter;
    // Subject Spinner...
    String subjectkey, selectedSubject;
    Spinner subjectSpinner;
    Map<String, String> subjectMap = new LinkedHashMap<String, String>();
    ArrayList<String> subjectList;
    ArrayAdapter<String> subjectAdapter;
    // Paper Spinner.... studentSpinner
    String paperkey, selectedPaper;
    Spinner paperSpinner;
    Map<String, String> paperMap = new LinkedHashMap<String, String>();
    ArrayList<String> paperList;
    ArrayAdapter<String> paperAdapter;
    // Student Spinner...
    String studentkey, selectedStudent;
    Spinner studentSpinner;
    Map<String, String> studentMap = new LinkedHashMap<String, String>();
    ArrayList<String> studentList;
    ArrayAdapter<String> studentAdapter;
    //
    EditText editMarks, editPercent;
    //
    String conductkey, feeTypeKey, selectedConduct, selectedConduct1;
    Spinner conductSpinner, feeTypeSpinner;
    Map<String, String> conductMap = new LinkedHashMap<String, String>();
    Map<String, String> conductMap1 = new LinkedHashMap<String, String>();

    ArrayList<String> conductList, conductList1;
    ArrayAdapter<String> conductAdapter, conductAdapter1;
    //
    Button addMarks;

    FloatingActionButton select;
    //date
    EditText editDate;
    //
    RecyclerView recycler_view;
    ArrayList<StudentGraphModel> listMarks = new ArrayList<StudentGraphModel>();
    AllDayStudentMonthAdapter feeCollectionAdapter;
    Context mContext;
    String schoolId;
    String studentId;
    String employeeId;


    public StudentProfileAttendanceOnlyGraphFragment() {

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
        examEvalView = inflater.inflate(R.layout.student_profile_attendance_month_graph_layout, container, false);
        mContext = getActivity();


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        SharedPreferences sharedPreferences1 = mContext.getSharedPreferences("studentInfo", MODE_PRIVATE);
        studentId = sharedPreferences1.getString(Constants.studentId, "");
        Log.e("student id from adapter", studentId);
        SharedPreferences sharedPreferences2 = mContext.getSharedPreferences("employeeInfo", MODE_PRIVATE);
        employeeId = sharedPreferences2.getString(Constants.employeeId, "");


        new AllAttendanceReportsMonthBackTaskForGraph(getActivity(), StudentProfileAttendanceOnlyGraphFragment.this).execute(studentId);

        /*sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examEvalView.findViewById(R.id.exam_layout);
        textExamCount = (TextView) examEvalView.findViewById(R.id.textExamCount);*/

        // Conduct Spinner....
        /*conductSpinner = (Spinner) examEvalView.findViewById(R.id.conductSpinner);
        conductMap.put("", "-- select --");
        conductMap.put("1", "January");
        conductMap.put("2", "February");
        conductMap.put("3", "March");
        conductMap.put("4", "April");
        conductMap.put("5", "May");
        conductMap.put("6", "June");
        conductMap.put("7", "July");
        conductMap.put("8", "August");
        conductMap.put("9", "September");
        conductMap.put("10", "October");
        conductMap.put("11", "November");
        conductMap.put("12", "December");*/



        /*conductList = new ArrayList<>(conductMap.values());
        conductAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, conductList);
        conductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conductSpinner.setAdapter(conductAdapter);
        conductAdapter.notifyDataSetChanged();

        conductSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedConduct = conductSpinner.getSelectedItem().toString();
                conductkey = (String) getKeyFromValue(conductMap, selectedConduct);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        //
        /*select = (FloatingActionButton)examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMarks.clear();
                if (studentId != null) {
                    new AllAttendanceReportsMonthBackTask(getActivity(), StudentProfileAttendanceOnlyGraphFragment.this).execute(conductkey,studentId);
                } else {
                    Toast.makeText(getActivity(), "Please select STUDENT", Toast.LENGTH_LONG).show();
                }
            }
        });*/
        //
        recycler_view = (RecyclerView) examEvalView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Attebdance Reports");


        return examEvalView;
    }


    @Override
    public void allFeeCollection(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            String studentId = "", studentName = "", totalCount = "", totalPresent = "", totalAbsent = "", totalLeave = "", sPresent = "", sAbsent = "", sOnleave = "", sPresentPercent = "", sAbsentPercent = "", sOnLeavePercent = "", imageDisplay = "", employeeId ="";
            String date = "";
            listMarks.clear();
            String sendPresent = null;
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("section report" + jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("studentAttendence");
            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("all attendance:" + jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                    studentName = markObject.getString("monthName");
                    totalCount=markObject.getString("count");
                    System.out.println("all student:" + studentName);

                    if (!markObject.isNull("attendance")) {
                        JSONObject attendanceObject = markObject.getJSONObject("attendance");
                        sPresent = attendanceObject.getString("present");
                     /*   if (sPresent != null && !sPresent.isEmpty()&&!sPresent.equals(0)) {
                            Log.e("present values",sPresent);
                            sendPresent=sPresent;
                            Log.e("sendPresent",sPresent);


                        }*/

                            sAbsent = attendanceObject.getString("absent");
                            sOnleave = attendanceObject.getString("onLeave");
                            sPresentPercent = attendanceObject.getString("presentPercent");
                            sAbsentPercent = attendanceObject.getString("absentPercent");
                            sOnLeavePercent = attendanceObject.getString("onLeavePercent");

                            System.out.println("all presesnt:" + sPresent);

                            System.out.println("all:" + sOnleave);

                        StudentGraphModel studentGraphModel = new StudentGraphModel(studentId, employeeId, studentName, totalCount, totalPresent, totalAbsent, totalLeave, sPresent, sAbsent, sOnleave, sPresentPercent, sAbsentPercent, sOnLeavePercent, imageDisplay);
                            listMarks.add(studentGraphModel);

                    }
                    count++;


                }
                //Count....
                /*String taskCount = "Attendance Records (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);*/
                //
                recycler_view.setVisibility(View.VISIBLE);

                feeCollectionAdapter = new AllDayStudentMonthAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(feeCollectionAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                /*LinearLayoutManager  linearLayoutManager=new LinearLayoutManager(getActivity())
                {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };*/
                recycler_view.setLayoutManager(linearLayoutManager);
                feeCollectionAdapter.notifyDataSetChanged();
                //
                /*sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(120);*/
            } else {
                /*sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);*/
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void displayReceivedData(String message) {
        Log.e("check Data Came", message);
    }


    public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
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
}











