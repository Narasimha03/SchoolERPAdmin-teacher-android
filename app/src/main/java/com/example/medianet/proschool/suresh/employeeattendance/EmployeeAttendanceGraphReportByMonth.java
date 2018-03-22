package com.example.medianet.proschool.suresh.employeeattendance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.StudentAttandenceModel;
import com.example.medianet.proschool.suresh.activity.AddAssigmentFragment;
import com.example.medianet.proschool.suresh.attendancemodule.AllAttendanceReportsMonthBackTask;
import com.example.medianet.proschool.suresh.attendancemodule.AttendanceReportsByMonthAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.AllStudentMonthGraphAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.StudentAttendanceGraphBackTask;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.StudentGraphModel;
import com.example.medianet.proschool.suresh.checkboxs.DemoCheckBoxFragment;
import com.example.medianet.proschool.suresh.feemodule.AllFeeTypeBackTask;
import com.example.medianet.proschool.suresh.feemodule.FeeTypeFragmentMain;
import com.example.medianet.proschool.suresh.feemodule.feecollection.AddFeeCollectionBackTask;
import com.example.medianet.proschool.suresh.feemodule.feecollection.AllFeeCollectionBackTask;
import com.example.medianet.proschool.suresh.feemodule.feecollection.FeeCollectionAdapter;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileShowFragment;
import com.example.medianet.proschool.suresh.studentprofile.backtasks.StudentProfileBackTask;
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

public class EmployeeAttendanceGraphReportByMonth extends Fragment implements

        EmployeeAttendenceGraphBacktask.AllStudentGraphs {

    View examEvalView;
    String imageurl = null;
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
    String conductkey,feeTypeKey, selectedConduct,selectedConduct1;
    Spinner conductSpinner,feeTypeSpinner;
    Map<String, String> conductMap = new LinkedHashMap<String, String>();
    Map<String, String> conductMap1 = new LinkedHashMap<String, String>();

    ArrayList<String> conductList,conductList1;
    ArrayAdapter<String> conductAdapter,conductAdapter1;
    //
    Button addMarks;

    FloatingActionButton select;
    //date
    EditText editDate;
    //
    RecyclerView recycler_view;
    ArrayList<StudentGraphModel> listMarks = new ArrayList<StudentGraphModel>();
    AllStudentMonthGraphAdapter allStudentMonthGraphAdapter;
    Context mContext;
    String schoolId;

    public EmployeeAttendanceGraphReportByMonth() {

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
        examEvalView = inflater.inflate(R.layout.all_attendance_month_employee_layout, container, false);
        mContext=getActivity();



        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        //new ClassBackGroundTask(getActivity(), StudentAttendanceGraphReportByMonth.this).execute(schoolId);


        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();*/


        sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examEvalView.findViewById(R.id.exam_layout);
        textExamCount = (TextView) examEvalView.findViewById(R.id.textExamCount);

        // Conduct Spinner....
        conductSpinner = (Spinner) examEvalView.findViewById(R.id.conductSpinner);
       // conductMap.put("", "-- select --");
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
        select = (FloatingActionButton)examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMarks.clear();
                if (conductkey != null) {
                    new EmployeeAttendenceGraphBacktask (getActivity(), EmployeeAttendanceGraphReportByMonth.this).execute(conductkey,schoolId);
                } else {
                    Toast.makeText(getActivity(), "Please select Month", Toast.LENGTH_LONG).show();
                }
            }
        });
        //
        recycler_view = (RecyclerView) examEvalView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Attebdance Reports");


        return examEvalView;
    }










    @Override
    public void allStudentGraphs(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            String studentId = "",studentName = "",totalCount = "",totalPresent = "",totalAbsent = "",totalLeave = "",sPresent = "",sAbsent = "",sOnleave = "",sPresentPercent = "",sAbsentPercent = "",sOnLeavePercent = "", imageDisplay = "", employeeId = "";
            String imageName="";
            String imageurlData="";

            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("employee report"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("employeeMonthlyAttendence");


            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("all attendance:"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                 /*   stdName = markObject.getString("first_name") + markObject.getString("last_name");
                    date=markObject.getString("date");
                    String firstTen=date.substring(0,10);*/


                    totalCount=markObject.getString("totalCount");
                    totalPresent=markObject.getString("totalPresent");
                    totalAbsent=markObject.getString("totaAbsent");
                    totalLeave=markObject.getString("totalOnLeave");

                    Log.e("student Count att",totalCount+" "+totalPresent);


                    if (!markObject.isNull("employeeAttendanceReport")) {
                        JSONArray parentArray = markObject.getJSONArray("employeeAttendanceReport");
                        int p = 0;
                        while (p < parentArray.length()) {
                            JSONObject parentObject = parentArray.getJSONObject(p);
                            studentId=parentObject.getString("employeeId");
                            imageName = parentObject.getString("employeeImage");
                            imageurlData = Constants.singleImage + imageName;



                            // new StudentProfileBackTask(getActivity(), StudentAttendanceGraphReportByMonth.this).execute(studentId);

                            studentName=parentObject.getString("Name");
                            Log.e("StudentAttendanceReport",studentId+" "+studentName);

                            if (!parentObject.isNull("attendance")) {
                                JSONObject attendanceObject = parentObject.getJSONObject("attendance");
                                sPresent = attendanceObject.getString("present");
                                sAbsent = attendanceObject.getString("absent");
                                sOnleave = attendanceObject.getString("onLeave");
                                sPresentPercent = attendanceObject.getString("presentPercent");
                                sAbsentPercent = attendanceObject.getString("absentPercent");
                                sOnLeavePercent = attendanceObject.getString("onLeavePercent");
                            }



                            Log.e("student graph",studentName+" "+sPresent);
                            Log.e("Image url",studentName+" "+imageurl);


                            StudentGraphModel studentGraphModel=new StudentGraphModel(studentId, employeeId,studentName,totalCount,totalPresent,totalAbsent,totalLeave,sPresent,sAbsent,sOnleave,sPresentPercent,sAbsentPercent,sOnLeavePercent, imageurlData);
                            listMarks.add(studentGraphModel);
                            p++;
                        }


                    }






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

                    count++;
                }
                //Count....
                String taskCount = "Attendance Records (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);

                allStudentMonthGraphAdapter = new AllStudentMonthGraphAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(allStudentMonthGraphAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                allStudentMonthGraphAdapter.notifyDataSetChanged();
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

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();


        }
    }*/
}
