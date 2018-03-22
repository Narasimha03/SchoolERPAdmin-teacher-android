package com.example.medianet.proschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 29-06-2017.
 */

public class AddTimeTableFragment extends AppCompatActivity implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        AllSubjectBackTask.OnSubjectResponse{

    View addTimeTableView;
    Context mContext;
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
    // Subject Spinner...
    String subjectkey, selectedSubject;
    Spinner subjectSpinner;
    Map<String, String> subjectMap = new LinkedHashMap<String, String>();
    ArrayList<String> subjectList;
    ArrayAdapter<String> subjectAdapter;
    // Button...
    Button submit, select;


    //

    //
    String conductkey,timeKey, selectedConduct,selectTime;
    Spinner conductSpinner,timeSpinner;
    Map<String, String> conductMap = new LinkedHashMap<String, String>();
    Map<String, String> timeMap = new LinkedHashMap<String, String>();

    ArrayList<String> conductList;
    ArrayList<String> timeList;

    ArrayAdapter<String> conductAdapter;
    ArrayAdapter<String> timeAdapter;
    String schoolId;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_time_table_layout);
        mContext = this;

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new ClassBackGroundTask(mContext, AddTimeTableFragment.this).execute(schoolId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.test_toolabr);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // class spinner....
        classSpinner = (Spinner) findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(mContext, AddTimeTableFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) findViewById(R.id.sectionSpinner);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                // Getting subjects....
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new AllSubjectBackTask(mContext, AddTimeTableFragment.this).execute(sectionKey);
                } else {
                    Toast.makeText(mContext, "Please select Section..!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //


        // Conduct Spinner....
        conductSpinner = (Spinner) findViewById(R.id.daySpinner);
        conductMap.put("", "-- select --");
        conductMap.put("1", "Sunday");

        conductMap.put("2", "Monday");
        conductMap.put("3", "Tuesday");
        conductMap.put("4", "Wednesday");
        conductMap.put("5", "Thursday");
        conductMap.put("6", "Friday");
        conductMap.put("7", "Saturday");
        conductList = new ArrayList<>(conductMap.values());
        conductAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, conductList);
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


        // Conduct Spinner....
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        timeMap.put("", "-- select --");
        timeMap.put("9:30-10:30", "9:30-10:30");
        timeMap.put("10:30-11:30", "10:30-11:30");
        timeMap.put("11:30-12:30", "11:30-12:30");
        timeMap.put("1:30:2:30", "1:30:2:30");
        timeMap.put("2:30-3:30", "2:30-3:30");
        timeMap.put("3:30-4:40", "3:30-4:40");

        timeList = new ArrayList<>(timeMap.values());
        timeAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, timeList);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectTime = timeSpinner.getSelectedItem().toString();
                timeKey = (String) getKeyFromValue(timeMap, selectTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //
      /*  submit = (Button) addTimeTableView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        // subject spinner...
        subjectSpinner = (Spinner) findViewById(R.id.subjectSpinner);
        subjectMap.put("", "-- select --");
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



        select = (Button) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject saveObject = new JSONObject();
                try {
                    saveObject.put("day",conductkey);
                    saveObject.put("start_time",timeKey);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("add time Table:"+saveObject);

                if (saveObject.length() > 0) {
                    new AddTimeTableBackTask(mContext).execute(String.valueOf(saveObject), sectionKey,subjectkey);
                }
            }
        });

        //noinspection ConstantConditions
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Timetable");

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
            classAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, classList);
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
            sectionAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, sectionList);
            sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sectionSpinner.setAdapter(sectionAdapter);
            sectionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnSubjectResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
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
                subjectAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, subjectList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectSpinner.setAdapter(subjectAdapter);
                subjectAdapter.notifyDataSetChanged();
            }
        }
    }
}
