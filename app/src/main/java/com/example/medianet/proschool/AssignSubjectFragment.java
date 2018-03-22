package com.example.medianet.proschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 07-06-2017.
 */

public class AssignSubjectFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        AllSubjectBackTask.OnSubjectResponse, AllTeacherBackTask.TeacherResponse {
    View assignSubjectView;
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
    //
    Button select;
    // Subject Spinner...
    String subjectkey, selectedSubject;
    Spinner subjectSpinner;
    Map<String, String> subjectMap = new LinkedHashMap<String, String>();
    ArrayList<String> subjectList;
    ArrayAdapter<String> subjectAdapter;
    // Teacher Spinner....
    String teacherkey, selectedTeacher;
    Spinner teacherSpinner;
    Map<String, String> teacherMap = new LinkedHashMap<String, String>();
    ArrayList<String> teacherList;
    ArrayAdapter<String> teacherAdapter;
    //
    Button assign;

    public AssignSubjectFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ClassBackGroundTask(getActivity(), AssignSubjectFragment.this).execute(Constants.schoolId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assignSubjectView = inflater.inflate(R.layout.assign_subject_layout, container, false);
        // class spinner....
        classSpinner = (Spinner) assignSubjectView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), AssignSubjectFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) assignSubjectView.findViewById(R.id.sectionSpinner);
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
        //
        select = (Button) assignSubjectView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting subjects....
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new AllSubjectBackTask(getActivity(), AssignSubjectFragment.this).execute(sectionKey);
                    new AllTeacherBackTask(getActivity(), AssignSubjectFragment.this).execute(Constants.schoolId);
                }
            }
        });
        // subject spinner...
        subjectSpinner = (Spinner) assignSubjectView.findViewById(R.id.subjectSpinner);
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjectSpinner.getSelectedItem().toString();
                subjectkey = (String) getKeyFromValue(subjectMap, selectedSubject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // teacher spinner....
        teacherSpinner = (Spinner) assignSubjectView.findViewById(R.id.teacherSpinner);
        teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTeacher = teacherSpinner.getSelectedItem().toString();
                teacherkey = (String) getKeyFromValue(teacherMap, selectedTeacher);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // assign....
        assign = (Button) assignSubjectView.findViewById(R.id.assign);
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject assignObject = new JSONObject();
                try {
                    assignObject.put("subject_id", subjectkey);
                    assignObject.put("subject_name", selectedSubject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new AssignTeacherBackTask(getActivity()).execute(String.valueOf(assignObject), teacherkey);
            }
        });
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Assign Subject");

        return assignSubjectView;
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
                subjectList = new ArrayList<String>(subjectMap.values());
                //
                subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectSpinner.setAdapter(subjectAdapter);
                subjectAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void TeacherResponse(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            teacherMap.clear();
            teacherMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("teachers");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject teacherObject = jsonArray.getJSONObject(count);
                teacherMap.put(teacherObject.getString("teacher_id"), teacherObject.getString("teacher_id"));
                count++;
            }
            teacherList = new ArrayList<String>(teacherMap.values());
            //
            teacherAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, teacherList);
            teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            teacherSpinner.setAdapter(teacherAdapter);
            teacherAdapter.notifyDataSetChanged();
        }
    }
    /*
    "{
  ""teachers"": [
    {
      ""_id"": ""59289d5e55931b2d3c043f1e"",
      ""teacher_id"": ""SCH-TCH-1"",
      ""school_id"": ""SCH-9273"",
      ""employee_id"": ""SCH-EMP-12"",
      ""added_on"": ""2017-05-20"",
      ""status"": 1,
      ""subjects"": [
        {
          ""subject_id"": ""CH-9273-CL-2-SEC-1-SUB-1"",
          ""subject_name"": ""Drawing1""
        }
      ]
    }
  ]
}"
     */
}
