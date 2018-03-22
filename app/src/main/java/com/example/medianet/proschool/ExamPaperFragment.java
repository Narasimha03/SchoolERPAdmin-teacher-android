package com.example.medianet.proschool;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medianet.proschool.Harish.AddScheduleFragment;
import com.example.medianet.proschool.suresh.activity.AddAssigmentFragment;
import com.example.medianet.proschool.suresh.examination.*;
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
 * Created by JANI on 16-06-2017.
 */

public class ExamPaperFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse, AllExamSchBackTask.ExamResponse, AllExamPaperBackTask.ExamPaperResult {

    View examPaperView;
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
    // EditText....
    EditText editTitle, editMarks, editDate, editStartTime, editEndTime;
    //
    //Button select, addPaper;
    FloatingActionButton select;
    //
    RecyclerView recycler_view;
    ArrayList<ExamPaper> listPaper = new ArrayList<ExamPaper>();
    ExamPaperAdapter examPaperAdapter;
    Context mContext;
    String schoolId;
    FloatingActionButton fab;
    LinearLayout fabLayout1;
    View fabBGLayout;
    FrameLayout frameLayout;
    // FrameLayout fabBGLayout;
    boolean isFABOpen = false;
    String role;

    public ExamPaperFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        examPaperView = inflater.inflate(R.layout.exam_paper_layout_two, container, false);
        mContext=getActivity();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        new ClassBackGroundTask(getActivity(), ExamPaperFragment.this).execute(schoolId);

        sliding_layout = (SlidingUpPanelLayout) examPaperView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examPaperView.findViewById(R.id.exam_layout);
        textExamCount = (TextView) examPaperView.findViewById(R.id.textExamCount);
        fabLayout1= (LinearLayout) examPaperView.findViewById(R.id.fabLayout1);
        fab = (FloatingActionButton) examPaperView.findViewById(R.id.fab);
        frameLayout=(FrameLayout)examPaperView.findViewById(R.id.frameLayout);
        fabBGLayout=examPaperView.findViewById(R.id.fabBGLayout);
        // class spinner....
        classSpinner = (Spinner) examPaperView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), ExamPaperFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) examPaperView.findViewById(R.id.sectionSpinner);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                // Getting subjects....
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    //new AllSubjectBackTask(getActivity(), ExamPaperFragment.this).execute(sectionKey);
                    new AllExamSchBackTask(getActivity(), ExamPaperFragment.this).execute(schoolId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*// subject spinner....
        subjectSpinner = (Spinner) examPaperView.findViewById(R.id.subjectSpinner);
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjectSpinner.getSelectedItem().toString();
                subjectkey = (String) getKeyFromValue(subjectMap, selectedSubject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        //
        examScheduleSpinner = (Spinner) examPaperView.findViewById(R.id.examScheduleSpinner);
        examScheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedExamSchedule = examScheduleSpinner.getSelectedItem().toString();
                examScheduleKey = (String) getKeyFromValue(examScheduleMap, selectedExamSchedule);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  showFABMenu();
                /*AddExamPaperFragment examPaperFragment = new AddExamPaperFragment();
                setFragment(examPaperFragment);*/
                //   closeFABMenu();

                Intent i = new Intent(getActivity(),AddExamPaperFragment.class);
                startActivity(i);

                closeFABMenu();
            }


        });
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
                //animateFAB();
            }
        });
        //
        recycler_view = (RecyclerView) examPaperView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        /*((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Exam Paper");*/

        return examPaperView;
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);

        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));


    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator)
            {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);



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
        /*editTitle = (EditText) examPaperView.findViewById(R.id.editTitle);
        editMarks = (EditText) examPaperView.findViewById(R.id.editMarks);
        editDate = (EditText) examPaperView.findViewById(R.id.editDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerFragement(), years, month, day);
                datePickerDialog.show();
            }
        });
        editStartTime = (EditText) examPaperView.findViewById(R.id.editStartTime);
        editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editStartTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        editEndTime = (EditText) examPaperView.findViewById(R.id.editEndTime);
        editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editEndTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });*/

        select = examPaperView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPaper.clear();
                /*if (sectionKey != null && !sectionKey.isEmpty()) {
                //    new AllExamPaperBackTask(getActivity(), ExamPaperFragment.this).execute(subjectkey, examScheduleKey);*/

                    new AllExamPaperBackTask(getActivity(), ExamPaperFragment.this).execute(examScheduleKey, sectionKey);
                /*} else {
                    Toast.makeText(getActivity(), "Please Select Subject...!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        /*addPaper = (Button) examPaperView.findViewById(R.id.addPaper);
        addPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject addObject = new JSONObject();
                try {
                    addObject.put("exam_paper_title", editTitle.getText().toString());
                    addObject.put("date", editDate.getText().toString());
                    addObject.put("start_time", editStartTime.getText().toString());
                    addObject.put("end_time", editEndTime.getText().toString());
                    addObject.put("max_marks", editMarks.getText().toString());

                    System.out.println("add paper"+addObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (addObject.length() > 0) {
                    if (examScheduleKey != null) {
                        new com.example.medianet.proschool.suresh.examination.AddExamPaperBackTask(getActivity()).execute(String.valueOf(addObject), subjectkey, examScheduleKey,classKey,sectionKey);
                        System.out.println("add paper"+addObject);

                    } else {
                        Toast.makeText(getActivity(), "Please select exam schedule....!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });*/
        /*
        "{
          ""exam_paper_title"": ""Test Exam"",
            ""date"": ""30-05-2017"",
            ""start_time"": ""10:30 AM"",
            ""end_time"": ""11.30 AM"",
            ""max_marks"": ""100""
}
"
         */
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

    /*@Override
    public void OnSubjectResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            subjectMap.clear();
            subjectMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject subObject = jsonArray.getJSONObject(count);
                    subjectMap.put(subObject.getString("subject_id"), subObject.getString("name"));
                    count++;
                }
                subjectList = new ArrayList<>(subjectMap.values());
                //
                subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectSpinner.setAdapter(subjectAdapter);
                subjectAdapter.notifyDataSetChanged();
            }
        }
    }*/

    @Override
    public void ExamResponse(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            examScheduleMap.clear();
            examScheduleMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("exam_schedules");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject examObject = jsonArray.getJSONObject(count);
                examScheduleMap.put(examObject.getString("exam_sch_id"), examObject.getString("exam_title"));
                count++;
            }
            examScheduleList = new ArrayList<>(examScheduleMap.values());

            examScheduleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, examScheduleList);
            examScheduleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            examScheduleSpinner.setAdapter(examScheduleAdapter);
            examScheduleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void examPaper(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listPaper.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("exam json paper"+jsonObject);
         //   JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + subjectkey);


            JSONArray jsonArray = jsonObject.getJSONArray("resultArray");
            System.out.println("exam paper"+jsonArray);
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject examObject = jsonArray.getJSONObject(count);
                    ExamPaper examPaper = new ExamPaper(examObject.getString("_id"), examObject.getString("exam_paper_id"),
                            selectedSubject, examObject.getString("exam_sch_id"), examObject.getString("exam_paper_title"),
                            examObject.getString("date"), examObject.getString("start_time"), examObject.getString("end_time"),
                            examObject.getString("max_marks"), examObject.getString("class_id"));
                    listPaper.add(examPaper);
                    count++;
                }
                //Count....
                String taskCount = "ExamPapers (" + String.valueOf(listPaper.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);

                examPaperAdapter = new ExamPaperAdapter(getActivity(), listPaper);
                recycler_view.setAdapter(examPaperAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                examPaperAdapter.notifyDataSetChanged();
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

    /*
    "{
  ""SCH-9273-EXM_SCH-3-SCH-9273-CL-2-SEC-1-SUB-1"": [
    {
      ""_id"": ""592bef0b501c1018082fd58d"",
      ""exam_paper_id"": ""SCH-9273-EXM_SCH-3-SCH-9273-CL-2-SEC-1-SUB-1-EXM-5"",
      ""subject_id"": ""SCH-9273-CL-2-SEC-1-SUB-1"",
      ""exam_sch_id"": ""SCH-9273-EXM_SCH-3"",
      ""exam_paper_title"": ""Test Exam"",
      ""date"": ""30-05-2017"",
      ""start_time"": ""10:30 AM"",
      ""end_time"": ""11.30 AM"",
      ""max_marks"": ""100"",
      ""status"": 1
    }
  ]
}"
     */

    /*public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }*/
}
