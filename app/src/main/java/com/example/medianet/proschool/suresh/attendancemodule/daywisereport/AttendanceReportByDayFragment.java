package com.example.medianet.proschool.suresh.attendancemodule.daywisereport;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.StudentAdmissionFragment;
import com.example.medianet.proschool.StudentAttandenceModel;

import com.example.medianet.proschool.checkattendance.AttSubModel;
import com.example.medianet.proschool.checkattendance.StudentCustomAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.daywisereport.AllAttendanceReportByDayAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.daywisereport.AllAttendanceReportByDayBackTask;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileTabsFragment;
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

public class AttendanceReportByDayFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,

        AllAttendanceReportByDayBackTask.AllAttendanceByday,AllAttendanceReportByDayAdapter.ItemClickStudentProfileListener  {

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
    EditText editDate, editAdmDate;
    //
    RecyclerView recycler_view;
    ArrayList<StudentAttandenceModel> listMarks = new ArrayList<StudentAttandenceModel>();
    AllAttendanceReportByDayAdapter feeCollectionAdapter;
    Context mContext;
    String schoolId;
    SharedPreferences sharedPreferences;

    public AttendanceReportByDayFragment() {

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
        examEvalView = inflater.inflate(R.layout.all_attendance_day_layout, container, false);
     mContext=getActivity();
        sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examEvalView.findViewById(R.id.exam_layout);
        textExamCount = (TextView) examEvalView.findViewById(R.id.textExamCount);


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new ClassBackGroundTask(getActivity(), AttendanceReportByDayFragment.this).execute(schoolId);

        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();*/

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


        // class spinner....
        classSpinner = (Spinner) examEvalView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), AttendanceReportByDayFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) examEvalView.findViewById(R.id.sectionSpinner);
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });









        //
        select =  (FloatingActionButton)examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDate="2017-12-02";
                listMarks.clear();
                if (classKey != null) {
                    new AllAttendanceReportByDayBackTask(getActivity(), AttendanceReportByDayFragment.this).execute(editAdmDate.getText().toString(), classKey, sectionKey);
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
        }
    }


    @Override
    public void allAttendanceByday(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            String stdName;
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

                    stdName = markObject.getString("first_name") + markObject.getString("last_name");


                 /*   String a;
                    String b;
                    String c;
                    int total;
                    String totalValue;
                    a = markObject.getString("fee_amount");
                    b = markObject.getString("fine");
                    c = markObject.getString("discount");
                    int amount=Integer.parseInt(a);
                    int fine=Integer.parseInt(b);
                    int discount=Integer.parseInt(c);
                    total=(amount+fine)-discount;
                    totalValue= String.valueOf(total);
                    System.out.println("total"+totalValue);*/
                    //paper_result_id
                  /*  FeeCollection evalMarks = new FeeCollection(markObject.getString("_id"), markObject.getString("exam_paper_id"),
                            selectedPaper, selectedStudent, markObject.getString("marks"),
                            markObject.getString("percentage"), markObject.getString("conduct"), markObject.getString("paper_name"),
                            markObject.getString("first_name"), markObject.getString("examschedule_name"), selectedExamSchedule, selectedSubject);*/
                    StudentAttandenceModel feeCollectionmodel = new StudentAttandenceModel(stdName, markObject.getString("admission_no"), markObject.getString("roll_no"), markObject.getString("class_name"), markObject.getString("gender"), markObject.getString("student_id"), markObject.getString("status"));
                    listMarks.add(feeCollectionmodel);
                    count++;
                }
                //Count....
                String taskCount = "Attendance Records (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);

                feeCollectionAdapter = new AllAttendanceReportByDayAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(feeCollectionAdapter);
                feeCollectionAdapter.setClickListener(this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                feeCollectionAdapter.notifyDataSetChanged();
                recycler_view.destroyDrawingCache();
                //
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

    @Override
    public void onClickProfile(View view, int position) {

        final StudentAttandenceModel city = listMarks.get(position);
        String studentId=city.getStdDob();

        sharedPreferences = mContext.getSharedPreferences("studentInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.studentId, studentId);
        editor.commit();
        startActivity(new Intent(mContext, StudentProfileTabsFragment.class));
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

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }*/

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

