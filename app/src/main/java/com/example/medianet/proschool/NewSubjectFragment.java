package com.example.medianet.proschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 16-06-2017.
 */

public class NewSubjectFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        AllSubjectBackTask.OnSubjectResponse {

    View subjectView;
    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout sub_layout;
    TextView textSubCount;
    //
    EditText editSubName, editSubCode;
    // Class Spinner....
    String classKey, selectedClass;
    Spinner classSpinner;
    Map<String, String> classMap = new LinkedHashMap<String, String>();
    ArrayList<String> classList;
    ArrayAdapter<String> classAdapter;
    // Section Spinner....
    String sectionKey = "";
    String selectedSection;
    Spinner sectionSpinner;
    Map<String, String> sectionMap = new LinkedHashMap<String, String>();
    ArrayList<String> sectionList;
    ArrayAdapter<String> sectionAdapter;
    //
    RecyclerView recycler_view;
    SubjectAdapter subjectAdapter;
    //StationAdapter stationAdapter;
    ArrayList<Subject> subjectList = new ArrayList<Subject>();
    //
    Button select, addSubject;

    public NewSubjectFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ClassBackGroundTask(getActivity(), NewSubjectFragment.this).execute(Constants.schoolId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        subjectView = inflater.inflate(R.layout.subject_layout, container, false);
        sliding_layout = (SlidingUpPanelLayout) subjectView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        sub_layout = (LinearLayout) subjectView.findViewById(R.id.sub_layout);
        textSubCount = (TextView) subjectView.findViewById(R.id.textSubCount);
        // class spinner....
        classSpinner = (Spinner) subjectView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), NewSubjectFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) subjectView.findViewById(R.id.sectionSpinner);
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
        recycler_view = (RecyclerView) subjectView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);

        select = (Button) subjectView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectList.clear();
                if (sectionKey.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_LONG).show();
                } else {
                    new AllSubjectBackTask(getActivity(), NewSubjectFragment.this).execute(sectionKey);
                }
            }
        });

        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Subjects");

        return subjectView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editSubName = (EditText) subjectView.findViewById(R.id.editSubName);
        editSubCode = (EditText) subjectView.findViewById(R.id.editSubCode);
        addSubject = (Button) subjectView.findViewById(R.id.addSubject);

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject saveObject = new JSONObject();
                try {
                    saveObject.put("name", editSubName.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AddSubjectBackTask addSubjectBackTask = new AddSubjectBackTask(getActivity());
                addSubjectBackTask.execute(String.valueOf(saveObject), sectionKey);
            }
        });
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
            //Log.e("Section", response);
        }
    }

    @Override
    public void OnSubjectResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            subjectList.clear();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject subObject = jsonArray.getJSONObject(count);
                    Subject subject = new Subject(subObject.getString("name"),subObject.getString("subject_id"));
                    subjectList.add(subject);
                    count++;
                }
                //
                //Count....
                String taskCount = "Subjects (" + String.valueOf(subjectList.size()) + ")";
                textSubCount.setText(taskCount);
                //
                subjectAdapter = new SubjectAdapter(getActivity(), subjectList);
                recycler_view.setAdapter(subjectAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                subjectAdapter.notifyDataSetChanged();
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
  ""subjects"": [
    {
      ""_id"": ""5926ada2f78c0904ccd5736f"",
      ""subject_id"": ""SCH-9273-CL-2-SEC-1-SUB-1"",
      ""section_id"": ""SCH-9273-CL-2-SEC-1"",
      ""name"": ""Drawing""
    }
  ]
}"
     */
}
