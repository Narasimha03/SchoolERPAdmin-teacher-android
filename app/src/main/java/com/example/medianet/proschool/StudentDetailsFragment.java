package com.example.medianet.proschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by personal on 23-04-2017.
 */

public class StudentDetailsFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        SearchSTDBackTask.OnSearchStd {
    Context mContext;
    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout std_layout;
    TextView textStdCount;
    // Edit Search....
    EditText editSearch;
    ImageView imgSearch;
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
    // Year Spinner....
    String yearKey, selectedYear;
    Spinner yearSpinner;
    Map<String, String> yearMap = new LinkedHashMap<String, String>();
    ArrayList<String> yearList;
    ArrayAdapter<String> yearAdapter;
    // Button....
    Button search;
    // Recyclerview....
    RecyclerView recycler_view;
    StudentDetailsAdapter studentDetailsAdapter;
    ArrayList<StudentsDetails> studentsDetailsArrayList = new ArrayList<StudentsDetails>();

    public StudentDetailsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ClassBackGroundTask(getActivity(), StudentDetailsFragment.this).execute(Constants.schoolId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View studentDetails = inflater.inflate(R.layout.student_details_layout, container, false);
        sliding_layout = (SlidingUpPanelLayout) studentDetails.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        std_layout = (LinearLayout) studentDetails.findViewById(R.id.std_layout);
        textStdCount = (TextView) studentDetails.findViewById(R.id.textStdCount);
        //
        editSearch = (EditText) studentDetails.findViewById(R.id.editSearch);
        imgSearch = (ImageView) studentDetails.findViewById(R.id.imgSearch);
        // class spinner....
        classSpinner = (Spinner) studentDetails.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), StudentDetailsFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) studentDetails.findViewById(R.id.sectionSpinner);
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
        // year spinner....
        yearSpinner = (Spinner) studentDetails.findViewById(R.id.yearSpinner);
        yearMap.put("", "-- select --");
        yearMap.put("2017", "2017");
        yearMap.put("2016", "2016");
        yearMap.put("2015", "2015");
        yearList = new ArrayList<String>(yearMap.values());
        yearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearAdapter.notifyDataSetChanged();
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = yearSpinner.getSelectedItem().toString();
                yearKey = (String) getKeyFromValue(yearMap, selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recycler_view = (RecyclerView) studentDetails.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // To Search Student....
        search = (Button) studentDetails.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentsDetailsArrayList.clear();
                if (editSearch.getText().toString().isEmpty() || yearKey.isEmpty() || classKey.isEmpty() || sectionKey.isEmpty()) {
                    Toast.makeText(getActivity(), "Please provide all details...!", Toast.LENGTH_LONG).show();
                } else {
                    new SearchSTDBackTask(getActivity(), StudentDetailsFragment.this).execute(yearKey, classKey, sectionKey, editSearch.getText().toString());
                }

            }
        });

        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Student Details");

        return studentDetails;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // To Display Classes....
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

    // To Display Sections....
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
    public void onSearchStd(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            studentsDetailsArrayList.clear();
            String stdName;
            String parentName = "";
            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    stdName = jsonObject.getString("first_name") + jsonObject.getString("last_name");
                    // Parent Array....
                    if (!jsonObject.isNull("parent")) {
                        JSONArray parentArray = jsonObject.getJSONArray("parent");
                        int p = 0;
                        while (p < parentArray.length()) {
                            JSONObject parentObject = parentArray.getJSONObject(p);
                            parentName = parentObject.getString("parent_name");
                            p++;
                        }
                    }

                    StudentsDetails studentsDetails = new StudentsDetails(jsonObject.getString("student_id"), jsonObject.getString("admission_no"),
                            stdName, jsonObject.getString("class_id"), parentName, jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("category"), jsonObject.getString("phone"), jsonObject.getString("status"),
                            jsonObject.getString("roll_no"),false, jsonObject.getString("roll_no"));
                    studentsDetailsArrayList.add(studentsDetails);

                    count++;
                }
                //Count....
                String taskCount = "Students (" + String.valueOf(studentsDetailsArrayList.size()) + ")";
                textStdCount.setText(taskCount);
                //Recyclerview....
                recycler_view.setVisibility(View.VISIBLE);
                studentDetailsAdapter = new StudentDetailsAdapter(getActivity(), studentsDetailsArrayList);
                recycler_view.setAdapter(studentDetailsAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentDetailsAdapter.notifyDataSetChanged();
                //Sliding Panel
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(320);
            } else {
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*

    {"class_sections":[{"_id":"5927f96b5e22d92b8ae23036","section_id":"SCH-9271-CL-1-SEC-1","class_id":"SCH-9271-CL-1","name":"section A","status":1}]}
            {"school_classes":[{"_id":"59140a7d816fb10c35252493","class_id":"SCH-9271-CL-1","school_id":"SCH-9271","name":"class 1","status":1},{"_id":"59140b14816fb10c35252494","class_id":"SCH-9271-CL-2","school_id":"SCH-9271","name":"class 2","status":1},{"_id":"59154c04816fb10c35252499","class_id":"SCH-9271-CL-3","school_id":"SCH-9271","name":"2nd Class","status":1},{"_id":"59154c25816fb10c3525249a","class_id":"SCH-9271-CL-4","school_id":"SCH-9271","name":"2nd Class","status":1}]}
             */
}
