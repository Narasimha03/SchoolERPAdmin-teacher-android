package com.example.medianet.proschool.suresh.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AddAssignmnetBackTask;
import com.example.medianet.proschool.AllChapterBackTask;
import com.example.medianet.proschool.AllSubjectBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;


/**
 * Created by JANI on 03-06-2017.
 */

public class AddAssigmentFragment2 extends AppCompatActivity implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        AllSubjectBackTask.OnSubjectResponse, AllChapterBackTask.ChapterResponse {

    View assignmentView;
    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout assignment_layout;
    TextView textAssignCount;
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
    //
    Button select;
    // Chapter Spinner....
    String chapterKey, selectedChapter;
    Spinner chapterSpinner;
    Map<String, String> chapterMap = new LinkedHashMap<String, String>();
    ArrayList<String> chapterList;
    ArrayAdapter<String> chapterAdapter;
    //
    EditText editDescription, editDueDate, editAssignName;
    //
    Button addAssignment;

    Context mContext;
    String schoolId;


    public AddAssigmentFragment2() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assignments_layout_activity);
        mContext = this;

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new ClassBackGroundTask(mContext, AddAssigmentFragment2.this).execute(schoolId);

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
                    new SectionBackGroundTask(mContext, AddAssigmentFragment2.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner)findViewById(R.id.sectionSpinner);
     //   sectionMap.put("", "-- select --");
        sectionSpinner = (Spinner)findViewById(R.id.sectionSpinner);
        sectionMap.put("", "-- select --");
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                // Getting subjects....
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new AllSubjectBackTask(mContext, AddAssigmentFragment2.this).execute(sectionKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // subject spinner...
        subjectSpinner = (Spinner)findViewById(R.id.subjectSpinner);
      //  subjectMap.put("", "-- select --");
        subjectSpinner = (Spinner) findViewById(R.id.subjectSpinner);
        subjectMap.put("", "-- select --");
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjectSpinner.getSelectedItem().toString();
                subjectkey = (String) getKeyFromValue(subjectMap, selectedSubject);
                if (subjectkey != null && !subjectkey.isEmpty()) {
                    new AllChapterBackTask(mContext, AddAssigmentFragment2.this).execute(subjectkey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // chapter spinner;
        chapterSpinner = (Spinner) findViewById(R.id.chapterSpinner);
       // chapterMap.put("", "-- select --");
        chapterSpinner = (Spinner) findViewById(R.id.chapterSpinner);
        chapterMap.put("", "-- select --");
        chapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedChapter = chapterSpinner.getSelectedItem().toString();
                chapterKey = (String) getKeyFromValue(chapterMap, selectedChapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editDescription = (EditText) findViewById(R.id.editDescription);
        editAssignName = (EditText)findViewById(R.id.editAssignName);
        //due date....
        editDueDate = (EditText) findViewById(R.id.editDueDate);
        editDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerFragement(), years, month, day);
                datePickerDialog.show();
            }
        });
        //
        addAssignment = (Button) findViewById(R.id.addAssignment);
        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject addObject = new JSONObject();
                try {
                    addObject.put("assignment_title", editAssignName.getText().toString());
                    addObject.put("due_date", editDueDate.getText().toString());
                    addObject.put("description", editDescription.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("add assignment response"+addObject);
                if (chapterKey != null && !chapterKey.isEmpty()) {
                    AddAssignmnetBackTask addAssignmnetBackTask = new AddAssignmnetBackTask(mContext);
                    addAssignmnetBackTask.execute(String.valueOf(addObject), sectionKey, chapterKey);
                }
                else {
                    Toast.makeText(mContext, "Please select Chapter..!", Toast.LENGTH_LONG).show();
                }

                AddAssignmnetBackTask addAssignmnetBackTask = new AddAssignmnetBackTask(mContext);
                addAssignmnetBackTask.execute(String.valueOf(addObject), sectionKey, chapterKey);
            }
        });
        /*
        "{
        ""assignment_title"": ""Test assignment title"",
        ""due_date"": ""26-05-2017"",
        ""description"": ""this is test description for assignment""
            }"
         */

        // to set action bar title....
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Assignment");

    }

    @Override
    public void onClassResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            //classMap.clear();
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
            subjectMap.clear();
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

    @Override
    public void ChapterResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            chapterMap.clear();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("chapters");
            Log.e("subject_key", String.valueOf(jsonArray));
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject chapObject = jsonArray.getJSONObject(count);
                    chapterMap.put(chapObject.getString("lession_id"), chapObject.getString("title"));
                    count++;
                }
            }
            chapterList = new ArrayList<>(chapterMap.values());
            //
            chapterAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, chapterList);
            chapterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chapterSpinner.setAdapter(chapterAdapter);
            chapterAdapter.notifyDataSetChanged();
        }
    }


    /*
    "{
  ""assignments"": [
    {
      ""_id"": ""592d21dd16b61d077cc5351e"",
      ""assignment_id"": ""ASMT-1"",
      ""assignment_title"": ""Test assignment title"",
      ""section_id"": ""SCH-9273-CL-2-SEC-1"",
      ""lesson_id"": ""SCH-9273-CL-2-SEC-1-SUB-2-LES-3"",
      ""due_date"": ""26-05-2017"",
      ""description"": ""this is test description for assignment""
    }
  ]
}"
     */

    public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editDueDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }
}
