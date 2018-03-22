package com.example.medianet.proschool;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 01-07-2017.
 */

public class TeacherwiseTimetableFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        TimeTableBackTask.Timetable, AllTeacherBackTask.TeacherResponse{

    Context mContext;
    View teacherView;
    TableLayout tableLayout;
    TableRow tr;
    TextView timeTv, sub1Tv, sub2Tv, sub3Tv, sub4Tv, sub5Tv, sub6Tv;

    String day[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    String time[] = {"9:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-1:00", "1:00-2:00", "3:00-4:00"};
    String subject[] = {"subject-1", "subject-2", "subject-3", "subject-4", "subject-5", "subject-6"};
    //
    ArrayList<String> timeList = new ArrayList<String>();
    ArrayList<String> subjectList = new ArrayList<String>();
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
    // Teacher Spinner....
    String teacherkey, selectedTeacher;
    Spinner teacherSpinner;
    Map<String, String> teacherMap = new LinkedHashMap<String, String>();
    ArrayList<String> teacherList;
    ArrayAdapter<String> teacherAdapter;
    // Button...
    Button select;
    //
    CardView cardView1;
    TextView noText;

    public TeacherwiseTimetableFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ClassBackGroundTask(getActivity(), TeacherwiseTimetableFragment.this).execute(Constants.schoolId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        teacherView =inflater.inflate(R.layout.teacher_wise_time_table_layout, container, false);
        tableLayout = (TableLayout) teacherView.findViewById(R.id.tableLayout);
        // class spinner....
        classSpinner = (Spinner) teacherView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), TeacherwiseTimetableFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) teacherView.findViewById(R.id.sectionSpinner);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                // Getting subjects....
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new AllTeacherBackTask(getActivity(), TeacherwiseTimetableFragment.this).execute(Constants.schoolId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // teacher spinner....
        teacherSpinner = (Spinner) teacherView.findViewById(R.id.teacherSpinner);
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
        // select button....
        select = (Button) teacherView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new TimeTableBackTask(getActivity(), TeacherwiseTimetableFragment.this).execute(sectionKey);
                } else {
                    Toast.makeText(mContext, "Please Select Section...!", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Tableview Headers....
        addHeaders();
        // Tableview Rows....
        addData();
        //
        noText = (TextView) teacherView.findViewById(R.id.noText);
        cardView1 = (CardView) teacherView.findViewById(R.id.cardView1);

        return teacherView;
    }
    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {

        /** Create a TableRow dynamically **/
        tr = new TableRow(mContext);
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 20);
        tr.setLayoutParams(lp);

        /*tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));*/

        /** Creating a TextView to add to the row **/
        TextView timeTV = new TextView(mContext);
        timeTV.setText("Time");
        timeTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        timeTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        timeTV.setPadding(1, 5, 1, 0);
        tr.addView(timeTV);  // Adding textView to tablerow.

        /** Creating another textview **/
        TextView monTV = new TextView(mContext);
        monTV.setText("Mon");
        monTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        monTV.setPadding(1, 5, 1, 0);
        monTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(monTV); // Adding textView to tablerow.

        /** Creating another textview **/
        TextView tuesTV = new TextView(mContext);
        tuesTV.setText("Tues");
        tuesTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        tuesTV.setPadding(1, 5, 1, 0);
        tuesTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(tuesTV); // Adding textView to tablerow.

        /** Creating another textview **/
        TextView wedTV = new TextView(mContext);
        wedTV.setText("Wed");
        wedTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        wedTV.setPadding(1, 5, 1, 0);
        wedTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(wedTV); // Adding textView to tablerow.

        /** Creating another textview **/
        TextView thurTV = new TextView(mContext);
        thurTV.setText("Thur");
        thurTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        thurTV.setPadding(1, 5, 1, 0);
        thurTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(thurTV); // Adding textView to tablerow.

        /** Creating another textview **/
        TextView friTV = new TextView(mContext);
        friTV.setText("Fri");
        friTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        friTV.setPadding(1, 5, 1, 0);
        friTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(friTV); // Adding textView to tablerow.

        /** Creating another textview **/
        TextView satTV = new TextView(mContext);
        satTV.setText("Sat");
        satTV.setTextColor(getResources().getColor(R.color.colorPrimary));
        satTV.setPadding(1, 5, 1, 0);
        satTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(satTV); // Adding textView to tablerow.

        /** Creating another textview **/
       /* TextView sunTV = new TextView(mContext);
        sunTV.setText("Sun");
        sunTV.setTextColor(Color.GRAY);
        sunTV.setPadding(1, 5, 1, 0);
        sunTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(sunTV); // Adding textView to tablerow.*/

        // Add the TableRow to the TableLayout
        tableLayout.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void addData() {
        for (int i = 0; i < subjectList.size(); i++) {
            /** Create a TableRow dynamically **/
            tr = new TableRow(getActivity());
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 10, 0, 10);
            tr.setLayoutParams(lp);

            timeTv = new TextView(mContext);
            timeTv.setText(time[i]);
            timeTv.setTextColor(getResources().getColor(R.color.colorAccent));
            //timeTv.setTextSize(15.0f);
            tr.addView(timeTv);

            sub1Tv = new TextView(mContext);
            sub1Tv.setText(subjectList.get(i));
            //sub1Tv.setTextSize(15.0f);
            tr.addView(sub1Tv);

            sub2Tv = new TextView(mContext);
            sub2Tv.setText(subjectList.get(i));
            //sub2Tv.setTextSize(15.0f);
            tr.addView(sub2Tv);

            sub3Tv = new TextView(mContext);
            sub3Tv.setText(subjectList.get(i));
            //sub3Tv.setTextSize(15.0f);
            tr.addView(sub3Tv);

            sub4Tv = new TextView(mContext);
            sub4Tv.setText(subjectList.get(i));
            //sub4Tv.setTextSize(15.0f);
            tr.addView(sub4Tv);

            sub5Tv = new TextView(mContext);
            sub5Tv.setText(subjectList.get(i));
            //sub5Tv.setTextSize(15.0f);
            tr.addView(sub5Tv);

            sub6Tv = new TextView(mContext);
            sub6Tv.setText(subjectList.get(i));
            //sub6Tv.setTextSize(15.0f);
            tr.addView(sub6Tv);

            // Add the TableRow to the TableLayout
            tableLayout.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
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
    public void timeTable(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            //Log.e("Time", result);
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("timetable");
            if (jsonArray.length() > 0){
                cardView1.setVisibility(View.VISIBLE);
                tableLayout.setVisibility(View.VISIBLE);
                noText.setVisibility(View.VISIBLE);
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject timeObject = jsonArray.getJSONObject(count);
                    subjectList.add(timeObject.getString("subject_id"));
                    count++;
                }
            } else {
                tableLayout.setVisibility(View.GONE);
                noText.setVisibility(View.VISIBLE);
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
}
