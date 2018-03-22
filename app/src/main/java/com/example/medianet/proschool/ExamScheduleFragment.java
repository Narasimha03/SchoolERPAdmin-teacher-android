package com.example.medianet.proschool;

import android.animation.Animator;
import android.app.DatePickerDialog;
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
import android.widget.Toast;

import com.example.medianet.proschool.Harish.AddScheduleFragment;
import com.example.medianet.proschool.suresh.activity.AddAssigmentFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 07-06-2017.
 */

public class ExamScheduleFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        AllExamSchBackTask.ExamResponse {
    View examScheduleView;
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
    // EditText...
    EditText editTitle, editStartDate, editEndDate;
    // Button...
    Button addSchedule;
    //
    RecyclerView recycler_view;
    ExamScheduleAdapter examScheduleAdapter;
    ArrayList<ExamSchedule> listExamSchedule = new ArrayList<ExamSchedule>();
    Context mContext;
    FloatingActionButton select;
    String schoolId;
    FloatingActionButton fab;
    LinearLayout fabLayout1;
    View fabBGLayout;
    FrameLayout frameLayout;
    // FrameLayout fabBGLayout;
    boolean isFABOpen = false;
String role;

    public ExamScheduleFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        examScheduleView = inflater.inflate(R.layout.exam_schedule_layout_two, container, false);
     mContext=getActivity();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        new ClassBackGroundTask(getActivity(), ExamScheduleFragment.this).execute(schoolId);

        sliding_layout = (SlidingUpPanelLayout) examScheduleView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examScheduleView.findViewById(R.id.exam_layout);
        textExamCount = (TextView) examScheduleView.findViewById(R.id.textExamCount);

        fabLayout1= (LinearLayout) examScheduleView.findViewById(R.id.fabLayout1);
        fab = (FloatingActionButton) examScheduleView.findViewById(R.id.fab);
        frameLayout=(FrameLayout)examScheduleView.findViewById(R.id.frameLayout);
        fabBGLayout=examScheduleView.findViewById(R.id.fabBGLayout);
        // class spinner....
        classSpinner = (Spinner) examScheduleView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), ExamScheduleFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) examScheduleView.findViewById(R.id.sectionSpinner);
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

        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  showFABMenu();
                /*AddScheduleFragment examScheduleFragment = new AddScheduleFragment();
                setFragment(examScheduleFragment);*/
                //   closeFABMenu();
                Intent i = new Intent(getActivity(),AddScheduleFragment.class);
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

        recycler_view = (RecyclerView) examScheduleView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
     /*((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Exam Schedule");*/

        return examScheduleView;
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
        /*editTitle = (EditText) examScheduleView.findViewById(R.id.editTitle);
        editStartDate = (EditText) examScheduleView.findViewById(R.id.editStartDate);
        editStartDate.setOnClickListener(new View.OnClickListener() {
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
        editEndDate = (EditText) examScheduleView.findViewById(R.id.editEndDate);
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerFragement2(), years, month, day);
                datePickerDialog.show();
            }
        });*/
        select = examScheduleView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listExamSchedule.clear();
                new AllExamSchBackTask(getActivity(), ExamScheduleFragment.this).execute(schoolId);
            }
        });
        /*addSchedule = (Button) examScheduleView.findViewById(R.id.addSchedule);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject addObject = new JSONObject();
                try {
                    addObject.put("exam_title", editTitle.getText().toString());
                    addObject.put("exam_classes", classKey);
                    addObject.put("from_date", editStartDate.getText().toString());
                    System.out.println("addexam schedule"+addObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new AddExamSchBackTask(getActivity()).execute(String.valueOf(addObject), schoolId);
            }
        });*/
        /*
        "{
          ""exam_title"": ""Drawing Exam"",
      ""exam_classes"": ""SCH-9273-CL-2"",
      ""from_date"": ""30-05-2017""
}"
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

    @Override
    public void ExamResponse(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listExamSchedule.clear();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("exam_schedules");

            System.out.println("getexam schedule"+jsonArray);

            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject examObject = jsonArray.getJSONObject(count);
                    ExamSchedule examSchedule = new ExamSchedule(examObject.getString("_id"), examObject.getString("exam_sch_id"),
                            examObject.getString("school_id"), examObject.getString("exam_title"), examObject.getString("exam_title"),
                            examObject.getString("from_date"), examObject.getString("status"));
                    listExamSchedule.add(examSchedule);
                    count++;
                }
                //
                //Count....
                String taskCount = "Exams (" + String.valueOf(listExamSchedule.size()) + ")";
                textExamCount.setText(taskCount);
                //
                examScheduleAdapter = new ExamScheduleAdapter(getActivity(), listExamSchedule);
                recycler_view.setAdapter(examScheduleAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                examScheduleAdapter.notifyDataSetChanged();
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

    /*
    "{
  ""exam_schedules"": [
    {
      ""_id"": ""5929c6eb28f8e51fc09c2ee1"",
      ""exam_sch_id"": ""SCH-9273-EXM_SCH-3"",
      ""school_id"": ""SCH-9273"",
      ""exam_title"": ""Drawing Exam"",
      ""exam_classes"": ""SCH-9273-CL-2"",
      ""from_date"": ""30-05-2017"",
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
            editStartDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }

    public class DatePickerFragement2 implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editEndDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }*/

}
