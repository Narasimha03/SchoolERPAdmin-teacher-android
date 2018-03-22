package com.example.medianet.proschool.suresh.examination;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.activity.AllStudentDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.medianet.proschool.Constants.studentId;

public class BulkEvaluationsActivitiy extends AppCompatActivity implements ClassBackGroundTask.OnClassResponse{
    // Context
    Context mContext = this;
    // Class RecyclerView
    String classResponse;
    LinkedHashMap<String, String> classMap = new LinkedHashMap<String, String>();
    RecyclerView classRecyclerView;
    BulkAdapter bulkAdapter;
    LinearLayoutManager linearLayoutManager;
    // Button
    Button submitBTN;
    String schoolId;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_upload);
        // Class RecyclerView
        classRecyclerView = (RecyclerView) findViewById(R.id.classRecyclerView);
        // Classes

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        Log.e("schoolId dashBaord", schoolId);

        new ClassBackGroundTask(mContext, BulkEvaluationsActivitiy.this).execute(schoolId);

        // submit button
        submitBTN = (Button) findViewById(R.id.submitBTN);
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Values", classMap.values().toString());
                if(classMap.values().toString()!= null && !classMap.values().toString().isEmpty()) {
                    System.out.println("arr1: " + Arrays.toString(new LinkedHashMap[]{classMap}));
                    String className = classMap.values().toString();
                    String data = classMap.values().toString();
                    Log.e("Values", String.valueOf(classMap.size()));


                    //  Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
                    Iterator itr = classMap.keySet().iterator();
                    while (itr.hasNext()) {
                        String key = itr.next().toString();
                        String value = classMap.get(key).toString();
                        System.out.println("data " + key + "=" + value);

                        if (value != null && !value.isEmpty()) {

                            JSONArray jsonArray = new JSONArray();

                            JSONObject cartItemsObjedct = new JSONObject();
                            try {


                                cartItemsObjedct.putOpt("student_id",
                                        classMap.keySet());

                                cartItemsObjedct.putOpt("marks", classMap.values());
                                jsonArray.put(cartItemsObjedct);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject singleObject = new JSONObject();
                            try {
                                singleObject.put("studentMarks", jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String str = singleObject.toString();
                            System.out.println("data all:" + str);


                        }

                        for (int i = 0; i < classMap.size(); i++) {

                            classMap.get(i);

                            JSONArray jsonArray = new JSONArray();

                            JSONObject cartItemsObjedct = new JSONObject();
                            try {


                                cartItemsObjedct.putOpt("student_id",
                                        classMap.keySet());

                                cartItemsObjedct.putOpt("marks", classMap.values());
                                jsonArray.put(cartItemsObjedct);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject singleObject = new JSONObject();
                            try {
                                singleObject.put("studentMarks", jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String str = singleObject.toString();
                            System.out.println("data all:" + str);

                        }
                    }

                }

            }
        });
    }

    @Override
    public void onClassResponse(String classResponse) throws JSONException {

       // classResponse = getIntent().getExtras().getString("classes");
        if (classResponse != null && !classResponse.isEmpty()) {
            try {
                JSONObject classObject = new JSONObject(classResponse);
                JSONArray classArray = classObject.getJSONArray("school_classes");
                int count = 0;
                while (count < classArray.length()) {
                    JSONObject cObject = classArray.getJSONObject(count);
                    classMap.put(cObject.getString("name"), cObject.getString("class_id"));
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        bulkAdapter = new BulkAdapter(mContext, classMap, new BulkAdapter.OnEditTextChanged() {
            @Override
            public void onTextChanged(String key, String value) {
                //classMap
                classMap.put(key, value);
                //classAdapter.notifyDataSetChanged();
            }
        });
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        classRecyclerView.setLayoutManager(linearLayoutManager);
        classRecyclerView.setAdapter(bulkAdapter);
      //  bulkAdapter.notifyDataSetChanged();

    }
}
