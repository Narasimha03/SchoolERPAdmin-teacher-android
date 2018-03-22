package com.example.medianet.proschool.suresh.examination;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AddMarkBackTask;
import com.example.medianet.proschool.AllExamPaperBackTask;
import com.example.medianet.proschool.AllExamSchBackTask;
import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.EvalMarkAdapter;
import com.example.medianet.proschool.EvalMarks;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
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

public class ExamEvaluationFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
       AllExamSchBackTask.ExamResponse, AllExamPaperBackTask.ExamPaperResult,
        AllStudentsBackTask.AllStudents, AllEvalMarkBackTask.EvalMarks {

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
    String conductkey, selectedConduct;
    Spinner conductSpinner;
    Map<String, String> conductMap = new LinkedHashMap<String, String>();
    ArrayList<String> conductList;
    ArrayAdapter<String> conductAdapter;
    //
    Button  addMarks;
    FloatingActionButton select;
    //
    RecyclerView recycler_view;
    ArrayList<EvalMarks> listMarks = new ArrayList<EvalMarks>();
    EvalMarkAdapter evalMarkAdapter;
    Context mContext;
    String schoolId;
    String role;

    public ExamEvaluationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        examEvalView = inflater.inflate(R.layout.exam_evaluation_get_layout, container, false);

        mContext=getActivity();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
      //  role = sharedPreferences.getString(Constants.rolePref, "");
        new ClassBackGroundTask(getActivity(), ExamEvaluationFragment.this).execute(schoolId);
        sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examEvalView.findViewById(R.id.exam_layout);
        textExamCount = (TextView) examEvalView.findViewById(R.id.textExamCount);
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
                    new SectionBackGroundTask(getActivity(), ExamEvaluationFragment.this).execute(classKey);
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
                    new AllStudentsBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
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

                if (studentkey != null && !studentkey.isEmpty()) {

                    new AllExamSchBackTask(getActivity(), ExamEvaluationFragment.this).execute(schoolId);

                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                    //new AllStudentsBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                  //  new AllExamPaperBackTask(getActivity(), ExamEvaluationFragment.this).execute(examScheduleKey, sectionKey);

                } else {
                    Toast.makeText(getActivity(), "Please Select Section.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // subject spinner....
       /* subjectSpinner = (Spinner) examEvalView.findViewById(R.id.subjectSpinner);
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjectSpinner.getSelectedItem().toString();
                subjectkey = (String) getKeyFromValue(subjectMap, selectedSubject);
                //
                if (subjectkey != null && !subjectkey.isEmpty()) {
                    new AllExamPaperBackTask(getActivity(), ExamEvaluationFragment.this).execute(subjectkey, examScheduleKey);
                } else {
                    Toast.makeText(getActivity(), "Please Select Subject", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        // Exam Schedule....
        examScheduleSpinner = (Spinner) examEvalView.findViewById(R.id.examScheduleSpinner);
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
        // Paper Spinner...
     /*   paperSpinner = (Spinner) examEvalView.findViewById(R.id.paperSpinner);
        paperSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPaper = paperSpinner.getSelectedItem().toString();
                paperkey = (String) getKeyFromValue(paperMap, selectedPaper);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
/*
        // Conduct Spinner....
        conductSpinner = (Spinner) examEvalView.findViewById(R.id.conductSpinner);
        conductMap.put("", "-- select --");
        conductMap.put("Good", "Good");
        conductMap.put("Bad", "Bad");
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
        });*/
        //
        select = examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMarks.clear();
                if (studentkey != null && examScheduleKey != null) {
                    new AllEvalMarkBackTask(getActivity(), ExamEvaluationFragment.this).execute(studentkey, examScheduleKey);
                } else {
                    Toast.makeText(getActivity(), "Please select EXAM SCHEDULE and STUDENT", Toast.LENGTH_LONG).show();
                }
            }
        });
        //
        recycler_view = (RecyclerView) examEvalView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //noinspection ConstantConditions
        /*((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Evaluations");*/
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return examEvalView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
     //   editMarks = (EditText) examEvalView.findViewById(R.id.editMarks);
    //    editPercent = (EditText) examEvalView.findViewById(R.id.editPercent);
        //
     //   addMarks = (Button) examEvalView.findViewById(R.id.addMarks);
   /*     addMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject addObject = new JSONObject();
                try {
                    addObject.put("marks", editMarks.getText().toString());
                    addObject.put("percentage", editPercent.getText().toString());
                    addObject.put("conduct", conductkey);
                    System.out.println("addeval"+addObject);

                    // addObject.put("conduct", "comment");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (addObject.length() > 0) {
                    if (studentkey != null) {
                      //  new com.example.medianet.proschool.suresh.examination.AddMarkBackTask(getActivity()).execute(String.valueOf(addObject), examScheduleKey,paperkey, studentkey,sectionKey,classKey);
                    } else {
                        Toast.makeText(getActivity(), "Please select PAPER and STUDENT", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });*/
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
            classAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, classList);
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

 /*   @Override
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
    public void examPaper(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            paperMap.clear();
            paperMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
          JSONArray jsonArray = jsonObject.getJSONArray("resultArray");

//System.out.println("evalpapercheck"+jsonArray);
        // JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("evalpapercheck"+jsonArray);

            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject examObject = jsonArray.getJSONObject(count);
                    paperMap.put(examObject.getString("exam_paper_id"), examObject.getString("exam_paper_title"));
                    count++;
                }
                paperList = new ArrayList<String>(paperMap.values());
                //
                paperAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paperList);
                paperAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                paperSpinner.setAdapter(paperAdapter);
                paperAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void allStudents(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            studentMap.clear();
            studentMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("students");
            System.out.println("");
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
    public void evalMarks(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("evalobject"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("resultArray");

         //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("alleval"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);
                    //paper_result_id
                    EvalMarks evalMarks = new EvalMarks(markObject.getString("_id"), markObject.getString("paper_name"),
                            selectedPaper, selectedStudent, markObject.getString("marks"),
                            markObject.getString("percentage"), markObject.getString("conduct"), markObject.getString("paper_name"),
                            markObject.getString("first_name"), markObject.getString("examschedule_name"), selectedExamSchedule, selectedSubject);
                    listMarks.add(evalMarks);
                    count++;
                }
                //Count....
                String taskCount = "ExamEvaluations (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);

                evalMarkAdapter = new EvalMarkAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(evalMarkAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                evalMarkAdapter.notifyDataSetChanged();
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
