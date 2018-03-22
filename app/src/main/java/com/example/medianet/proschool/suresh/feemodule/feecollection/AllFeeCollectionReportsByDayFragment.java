package com.example.medianet.proschool.suresh.feemodule.feecollection;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.attendancemodule.daywisereport.AllAttendanceReportByDayBackTask;
import com.example.medianet.proschool.suresh.attendancemodule.daywisereport.AttendanceReportByDayFragment;
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

/**
 * Created by JANI on 17-06-2017.
 */

public class AllFeeCollectionReportsByDayFragment extends Fragment implements AllFeeCollectionReportsByDayBackTaski.AllFeeCollectionReportsByDay {

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
    //date
    EditText editDate;

    FloatingActionButton fab;
    LinearLayout fabLayout1;
    View fabBGLayout;
    FrameLayout frameLayout;
    boolean isFABOpen = false;
    //
    RecyclerView recycler_view;
    ArrayList<FeeCollectionReports> listMarks = new ArrayList<FeeCollectionReports>();
    AllFeeCollectionReportsByDayAdapter feeCollectionReportsByDayAdapter;
    Context mContext;
    String schoolId;
    String role;
    TextView totalAmount;

    EditText feeDateByDate;
    FloatingActionButton select;


    public AllFeeCollectionReportsByDayFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  new AllExamSchBackTask(getActivity(), AllFeeCollectionFragment.this).execute(Constants.schoolId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        examEvalView = inflater.inflate(R.layout.fee_reports_by_day_layout, container, false);
        mContext = getActivity();


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        //new ClassBackGroundTask(getActivity(), AllFeeCollectionReportsFragment.this).execute(schoolId);
        //new AllFeeTypeBackTask(getActivity(), AllFeeCollectionFragment.this).execute(schoolId);

        sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examEvalView.findViewById(R.id.std_layout);
        textExamCount = (TextView) examEvalView.findViewById(R.id.textStdCount);


        totalAmount = examEvalView.findViewById(R.id.totalAmount);
        feeDateByDate = (EditText) examEvalView.findViewById(R.id.feeDateByDate);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        feeDateByDate.setText(date);
        feeDateByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new AllFeeCollectionReportsByDayFragment.DatePickerFragement2(), years, month, day);
                datePickerDialog.show();
            }
        });

        select = (FloatingActionButton) examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDate = "2017-12-02";
                listMarks.clear();
                if (feeDateByDate.getText().toString() != null) {
                    new AllFeeCollectionReportsByDayBackTaski(getActivity(), AllFeeCollectionReportsByDayFragment.this).execute(feeDateByDate.getText().toString(), schoolId);
                } else {
                    Toast.makeText(getActivity(), "Please select Date", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*fabLayout1= (LinearLayout) examEvalView.findViewById(R.id.fabLayout1);

        fab = (FloatingActionButton) examEvalView.findViewById(R.id.fab);

        frameLayout=(FrameLayout)examEvalView.findViewById(R.id.frameLayout);
        fabBGLayout=examEvalView.findViewById(R.id.fabBGLayout);*/
        // class spinner....
        /*classSpinner = (Spinner) examEvalView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), AllFeeCollectionReportsFragment.this).execute(classKey);
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
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                    new AllStudentsBackTask(getActivity(), AllFeeCollectionReportsFragment.this).execute(sectionKey);
                    //  new AllExamPaperBackTask(getActivity(), ExamEvaluationFragment.this).execute(examScheduleKey, sectionKey);

                } else {
                    Toast.makeText(getActivity(), "Please Select Section.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Student Spinner...
        studentSpinner = (Spinner) examEvalView.findViewById(R.id.studentSpinner);
        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStudent = studentSpinner.getSelectedItem().toString();
                studentkey = (String) getKeyFromValue(studentMap, selectedStudent);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        /*// Conduct Spinner....
        conductSpinner = (Spinner) examEvalView.findViewById(R.id.conductSpinner);
        conductMap.put("", "-- select --");
        conductMap.put("Cash", "Cash");
        conductMap.put("Cheque", "Cheque");
        conductMap.put("paytm", "paytm");

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



        // Student Spinner...
        feeTypeSpinner = (Spinner) examEvalView.findViewById(R.id.feeType);
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

      /*  // Conduct Spinner....
        feeTypeSpinner = (Spinner) examEvalView.findViewById(R.id.feeType);
        conductMap1.put("", "-- select --");
        conductMap1.put("library", "library");
        conductMap1.put("books", "books");
        conductMap1.put("data", "data");

        conductList1 = new ArrayList<>(conductMap1.values());
        conductAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, conductList1);
        conductAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feeTypeSpinner.setAdapter(conductAdapter1);
        conductAdapter1.notifyDataSetChanged();

        feeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedConduct1 = feeTypeSpinner.getSelectedItem().toString();
                feeTypeKey = (String) getKeyFromValue(conductMap1, selectedConduct1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

*/
        /*fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  showFABMenu();
                AddFeeCollectionFragment addFeeCollectionFragment = new AddFeeCollectionFragment();
                setFragment(addFeeCollectionFragment);
                //   closeFABMenu();
            }
            

        });*/
        /*if (role.equals("admin")) {
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
                //animateFAB();
            }
        });*/


        /*editDate = (EditText) examEvalView.findViewById(R.id.editDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new AllFeeCollectionFragment.DatePickerFragement(), years, month, day);
                datePickerDialog.show();
            }
        });*/


        //
        /*select = examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMarks.clear();
                if (studentkey != null) {
                    new AllFeeCollectionBackTask(getActivity(), AllFeeCollectionReportsFragment.this).execute(studentkey);
                } else {
                    Toast.makeText(getActivity(), "Please select STUDENT", Toast.LENGTH_LONG).show();
                }
            }
        });*/
        //
        recycler_view = (RecyclerView) examEvalView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Fee Collection");


        return examEvalView;
    }

    @Override
    public void allFeeCollectionReportsByDay(String result) throws JSONException {


        if (result != null && !result.isEmpty()) {
            String currentDate = "";
            String[] monthName = {"January", "February", "March", "April", "May", "June", "July",
                    "August", "September", "October", "November", "December"};
            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            String feePaid = jsonObject.getString("feePaid");
            System.out.println("fee Reports check" + jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("fee");
            //    jsonArray.getString("feePaid");
            //  JSONObject markObject = jsonArray.getJSONObject();


            totalAmount.setText(feePaid);

            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {

                    JSONObject markObject = jsonArray.getJSONObject(count);

                    currentDate = markObject.getString("current_date");

                  /*  Calendar cal = Calendar.getInstance();
                    String month = monthName[cal.get(Calendar.MONTH)];*/

                    //  System.out.println("Month name: " + month);
                    String currentDateDisplay = currentDate.substring(0, 10);

                    FeeCollectionReports feeCollectionmodel = new FeeCollectionReports(markObject.getString("student_Name"), markObject.getString("fee_type"), markObject.getString("fee_paid"), markObject.getString("fee_paid"), markObject.getString("payment_mode"), markObject.getString("discount"), markObject.getString("fine"), markObject.getString("current_date"), currentDateDisplay);
                    listMarks.add(feeCollectionmodel);
                    count++;
                }
                //Count....
                String taskCount = "FeeCollection (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);
                feeCollectionReportsByDayAdapter = new AllFeeCollectionReportsByDayAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(feeCollectionReportsByDayAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                feeCollectionReportsByDayAdapter.notifyDataSetChanged();
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

    public class DatePickerFragement2 implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            feeDateByDate.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).append(""));
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                //.addToBackStack(null)
                .commit();
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        //fabLayout2.setVisibility(View.VISIBLE);
        //fabLayout3.setVisibility(View.VISIBLE);
        // fabLayout4.setVisibility(View.VISIBLE);
        // fabLayout5.setVisibility(View.VISIBLE);
        //fabLayout6.setVisibility(View.VISIBLE);

        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        //fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        //fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
        //    fabLayout4.animate().translationY(-getResources().getDimension(R.dimen.standard_190));
        //  fabLayout5.animate().translationY(-getResources().getDimension(R.dimen.standard_235));
        //  fabLayout6.animate().translationY(-getResources().getDimension(R.dimen.standard_280));


    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        //fabLayout1.animate().translationY(0);
        //fabLayout2.animate().translationY(0);
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    //fabLayout2.setVisibility(View.GONE);
                    //fabLayout3.setVisibility(View.GONE);


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

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        editMarks = (EditText) examEvalView.findViewById(R.id.editMarks);
        editPercent = (EditText) examEvalView.findViewById(R.id.editPercent);
        //
        addMarks = (Button) examEvalView.findViewById(R.id.addMarks);




        addMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject addObject = new JSONObject();
                try {
                    addObject.put("fine", editMarks.getText().toString());
                    addObject.put("discount", editPercent.getText().toString());
                   addObject.put("fee_type", feeTypeKey);
                    addObject.put("payment_mode", conductkey);
                    addObject.put("date", editDate.getText().toString());


                    System.out.println("addFeeCollection"+addObject);

                    // addObject.put("conduct", "comment");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AddFeeCollectionBackTask addFeeCollectionBackTask = new AddFeeCollectionBackTask(getActivity());
                addFeeCollectionBackTask.execute(String.valueOf(addObject), studentkey);
            }
              *//*  if (addObject.length() > 0) {
                    if (studentkey!= null) {
                        new AddFeeCollectionBackTask(getActivity()).execute(String.valueOf(addObject),studentkey);
                    } else {
                        Toast.makeText(getActivity(), "Please select Student", Toast.LENGTH_LONG).show();
                    }
                }*//*

        });
    }*/

    /*@Override
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
            studentMap.clear();
            studentMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("students");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject stdObject = jsonArray.getJSONObject(count);
                String name = stdObject.getString("first_name") + " " + stdObject.getString("last_name");
                studentMap.put(stdObject.getString("student_id"), name);
                count++;
            }
            studentList = new ArrayList<String>(studentMap.values());
            //
            studentAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, studentList);
            studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            studentSpinner.setAdapter(studentAdapter);
            studentAdapter.notifyDataSetChanged();
        }
    }




    @Override
    public void allFeeCollection(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("feecollectionobject"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("student_fee_details");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("alleval"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                    String a;
                    String b;
                    String c;
                    String d;
                    int total;
                    String totalValue;
                    a = markObject.getString("fee_amount");
                    b = markObject.getString("fine");
                    c = markObject.getString("discount");
                    d= markObject.getString("fee_paid");


                    int amount=Integer.parseInt(a);
                    int fine=Integer.parseInt(b);
                    int discount=Integer.parseInt(c);
                    int feePaid=Integer.parseInt(d);
                    total=((feePaid+fine)-discount);

                   // total=((amount+fine)-feePaid)-discount;
                    totalValue= String.valueOf(total);
                    System.out.println("total"+totalValue);
                    //paper_result_id
                  *//*  FeeCollection evalMarks = new FeeCollection(markObject.getString("_id"), markObject.getString("exam_paper_id"),
                            selectedPaper, selectedStudent, markObject.getString("marks"),
                            markObject.getString("percentage"), markObject.getString("conduct"), markObject.getString("paper_name"),
                            markObject.getString("first_name"), markObject.getString("examschedule_name"), selectedExamSchedule, selectedSubject);*//*
                  FeeCollection feeCollectionmodel=new FeeCollection(markObject.getString("current_date"),markObject.getString("fee_category"),markObject.getString("payment_mode"),markObject.getString("fee_amount"),markObject.getString("fine"),markObject.getString("discount"),totalValue,markObject.getString("fee_paid"));
                    listMarks.add(feeCollectionmodel);
                    count++;
                }
                //Count....
                String taskCount = "FeeCollection (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);
                feeCollectionAdapter = new FeeCollectionAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(feeCollectionAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                feeCollectionAdapter.notifyDataSetChanged();
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

    }*/

    /*@Override
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


                conductMap1.put(stdObject.getString("_id"), stdObject.getString("fee_type"));


               // String name = stdObject.getString("first_name") + " " + stdObject.getString("last_name");
              //  conductMap1.put(stdObject.getString("student_id"), name);
                count++;
            }
            conductList1 = new ArrayList<String>(conductMap1.values());
            //
            conductAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, conductList1);
            conductAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            feeTypeSpinner.setAdapter(conductAdapter1);
            conductAdapter1.notifyDataSetChanged();
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
    }*/
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
