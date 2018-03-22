package com.example.medianet.proschool.suresh.studentprofile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.example.medianet.proschool.StudentAttandenceModel;
import com.example.medianet.proschool.suresh.activity.AddAssigmentFragment;
import com.example.medianet.proschool.suresh.attendancemodule.AllAttendanceReportsMonthBackTask;
import com.example.medianet.proschool.suresh.attendancemodule.AttendanceReportsByMonthAdapter;
import com.example.medianet.proschool.suresh.checkboxs.DemoCheckBoxFragment;
import com.example.medianet.proschool.suresh.feemodule.AllFeeTypeBackTask;
import com.example.medianet.proschool.suresh.feemodule.FeeTypeFragmentMain;
import com.example.medianet.proschool.suresh.feemodule.feecollection.AddFeeCollectionBackTask;
import com.example.medianet.proschool.suresh.feemodule.feecollection.AllFeeCollectionBackTask;
import com.example.medianet.proschool.suresh.feemodule.feecollection.FeeCollectionAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 17-06-2017.
 */

public class StudentProfileGraphsFragment extends Fragment implements

        AllAttendanceReportsMonthBackTask.AllFeeCollection, OnChartValueSelectedListener {

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
    ArrayList<StudentAttandenceModel> listMarks = new ArrayList<StudentAttandenceModel>();
    AttendanceReportsByMonthAdapter feeCollectionAdapter;
    Context mContext;
    String schoolId;
    String studentId;
    private PieChart mAdminChart;


    public StudentProfileGraphsFragment() {

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
        examEvalView = inflater.inflate(R.layout.student_profile_garphs_show_layout_three, container, false);
        mContext = getActivity();


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        SharedPreferences sharedPreferences1 = mContext.getSharedPreferences("studentInfo", MODE_PRIVATE);
        studentId = sharedPreferences1.getString(Constants.studentId, "");
        Log.e("student id from adapter", studentId);




      /*  sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);*/
     /*   exam_layout = (LinearLayout) examEvalView.findViewById(R.id.exam_layout);
        textExamCount = (TextView) examEvalView.findViewById(R.id.textExamCount);*/

        // Conduct Spinner....
        conductSpinner = (Spinner) examEvalView.findViewById(R.id.conductSpinner);
        //  conductMap.put("", "-- select --");
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


        mAdminChart = examEvalView.findViewById(R.id.administration_attendance_piechart);
        mAdminChart.setBackgroundColor(Color.WHITE);

        mAdminChart.setUsePercentValues(true);
        mAdminChart.getDescription().setEnabled(false);
        mAdminChart.setOnChartValueSelectedListener(this);
        mAdminChart.setTouchEnabled(true);

        //mAdminChart.setCenterTextTypeface(mTfLight);
        //mAdminChart.setCenterText(generateCenterSpannableText());
        mAdminChart.setDrawHoleEnabled(true);
        mAdminChart.setHoleColor(Color.WHITE);
        mAdminChart.setExtraOffsets(5, 5, 5, 5);
        mAdminChart.setTransparentCircleColor(Color.WHITE);
        mAdminChart.setTransparentCircleAlpha(110);
        //mAdminChart.setCenterText("Administrative Staff Attendance");
        mAdminChart.setHoleRadius(50f);
        mAdminChart.setTransparentCircleRadius(55f);
        mAdminChart.setDrawEntryLabels(false);
        mAdminChart.setDrawCenterText(true);
        //mAdminChart.setCenterTextOffset(5,-5);
        mAdminChart.setRotationEnabled(false);
        mAdminChart.setHighlightPerTapEnabled(true);

        //mAdminChart.setMaxAngle(180f); // HALF CHART
        mAdminChart.setRotationAngle(180f);
        mAdminChart.setCenterTextSize(10);
        //mAdminChart.setCenterTextOffset(0, -20);

        // setData(3, 100);

        mAdminChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mAdminChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setTextSize(12);
        l.setYEntrySpace(0f);
        l.setYOffset(5f);

        // entry label styling
        //mAdminChart.setEntryLabelColor(Color.BLACK);
        //mAdminChart.setEntryLabelTypeface(mTfRegular);
        mAdminChart.setEntryLabelTextSize(12f);


        //
        select = (FloatingActionButton) examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMarks.clear();
                if (studentId != null) {
                    new AllAttendanceReportsMonthBackTask(getActivity(), StudentProfileGraphsFragment.this).execute(conductkey, studentId);
                } else {
                    Toast.makeText(getActivity(), "Please select STUDENT", Toast.LENGTH_LONG).show();
                }
            }
        });
        //
        //   recycler_view = (RecyclerView) examEvalView.findViewById(R.id.recycler_view);
        //   recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Attebdance Reports");


        return examEvalView;
    }


    @Override
    public void allFeeCollection(String result) throws JSONException {


        if (result != null && !result.isEmpty()) {


            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.clear();
            JSONObject jsonObject = new JSONObject(result);


            JSONArray jsonArray = jsonObject.getJSONArray("donutchart");

            System.out.println("all attendance:" + jsonArray);

            int taskCount = entries.size();
            System.out.println("total entries value:" + taskCount);

            int countPresent1 = 0;
            int countAbsent1 = 0;
            int countOnLeave1 = 0;
            int countTotalJsonArray;


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject element = jsonArray.getJSONObject(i);
                String location = element.getString("status");
                if (location.equals("Present")) {
                    countPresent1++;
                } else if (location.equals("Absent")) {
                    countAbsent1++;
                } else if (location.equals("On Leave")) {
                    countOnLeave1++;
                }
            }
            countTotalJsonArray = jsonArray.length();


            System.out.println("total attendance json Objects:" + countTotalJsonArray);


            System.out.println("Present=" + countPresent1 + "Absent=" + countAbsent1 + "On Leave=" + countOnLeave1);


            Float totalPresents, totalAbsents, totalLeaves;
            Float totalAdminisations = Float.parseFloat(String.valueOf(countTotalJsonArray));
            Float presentCount = Float.parseFloat(String.valueOf(countPresent1));
            Float absentCount = Float.parseFloat(String.valueOf(countAbsent1));
            Float leaveCount = Float.parseFloat(String.valueOf(countOnLeave1));
            totalPresents = presentCount * 100 / totalAdminisations;
            totalAbsents = absentCount * 100 / totalAdminisations;
            totalLeaves = leaveCount * 100 / totalAdminisations;


            System.out.println("attandance report all  pie chart values" + totalPresents + " " + totalAbsents + " " + totalLeaves);


            entries.add(new PieEntry(totalPresents, "Present" + "-" + countPresent1));
            entries.add(new PieEntry(totalAbsents, "Absent" + "-" + countAbsent1));
            entries.add(new PieEntry(totalLeaves, "Leave" + "-" + countOnLeave1));

            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.PASTEL_COLORS);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            data.setValueTextColor(Color.BLACK);
            mAdminChart.setRotationEnabled(true);
            mAdminChart.setVisibility(View.VISIBLE);
            //data.setValueTypeface(mTfLight);
            mAdminChart.setData(data);
            mAdminChart.invalidate();

        }


     /*   if (result != null && !result.isEmpty()) {
            String stdName="";
            String date="";
            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("attandance report"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("donutchart");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("all attendance:"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                    stdName = markObject.getString("first_name") + markObject.getString("last_name");
                    date=markObject.getString("date");
                    String firstTen=date.substring(0,10);

                    StudentAttandenceModel feeCollectionmodel=new StudentAttandenceModel(firstTen,markObject.getString("admission_no"),markObject.getString("roll_no"),markObject.getString("class_name"),markObject.getString("gender"),markObject.getString("date"),markObject.getString("status"));
                    listMarks.add(feeCollectionmodel);
                    count++;
                }
                //Count....
                String taskCount = "Attendance Records (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);

                feeCollectionAdapter = new AttendanceReportsByMonthAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(feeCollectionAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                *//*LinearLayoutManager  linearLayoutManager=new LinearLayoutManager(getActivity())
                {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };*//*
                recycler_view.setLayoutManager(linearLayoutManager);
                feeCollectionAdapter.notifyDataSetChanged();
                //
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(230);
            } else {
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }*/

    }

    protected void displayReceivedData(String message) {
        Log.e("check Data Came", message);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

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











