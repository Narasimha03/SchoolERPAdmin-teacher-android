package com.example.medianet.proschool.suresh.examination;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AllExamPaperBackTask;
import com.example.medianet.proschool.AllExamSchBackTask;
import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 17-06-2017.
 */

public class BulkEvaluationFragment extends Fragment implements ClassBackGroundTask.OnClassResponse,SectionBackGroundTask.OnSectionResponse,AllStudentsBackTask.AllStudents,AllExamSchBackTask.ExamResponse,AllExamPaperBackTask.ExamPaperResult {

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
    ArrayList<BulkEvaluationModel> listMarks = new ArrayList<>();
    BulkMarksAdpter bulkMarksAdpter;
    Context mContext;
    String schoolId;
    String role;
    Button submitMarks;

    private List<BulkEvaluationModel> bulkEvalMarksArray= new ArrayList<BulkEvaluationModel>();;


    public BulkEvaluationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        examEvalView = inflater.inflate(R.layout.exam_evaluation_get_layout_two, container, false);

        mContext = getActivity();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        new ClassBackGroundTask(getActivity(), BulkEvaluationFragment.this).execute(schoolId);

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
                    new SectionBackGroundTask(getActivity(), BulkEvaluationFragment.this).execute(classKey);
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
                    new AllStudentsBackTask(getActivity(), BulkEvaluationFragment.this).execute(sectionKey);
                    new AllExamSchBackTask(getActivity(), BulkEvaluationFragment.this).execute(schoolId);

                    //  new AllExamPaperBackTask(getActivity(), ExamEvaluationFragment.this).execute(examScheduleKey, sectionKey);

                } else {
                    Toast.makeText(getActivity(), "Please Select Section.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        // Exam Schedule....
        examScheduleSpinner = (Spinner) examEvalView.findViewById(R.id.examScheduleSpinner);
        examScheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedExamSchedule = examScheduleSpinner.getSelectedItem().toString();
                examScheduleKey = (String) getKeyFromValue(examScheduleMap, selectedExamSchedule);
                if (examScheduleKey != null && !examScheduleKey.isEmpty()&&sectionKey != null && !sectionKey.isEmpty()) {

                    new AllExamPaperBackTask(getActivity(), BulkEvaluationFragment.this).execute(examScheduleKey, sectionKey);

                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                  //  new AllExamPaperBackTask(getActivity(), BulkEvaluationFragment.this).execute(subjectkey, examScheduleKey);

                } else {
                    Toast.makeText(getActivity(), "Please Select Schedule or Section", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        paperSpinner = (Spinner) examEvalView.findViewById(R.id.examPaperSpinner);
        paperSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPaper = paperSpinner.getSelectedItem().toString();
                paperkey = (String) getKeyFromValue(paperMap, selectedPaper);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        submitMarks = (Button) examEvalView.findViewById(R.id.submitMarks);
        submitMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(SubmitAttendanceStudentActivity.this,NextActivity.class);
                startActivity(intent);*/

                String[] escrito = bulkMarksAdpter.getQuantities();
                //  System.out.println("arr1: " + Arrays.toString(escrito));

                String[] marks = bulkMarksAdpter.getMarks();

                ArrayList<String> arraylist1 = new ArrayList<>();
                Collections.addAll(arraylist1, escrito);

                ArrayList<String> studentSend = new ArrayList<>();
                Collections.addAll(studentSend, marks);
                JSONArray jsonArray = new JSONArray();
                JSONObject cartItemsObjedct;

                if (arraylist1.contains(null)||arraylist1.contains("")) {
                    Toast.makeText(getActivity(), "Please Enter All Students Marks....!", Toast.LENGTH_SHORT).show();

                }
                else
                {

                    int p = 0;
                    for (Iterator<String> it = arraylist1.iterator(); it.hasNext(); p++) {
                        String s = it.next();
                      //  System.out.println(p + ": " + s);
                        int q = 0;
                        for (Iterator<String> stdIt = studentSend.iterator(); stdIt.hasNext(); q++) {

                            {
                                String stdOk = stdIt.next();
                                if (p == q) {

                             /*   if (s==null ||s.isEmpty()) {
                                    Toast.makeText(getActivity(), "Please Fill  Student Marks....!", Toast.LENGTH_SHORT).show();

                                }*/
                                    if (s != null && !s.isEmpty()) {
                                        cartItemsObjedct = new JSONObject();
                                        try {


                                            cartItemsObjedct.putOpt("student_id",
                                                    stdOk);

                                            cartItemsObjedct.putOpt("marks", s);
                                            jsonArray.put(cartItemsObjedct);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        JSONObject singleObject = new JSONObject();
                                        try {
                                            singleObject.put("studentsMarks", jsonArray);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        String str = singleObject.toString();
                                        System.out.println("data marks:" + str);

                                        //  if (examScheduleKey != null && !examScheduleKey.isEmpty()) {


                                        if (singleObject.length() > 0) {
                                            AddBulkMarksBackTask addBulkMarksBackTask = new AddBulkMarksBackTask(mContext);

                                            addBulkMarksBackTask.execute(String.valueOf(singleObject), examScheduleKey, paperkey, sectionKey, classKey);

                                        } else {
                                            Toast.makeText(getActivity(), "Please Fill Atleast One Student Marks....!", Toast.LENGTH_SHORT).show();

                                        }


                                        //  Toast.makeText(getActivity(), "Please Select Schedule....!", Toast.LENGTH_SHORT).show();
                                    }
                                 /*else {
                                     Toast.makeText(getActivity(), "Enter Student Marks....!", Toast.LENGTH_SHORT).show();

                                 }*/

                                }


                            }

                        }

                    }
                }

            }


        });
        recycler_view = examEvalView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        return examEvalView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
           // sectionMap.put("", "-- select --");
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
            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("evalobject"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("students");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("alleval"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);
                    String maxMarks="100";
                    String name = markObject.getString("first_name") + " " + markObject.getString("last_name");

                    //paper_result_id
                    BulkEvaluationModel evalMarks = new BulkEvaluationModel(name,maxMarks,markObject.getString("student_id"));

                    listMarks.add(evalMarks);
                    count++;
                }
                //Count....
                String taskCount = "ExamEvaluations (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);

                bulkMarksAdpter = new BulkMarksAdpter(getActivity(), listMarks);
                recycler_view.setAdapter(bulkMarksAdpter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                bulkMarksAdpter.notifyDataSetChanged();
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
    public void ExamResponse(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            examScheduleMap.clear();
          //  examScheduleMap.put("", "-- select --");
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
            paperMap.clear();
           // paperMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
          JSONArray jsonArray = jsonObject.getJSONArray("resultArray");

//System.out.println("evalpapercheck"+jsonArray);
        // JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("evalpapercheck"+jsonArray);

            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject examObject = jsonArray.getJSONObject(count);
                paperMap.put(examObject.getString("exam_paper_id"), examObject.getString("exam_paper_title"));
                count++;
            }

            paperList = new ArrayList<>(paperMap.values());

            paperAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paperList);
            paperAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            paperSpinner.setAdapter(paperAdapter);
            paperAdapter.notifyDataSetChanged();

        }
    }


}
