package com.example.medianet.proschool.suresh.academics;


import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AddSubjectBackTask;
import com.example.medianet.proschool.AllSubjectBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.EmployeeDetailsFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.StudentDetailsAdapter;
import com.example.medianet.proschool.StudentsDetails;
import com.example.medianet.proschool.Subject;
import com.example.medianet.proschool.SubjectAdapter;
import com.example.medianet.proschool.suresh.activity.AddChapterFragment;
import com.example.medianet.proschool.suresh.activity.AddSubjectFragment;
import com.example.medianet.proschool.suresh.activity.AllChapterFragment;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileTabsFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by suresh on 11/10/2017.
 */



public class SubjectsShowFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        AllSubjectBackTask.OnSubjectResponse,SubjectAdapter.ItemClickSubjectListener {

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
    Button addSubject;

    FloatingActionButton select;
    TextView noRecordsFound;

    FloatingActionButton fab, fab1, fab2, fab3;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    FrameLayout frameLayout;
    boolean isFABOpen = false;
    Context mContext;
    String schoolId;
    SharedPreferences sharedPreferences;
String role;

    public SubjectsShowFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        subjectView = inflater.inflate(R.layout.show_all_subject_layout_two, container, false);
        mContext = getActivity();
        sliding_layout = (SlidingUpPanelLayout) subjectView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");

        new ClassBackGroundTask(getActivity(), SubjectsShowFragment.this).execute(schoolId);

        sub_layout = (LinearLayout) subjectView.findViewById(R.id.emp_layout);
        textSubCount = (TextView) subjectView.findViewById(R.id.textEmpCount);


        noRecordsFound = (TextView) subjectView.findViewById(R.id.no_records_found);

        fabLayout1 = (LinearLayout) subjectView.findViewById(R.id.fabLayout1);
        /*fabLayout2= (LinearLayout) subjectView.findViewById(R.id.fabLayout2);
        fabLayout3= (LinearLayout) subjectView.findViewById(R.id.fabLayout3);*/
        fab = (FloatingActionButton) subjectView.findViewById(R.id.fab);
       /* fab1 = (FloatingActionButton) subjectView.findViewById(R.id.fab1);
        fab2= (FloatingActionButton) subjectView.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) subjectView.findViewById(R.id.fab3);*/
        frameLayout = (FrameLayout) subjectView.findViewById(R.id.frameLayout);
        fabBGLayout = subjectView.findViewById(R.id.fabBGLayout);


        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*AddSubjectFragment addSubjectFragment = new AddSubjectFragment();
                setFragment(addSubjectFragment);*/

                Intent i = new Intent(getActivity(),AddSubjectFragment.class);
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
                    new SectionBackGroundTask(getActivity(), SubjectsShowFragment.this).execute(classKey);
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

        select = subjectView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectList.clear();
                if (sectionKey.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_LONG).show();
                } else {
                    new AllSubjectBackTask(getActivity(), SubjectsShowFragment.this).execute(sectionKey);
                }
            }
        });

        // to set action bar title....
        //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Subjects");

        return subjectView;
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        //fabLayout2.setVisibility(View.VISIBLE);
        //fabLayout3.setVisibility(View.VISIBLE);
        // fabLayout4.setVisibility(View.VISIBLE);
        // fabLayout5.setVisibility(View.VISIBLE);
        //fabLayout6.setVisibility(View.VISIBLE);

        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        //fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        //fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
        //    fabLayout4.animate().translationY(-getResources().getDimension(R.dimen.standard_190));
        //  fabLayout5.animate().translationY(-getResources().getDimension(R.dimen.standard_235));
        //  fabLayout6.animate().translationY(-getResources().getDimension(R.dimen.standard_280));


    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        //fabLayout1.animate().translationY(0);
        //fabLayout2.animate().translationY(0);
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    //fabLayout2.setVisibility(View.GONE);
                    //fabLayout3.setVisibility(View.GONE);


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

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
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
            System.out.println("Subject Data"+jsonArray);
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
                recycler_view.setVisibility(View.VISIBLE);

                subjectAdapter = new SubjectAdapter(getActivity(), subjectList);
                recycler_view.setAdapter(subjectAdapter);
                subjectAdapter.setClickListener(this);

                //noRecordsFound.setVisibility(View.GONE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                subjectAdapter.notifyDataSetChanged();
                //Sliding Panel
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(120);
            } else {
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                // recycler_view.setVisibility(View.GONE);
                // String taskCount = "Subjects";
                // textSubCount.setText(taskCount);
                recycler_view.setVisibility(View.GONE);
                // noRecordsFound.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_SHORT).show();
                //noRecordsFound.setText("No Records Found");

            }
        }


    }

    @Override
    public void onClickSubjectName(View view, int position) {
        final Subject subject = subjectList.get(position);
        String subjectCode=subject.getCode();

        sharedPreferences = mContext.getSharedPreferences("subjectId", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.subjectId, subjectCode);
        editor.commit();
        startActivity(new Intent(mContext, ChaptersActivity.class));

       /* Intent intent = new Intent(mContext, StudentProfileTabsFragment.class);
        mContext.startActivity(intent);*/
        //   setFragment(studentProfileTabsFragment);
        //sendStudentId.sendData(studentName);

        Log.e("subject click",subjectCode);

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
    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }

    }*/
}