package com.example.medianet.proschool.suresh.timetable;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AddTimeTableFragment;
import com.example.medianet.proschool.AllEmpAttBackTask;
import com.example.medianet.proschool.AssignmentAdapter;
import com.example.medianet.proschool.Assignments;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.EmpAttendanceFragment;
import com.example.medianet.proschool.Harish.AddScheduleFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.TimeTableBackTask;
import com.example.medianet.proschool.suresh.admindashboard.AdminCurriculumAdapter;
import com.example.medianet.proschool.suresh.admindashboard.AdminCurriculumClass;
import com.example.medianet.proschool.suresh.admindashboard.AdminSectionsAdapter;
import com.example.medianet.proschool.suresh.timetablenew.TimeTableActivity;
import com.example.medianet.proschool.suresh.timetablenew.TimeTableBackGroundTask;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 30-06-2017.
 */

public class TimeTableClassSection extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
       TimeTableClassSectionResponse.OnTimeTableResponse,SessionTimingsBackGroundTask.OnTimeResponse {

    View viewTimetable;
    Context mContext;
    TableLayout tableLayout;
    SlidingUpPanelLayout sliding_layout;
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
    // Button...
    FloatingActionButton select;
    //
    CardView cardView1;
    TextView noText;
    FloatingActionButton fab;
    LinearLayout fabLayout1;
    View fabBGLayout;
    FrameLayout frameLayout;
    // FrameLayout fabBGLayout;
    boolean isFABOpen = false;

    //RecyclerView Time Table
    private List<ViewTimeClass> ttSectionsClassList = new ArrayList<>();
    private RecyclerView ttSectionsRecyclerView;
    private ViewTimeAdapter mttSectionAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    ViewTimeAdapter viewTimeAdapter;
    ArrayList<ViewTimeClass> timeTableList = new ArrayList<ViewTimeClass>();
    //List<ViewTimeClass> ttSectionsClassList;
    String role;

    // Timings
    String timeResponse;
    LinearLayout timingsLayout;
    LinearLayout timeTableLayout;
    LinkedHashMap<String, String> listTime = new LinkedHashMap<String, String>();
    String sectionName;
    String schoolId;
    RecyclerView classRecyclerView;

    public TimeTableClassSection() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
    //    classRecyclerView = (RecyclerView)viewTimetable. findViewById(R.id.classRecyclerView);
        // Timings Layout

        viewTimetable = inflater.inflate(R.layout.view_timetable_layout_two_section, container, false);
        tableLayout = (TableLayout) viewTimetable.findViewById(R.id.tableLayout);
        timingsLayout = (LinearLayout)viewTimetable. findViewById(R.id.timingsLayout);
        timeTableLayout = (LinearLayout)viewTimetable. findViewById(R.id.timeTableLayout);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        new ClassBackGroundTask(getActivity(), TimeTableClassSection.this).execute(schoolId);
        new SessionTimingsBackGroundTask(getActivity(), TimeTableClassSection.this).execute(schoolId);

        fabLayout1 = (LinearLayout) viewTimetable.findViewById(R.id.fabLayout1);
        fab = (FloatingActionButton) viewTimetable.findViewById(R.id.fab);
        frameLayout = (FrameLayout) viewTimetable.findViewById(R.id.frameLayout);
        fabBGLayout = viewTimetable.findViewById(R.id.fabBGLayout);
        sliding_layout = (SlidingUpPanelLayout) viewTimetable.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);

        // class spinner....
        classSpinner = (Spinner) viewTimetable.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), TimeTableClassSection.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        // section spinner....
        sectionSpinner = (Spinner) viewTimetable.findViewById(R.id.sectionSpinner);
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

        select = (FloatingActionButton) viewTimetable.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTableLayout.removeAllViews();
                /*String classId = classMap.keySet().toArray()[position].toString();
                System.out.println("classId"+classId);
                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                System.out.println("day of Week" + dayOfWeek);*/
                //new TimeTableBackGroundTask(mContext, TimeTableActivity.this).execute(String.valueOf(dayOfWeek), classId);


             //   listEmp.clear();
                new TimeTableClassSectionResponse(getActivity(), TimeTableClassSection.this).execute(sectionKey);
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(120);
            }
        });


        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  showFABMenu();
                /*AddTimeTableFragment addTimetableFragment = new AddTimeTableFragment();
                setFragment(addTimetableFragment);*/
                //   closeFABMenu();

                Intent i = new Intent(getActivity(),AddTimeTableFragment.class);
                startActivity(i);

                closeFABMenu();
            }


        });
        if (role.equals("admin")) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //    animateFAB();
                    if (!isFABOpen) {
                        showFABMenu();
                    } else {
                        closeFABMenu();
                    }
                }
            });
        }
        else  if (role.equals("teacher")) {
            fab.hide();
        }

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                //animateFAB();
            }
        });
        // select button....
        /*select = viewTimetable.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new TimeTableBackTask(getActivity(), ViewTimeTableClassFragment.this).execute(sectionKey);
                } else {
                    Toast.makeText(mContext, "Please Select Section...!", Toast.LENGTH_LONG).show();
                }
            }
        });*/
        // Tableview Headers....
        //   addHeaders();
        // Tableview Rows....
        //   addData();
        //
        noText = (TextView) viewTimetable.findViewById(R.id.noText);
        cardView1 = (CardView) viewTimetable.findViewById(R.id.cardView1);


        //Time Table RecyclerView


        /*View view;
        view = inflater.inflate(R.layout.view_time_table_recycler_layout, container, false);
*/
     /*   ttSectionsRecyclerView = viewTimetable.findViewById(R.id.view_time_table_recycler_two);
        ttSectionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        mttSectionAdapter = new ViewTimeAdapter(ttSectionsClassList);
        //RecyclerView.LayoutManager mttSectionsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false );
        ttSectionsRecyclerView.setLayoutManager(mLayoutManager);
        ttSectionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ttSectionsRecyclerView.setAdapter(mttSectionAdapter);*/
        /*adminSectionsRecyclerView.setAdapter(new AdminSectionsAdapter(adminSectionsClassList, new AdminCurriculumAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(AdminCurriculumClass item) {
                Toast.makeText(getApplicationContext(), item.getAdminCurClasses(), Toast.LENGTH_SHORT).show();
            }

        }));*/


       // preparettSectionsData();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Timetable");

        return viewTimetable;
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);

        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));


    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);


                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    //Time Table Data

    private void preparettSectionsData() {

        /*ViewTimeClass ttSectionsTotal = new ViewTimeClass("9:30 - 10:30", "10:30 - 11:30", "11:30 - 12:30", "12:30 - 1:00", "1:00 - 2:00", "2:00 - 3:00", "3:00 - 4:00", "4:00 - 5:00");
        ttSectionsClassList.add(ttSectionsTotal);*/

        ViewTimeClass  ttSectionsTotal = new ViewTimeClass("Monday", "Telugu", "Maths", "Chemistry", "Lunch", "Physics", "English", "Social");
        ttSectionsClassList.add(ttSectionsTotal);

        ttSectionsTotal = new ViewTimeClass("Tuesday", "Social", "Maths", "English", "Lunch", "Chemistry", "Telugu", "Physics");
        ttSectionsClassList.add(ttSectionsTotal);

        ttSectionsTotal = new ViewTimeClass("Wednesday", "Maths", "Social", "Chemistry", "Lunch", "English", "Physics", "Telugu");
        ttSectionsClassList.add(ttSectionsTotal);

        ttSectionsTotal = new ViewTimeClass("Thursday", "Social", "Telugu", "Physics", "Lunch", "English", "Telugu", "Chemistry");
        ttSectionsClassList.add(ttSectionsTotal);

        ttSectionsTotal = new ViewTimeClass("Friday", "Sports", "Telugu", "Biology", "Lunch", "English", "Telugu", "Chemistry");
        ttSectionsClassList.add(ttSectionsTotal);

        ttSectionsTotal = new ViewTimeClass("Saturday", "Biology", "Telugu", "Physics", "Lunch", "English", "Telugu", "Chemistry");
        ttSectionsClassList.add(ttSectionsTotal);

        mttSectionAdapter.notifyDataSetChanged();
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
    public void onTimeResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("session_timings");
                Log.e("session timings", String.valueOf(jsonArray));
                if (jsonArray.length() > 0) {
                    // TimeTable
                    TextView heading = new TextView(mContext);
                    heading.setText("");
                    heading.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Handlee-Regular.ttf"));
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
                        timeView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Handlee-Regular.ttf"));
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
                sectionName = sectionObject.getString("day").toUpperCase();
                TextView sectionTV = new TextView(mContext);
                sectionTV.setText(sectionName);
                sectionTV.setTextSize(18);
                sectionTV.setGravity(1);
                sectionTV.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Handlee-Regular.ttf"));
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
                    subjectTV.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Handlee-Regular.ttf"));
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



    /*
    "{
    ""timetable"": [
        {
            ""_id"": ""5928895127110e1fdc846a42"",
            ""timetable_id"": ""SCH-9273-CL-2-SEC-1-SEC-1"",
            ""section_id"": ""SCH-9273-CL-2-SEC-1"",
            ""day"": ""Monday"",
            ""start_time"": ""09:00"",
            ""end_time"": ""10:00"",
            ""room_no"": ""ROOM-1"",
            ""subject_id"": ""SCH-9273-CL-2-SEC-1-SUB-1"",
            ""status"": 1
        },
        {
            ""_id"": ""593e33ff8f6f4e0d04fc4350"",
            ""timetable_id"": ""SCH-9273-CL-2-SEC-1-SEC-2"",
            ""section_id"": ""SCH-9273-CL-2-SEC-1"",
            ""day"": ""Monday"",
            ""start_time"": ""09:00"",
            ""end_time"": ""10:00"",
            ""room_no"": ""ROOM-1"",
            ""subject_id"": ""SCH-9273-CL-2-SEC-1-SUB-2"",
            ""status"": 1
        }
    ]
}"
     */
}
