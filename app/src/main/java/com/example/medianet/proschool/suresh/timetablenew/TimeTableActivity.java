package com.example.medianet.proschool.suresh.timetablenew;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.ExamEvaluationFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.admindashboard.AdminStudentAttendanceClass;
import com.example.medianet.proschool.suresh.timetable.SessionTimingsBackGroundTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class TimeTableActivity extends AppCompatActivity implements ClassAdapter.OnItemClick, TimeTableBackGroundTask.OnTimeTableResponse,ClassBackGroundTask.OnClassResponse,SessionTimingsBackGroundTask.OnTimeResponse {
    // Context
    Context mContext = this;
    // Class RecyclerView
    String classResponse;
    LinkedHashMap<String, String> classMap = new LinkedHashMap<String, String>();
    RecyclerView classRecyclerView;
    ClassAdapter classAdapter;
    LinearLayoutManager linearLayoutManager;
    // Timings
    String timeResponse;
    LinearLayout timingsLayout;
    LinearLayout timeTableLayout;
    LinkedHashMap<String, String> listTime = new LinkedHashMap<String, String>();
    String sectionName;
    String schoolId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        // Class RecyclerView
        classRecyclerView = (RecyclerView) findViewById(R.id.classRecyclerView);
        // Timings Layout
        timingsLayout = (LinearLayout) findViewById(R.id.timingsLayout);
        timeTableLayout = (LinearLayout) findViewById(R.id.timeTableLayout);


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        Log.e("schoolId dashBaord", schoolId);

       // Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "Handlee-Regular.ttf");

        new ClassBackGroundTask(this, TimeTableActivity.this).execute(schoolId);
        new SessionTimingsBackGroundTask(this, TimeTableActivity.this).execute(schoolId);


    }

    /*@Override
    public void OnClick(int position) {
        timeTableLayout.removeAllViews();
        String classId = classMap.keySet().toArray()[position].toString();
        System.out.println("classId"+classId);
        new TimeTableBackGroundTask(mContext, TimeTableActivity.this).execute("3", classId);
    }*/

    @Override
    public void OnClick(int position) {
        timeTableLayout.removeAllViews();
        String classId = classMap.keySet().toArray()[position].toString();
        System.out.println("classId"+classId);
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("day of Week" + dayOfWeek);
        new TimeTableBackGroundTask(mContext, TimeTableActivity.this).execute(String.valueOf(dayOfWeek), classId);
    }

    @Override
    public void onTimeTableResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            Log.e("Resp", response);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("timetable");
            for (int i = 0; i < jsonArray.length(); i++) {
                // Linear Layout
                LinearLayout linearLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(15, 0, 15, 0);
                linearLayout.setBackgroundResource(R.color.white);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(layoutParams);
                // JSONObject
                JSONObject sectionObject = jsonArray.getJSONObject(i);
                // Section
                sectionName = "Section " + sectionObject.getString("sectionName");
                TextView sectionTV = new TextView(mContext);
                sectionTV.setText(sectionName);
                sectionTV.setTextSize(18);
                sectionTV.setGravity(1);
                sectionTV.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Handlee-Regular.ttf"));
                sectionTV.setPadding(20,0,20,0);
                sectionTV.setTypeface(Typeface.DEFAULT_BOLD);
                // Adding section name to linear layout
                linearLayout.addView(sectionTV);
                // TimeTable
                JSONArray timeTableArray = sectionObject.getJSONArray("timetableData");
                for (int j = 0; j < timeTableArray.length(); j++) {
                    JSONObject timeTableObject = timeTableArray.getJSONObject(j);
                    // Subject
                    TextView subjectTV = new TextView(mContext);
                    subjectTV.setText(timeTableObject.getString("subject"));
                    subjectTV.setPadding(10, 20, 10, 20);
                    subjectTV.setTextSize(15);
                    subjectTV.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Handlee-Regular.ttf"));
                    subjectTV.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
                    // Adding subject name to linear layout
                    linearLayout.addView(subjectTV);
                    // View
                    View view = new View(mContext);
                    LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                    view.setLayoutParams(viewParams);
                    view.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
                    // Adding view to linear layout
                    linearLayout.addView(view);
                }
                timeTableLayout.addView(linearLayout);
            }
        }
    }

    @Override
    public void onClassResponse(String classResponse) throws JSONException {

        // Classes
      //  classResponse = getIntent().getExtras().getString("classes");
        if (classResponse != null && !classResponse.isEmpty()) {
            try {
                JSONObject classObject = new JSONObject(classResponse);
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
        }
        classAdapter = new ClassAdapter(mContext, classMap, TimeTableActivity.this);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        classRecyclerView.setLayoutManager(linearLayoutManager);
        classRecyclerView.setAdapter(classAdapter);
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTimeResponse(String response) throws JSONException {

        // Timings
     //   timeResponse = getIntent().getExtras().getString("timings");
        if (response != null && !response.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("session_timings");
                Log.e("session timings", String.valueOf(jsonArray));
                if (jsonArray.length() > 0) {
                    // TimeTable
                    TextView heading = new TextView(mContext);
                    heading.setText("");
                    heading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Handlee-Regular.ttf"));
                    heading.setTextSize(18);
                    //heading.setTypeface(Typeface.DEFAULT_BOLD);
                    timingsLayout.addView(heading);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject timeObject = jsonArray.getJSONObject(i);
                        // Timings
                        TextView timeView = new TextView(mContext);
                        String text = timeObject.getString("start_time") + " - " + timeObject.getString("end_time");
                        timeView.setText(text);
                        timeView.setPadding(10, 20, 10, 20);
                        timeView.setTextSize(15);
                        timeView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Handlee-Regular.ttf"));
                        timeView.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
                        timingsLayout.addView(timeView);
                        listTime.put(timeObject.getString("start_time"), timeObject.getString("end_time"));
                        // View
                        View view = new View(mContext);
                        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                        view.setLayoutParams(viewParams);
                        view.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
                        timingsLayout.addView(view);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
