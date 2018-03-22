package com.example.medianet.proschool.suresh.employeeattendancetabs.monthlyadapterandbacktask;

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

import com.example.medianet.proschool.AllEmpBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.StudentAttandenceModel;

import com.example.medianet.proschool.StudentDetailsAdapter;
import com.example.medianet.proschool.StudentsDetails;
import com.example.medianet.proschool.suresh.activity.AllEmployeeDetails;
import com.example.medianet.proschool.suresh.attendancemodule.daywisereport.AllAttendanceReportByDayAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.daywisereport.AllAttendanceReportByDayBackTask;
import com.example.medianet.proschool.suresh.employeeattendancetabs.EmpAttModel;
import com.example.medianet.proschool.suresh.employeeattendancetabs.dailyadapterandbacktask.EmpAttDailyBackTask;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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

public class EmpAttendanceByMonthFragment extends Fragment implements EmpAttMonthlyBackTask.AllEmpAttendanceByMonth,AllEmpBackTask.AllEmployees
{

    View examEvalView;
    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout exam_layout;
    TextView textExamCount;
    // Class Spinner....
    String classKey;



    // Section Spinner....
    String sectionKey, selectedSection;
    Spinner sectionSpinner;
    Map<String, String> sectionMap = new LinkedHashMap<String, String>();
    ArrayList<String> sectionList;
    ArrayAdapter<String> sectionAdapter;



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
    EmpAttMonthlyAdapter empAttMonthlyAdapter;
    Context mContext;
    String schoolId;



    //
    String conductkey,feeTypeKey, selectedConduct,selectedConduct1;
    Spinner conductSpinner,feeTypeSpinner;
    Map<String, String> conductMap = new LinkedHashMap<String, String>();
    Map<String, String> conductMap1 = new LinkedHashMap<String, String>();

    ArrayList<String> conductList,conductList1;
    ArrayAdapter<String> conductAdapter,conductAdapter1;

    public EmpAttendanceByMonthFragment() {

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
        examEvalView = inflater.inflate(R.layout.emp_all_attendance_month_layout, container, false);
        mContext=getActivity();
        sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examEvalView.findViewById(R.id.std_layout);
        textExamCount = (TextView) examEvalView.findViewById(R.id.textStdCount);


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new AllEmpBackTask(getActivity(), EmpAttendanceByMonthFragment.this).execute(schoolId);


        sectionSpinner = (Spinner) examEvalView.findViewById(R.id.attEmpName);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                         selectedSection = sectionSpinner.getSelectedItem().toString();
                                                         sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                                                         // Getting subjects, Students....
             /*   if (sectionKey != null && !sectionKey.isEmpty()) {
                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                    new SectionBackGroundTask(getActivity(), AttendanceReportByDayFragment.this).execute(sectionKey);
                    //  new AllExamPaperBackTask(getActivity(), ExamEvaluationFragment.this).execute(examScheduleKey, sectionKey);

                } else {
                    Toast.makeText(getActivity(), "Please Select Section.", Toast.LENGTH_LONG).show();
                }*/
                                                     }


                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> adapterView) {

                                                     }
                                                 });




            // Conduct Spinner....
        conductSpinner = (Spinner) examEvalView.findViewById(R.id.attMonth);
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
        conductMap.put("12", "December");



        conductList = new ArrayList<>(conductMap.values());
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
        });



        //
        select = examEvalView.findViewById(R.id.attSearch);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDate="2017-12-02";
                listMarks.clear();
                if (sectionKey != null) {
                    new EmpAttMonthlyBackTask(getActivity(), EmpAttendanceByMonthFragment.this).execute(conductkey,sectionKey,schoolId);
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
    public void allEmpAttendanceByMonth(String result) throws JSONException {


        if (result != null && !result.isEmpty()) {
            String empName = "";
            String gender="";
            String date="";
            String empFirstName="";
            String empLastName ="";


            String employeeNameL;
            String genderL;


            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("attandance report by day" + jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("donutchart");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("all attendance:" + jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                    //   stdName = markObject.getString("first_name") + markObject.getString("last_name");
                    //studentImage
                    //  if (!jsonObject.isNull("employee_doc")) {

                     /*   while (p < nameArray.length()) {
                            JSONObject parentObject = nameArray.getJSONObject(p);
                            empName = parentObject.getString("first_name") + parentObject.getString("last_name");
                            gender = parentObject.getString("gender");
                            System.out.println("employee name "+empName);

                            p++;
                        }*/
                    //  }
                    //empName = markObject.getString("first_name") + markObject.getString("last_name");
                    date=markObject.getString("date");

                   String firstTen=date.substring(0,10);
                 //   convertTime(date);

                    gender = markObject.getString("gender") ;

                    // genderL=gender;

                    EmpAttModel empAttModel = new EmpAttModel(firstTen,firstTen, markObject.getString("employee_type"), gender, markObject.getString("status"));
                    listMarks.add(empAttModel);
                    count++;


                    //Count....
                    String taskCount = "Attendance Records (" + String.valueOf(listMarks.size()) + ")";
                   textExamCount.setText(taskCount);
                    //
                    recycler_view.setVisibility(View.VISIBLE);

                    empAttMonthlyAdapter = new EmpAttMonthlyAdapter(getActivity(), listMarks);
                    recycler_view.setAdapter(empAttMonthlyAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recycler_view.setLayoutManager(linearLayoutManager);
                    empAttMonthlyAdapter.notifyDataSetChanged();
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

    private String convertTime(String time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm aa MM-dd");
        java.util.Date date = null;

        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String convertedDate = format1.format(date);

        return convertedDate;
    }


    @Override
    public void allEmployees(String result) throws JSONException {

        if (result != null && !result.isEmpty()) {
            sectionMap.clear();
            sectionMap.put("", "-- select --");
            JSONObject secObject = new JSONObject(result);
            JSONArray secArray = secObject.getJSONArray("employee");
            int count = 0;
            while (count < secArray.length()) {
                JSONObject jsonObject = secArray.getJSONObject(count);
                sectionMap.put(jsonObject.getString("employee_id"), jsonObject.getString("first_name"));
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

