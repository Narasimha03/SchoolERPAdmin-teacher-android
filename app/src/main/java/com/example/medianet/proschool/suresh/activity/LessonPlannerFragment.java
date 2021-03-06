package com.example.medianet.proschool.suresh.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.example.medianet.proschool.AllChapterBackTask;
import com.example.medianet.proschool.AllSubjectBackTask;
import com.example.medianet.proschool.ChapterAdapter;
import com.example.medianet.proschool.Chapters;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.suresh.examination.AddBulkMarksBackTask;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by suresh on 11/10/2017.
 */

/**
 * Created by JANI on 16-06-2017.
 */

public class LessonPlannerFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        AllSubjectBackTask.OnSubjectResponse, AllChapterBackTask.ChapterResponse  {

    View chapterView;
    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout chapter_layout;
    TextView textChapterCount;
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
    String subjectkey = "";
    String selectedSubject;
    Spinner subjectSpinner;
    Map<String, String> subjectMap = new LinkedHashMap<String, String>();
    ArrayList<String> subjectList;
    ArrayAdapter<String> subjectAdapter;
    //
    FloatingActionButton select;
    //
    TextView noRecordsFound;

    EditText editDescription, editNoTopics, editChapterName, editChapterCode;
    //
    Button addChapter;
    //
    RecyclerView recycler_view;
    ArrayList<Chapters> listChapters = new ArrayList<Chapters>();
    LessionPlannerAdapter chapterAdapter;
    FrameLayout frameLayout;
    LinearLayout fabLayout1;
    FloatingActionButton fab;
    View fabBGLayout;
    Context mContext;
    String schoolId;
    boolean isFABOpen = false;
    String role;

    Button submitMarks;
    public LessonPlannerFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        chapterView = inflater.inflate(R.layout.lesson_planner_layout, container, false);
        mContext = getActivity();
        sliding_layout = (SlidingUpPanelLayout) chapterView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");

        new ClassBackGroundTask(getActivity(), LessonPlannerFragment.this).execute(schoolId);


        /*fab = (FloatingActionButton) chapterView.findViewById(R.id.fab);
       *//*fab1 = (FloatingActionButton) subjectView.findViewById(R.id.fab1);
        fab2= (FloatingActionButton) subjectView.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) subjectView.findViewById(R.id.fab3);*//*
        frameLayout=(FrameLayout)chapterView.findViewById(R.id.frameLayout);
        fabBGLayout=chapterView.findViewById(R.id.fabBGLayout);
        fabLayout1= (LinearLayout) chapterView.findViewById(R.id.fabLayout1);*/


        /*fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddChapterFragment addChapterFragment=new AddChapterFragment();
                setFragment(addChapterFragment);

            }
        });*/
        /*if (role.equals("admin")) {
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
        }*/

        /*fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                //animateFAB();
            }
        });*/


        //  noRecordsFound=(TextView)chapterView.findViewById(R.id.no_records_found);

        chapter_layout = (LinearLayout) chapterView.findViewById(R.id.chapter_layout);
        textChapterCount = (TextView) chapterView.findViewById(R.id.textChapterCount);
        // class spinner....
        classSpinner = (Spinner) chapterView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), LessonPlannerFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) chapterView.findViewById(R.id.sectionSpinner);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                // Getting subjects....
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new AllSubjectBackTask(getActivity(), LessonPlannerFragment.this).execute(sectionKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // subject spinner...
        subjectSpinner = (Spinner) chapterView.findViewById(R.id.subjectSpinner);
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
        //
        select = chapterView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listChapters.clear();
                if (subjectkey.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Select Subject...!", Toast.LENGTH_LONG).show();
                } else {
                         new AllChapterBackTask(getActivity(), LessonPlannerFragment.this).execute(subjectkey);
                }
            }
        });



       submitMarks = (Button) chapterView.findViewById(R.id.submitMarks);
        submitMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] escrito = chapterAdapter.getEtCompTopics();

                 System.out.println("arr1: " + Arrays.toString(escrito));

                String[] marks = chapterAdapter.getStChapterId();
                System.out.println("arr2: " + Arrays.toString(marks));


                ArrayList<String> arraylist1 = new ArrayList<>();
                Collections.addAll(arraylist1, escrito);

                ArrayList<String> studentSend = new ArrayList<>();
                Collections.addAll(studentSend, marks);
                JSONArray jsonArray = new JSONArray();
                JSONObject cartItemsObjedct;


              //if (!arraylist1.contains(null)||!arraylist1.isEmpty()) {

                  int p = 0;
                  for (Iterator<String> it = arraylist1.iterator(); it.hasNext(); p++) {
                      String s = it.next();
                      //  System.out.println(p + ": " + s);
                      int q = 0;
                      for (Iterator<String> stdIt = studentSend.iterator(); stdIt.hasNext(); q++) {

                          {
                              String stdOk = stdIt.next();
                              if (p == q) {

                                 /* if (s.contains(null) || s.contains("")) {
                                      Toast.makeText(getActivity(), "Nothing Has Changed....!", Toast.LENGTH_SHORT).show();

                                  }*/

                                  if (s != null && !s.isEmpty()) {
                                      cartItemsObjedct = new JSONObject();
                                      try {


                                          cartItemsObjedct.putOpt("chapter_id",
                                                  stdOk);

                                          cartItemsObjedct.putOpt("completed_topics", s);
                                          jsonArray.put(cartItemsObjedct);
                                      } catch (JSONException e) {
                                          e.printStackTrace();
                                      }

                                      JSONObject singleObject = new JSONObject();
                                      try {
                                          singleObject.put("chapters_completed", jsonArray);
                                      } catch (JSONException e) {
                                          e.printStackTrace();
                                      }
                                      String str = singleObject.toString();
                                      System.out.println("data marks:" + str);

                                      //  if (examScheduleKey != null && !examScheduleKey.isEmpty()) {


                                      if (singleObject.length() > 0) {
                                          AddBulkChapterBackTask addBulkChapterBackTask = new AddBulkChapterBackTask(mContext);

                                          addBulkChapterBackTask.execute(String.valueOf(singleObject), subjectkey);

                                      }


                                      //  Toast.makeText(getActivity(), "Please Select Schedule....!", Toast.LENGTH_SHORT).show();
                                  }
                                  /*else {
                                      Toast.makeText(getActivity(), "Please Fill Atleast One Student Marks....!", Toast.LENGTH_SHORT).show();

                                  }*/


                              }


                          }

                      }

                  }


               // }

            }


        });


        recycler_view = (RecyclerView) chapterView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Chapters");

        return chapterView;
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


  /*  @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editChapterName = (EditText) chapterView.findViewById(R.id.editChapterName);
        editChapterCode = (EditText) chapterView.findViewById(R.id.editChapterCode);
        editNoTopics = (EditText) chapterView.findViewById(R.id.editNoTopics);
        editDescription = (EditText) chapterView.findViewById(R.id.editDescription);
        addChapter = (Button) chapterView.findViewById(R.id.addChapter);

        addChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject saveObject = new JSONObject();
                try {
                    saveObject.put("title", editChapterName.getText().toString());
                    saveObject.put("chapter_code", editChapterCode.getText().toString());
                    saveObject.put("no_of_topics", editNoTopics.getText().toString());
                    saveObject.put("description", editDescription.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AddChapterBackTask addChapterBackTask = new AddChapterBackTask(getActivity());
                addChapterBackTask.execute(String.valueOf(saveObject), subjectkey);
            }
        });
    }*/

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
                subjectList = new ArrayList<>(subjectMap.values());
                //
                subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectList);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectSpinner.setAdapter(subjectAdapter);
                subjectAdapter.notifyDataSetChanged();
            }
        }
    }


   @Override
    public void ChapterResponse(String response) throws JSONException {
       if (response != null && !response.isEmpty()) {
           listChapters.clear();
           JSONObject jsonObject = new JSONObject(response);
           System.out.println("chapters data in bulk-->:" + jsonObject);

           JSONArray jsonArray = jsonObject.getJSONArray("chapters");
           System.out.println("chapters" + jsonArray);
           if (jsonArray.length() > 0) {

               int count = 0;
               while (count < jsonArray.length()) {
                   JSONObject chapObject = jsonArray.getJSONObject(count);
                   Chapters chapters = new Chapters(chapObject.getString("_id"), chapObject.getString("lession_id"), selectedSubject,
                           chapObject.getString("title"), chapObject.getString("chapter_code"), chapObject.getString("no_of_topics"),
                           chapObject.getString("completed_topics"), chapObject.getString("status"));
                   listChapters.add(chapters);
                   count++;
               }
               //Count....
               String taskCount = "Chapters (" + String.valueOf(listChapters.size()) + ")";
               textChapterCount.setText(taskCount);
               // textChapterCount.setVisibility(View.VISIBLE);
               recycler_view.setVisibility(View.VISIBLE);

               //
               chapterAdapter = new LessionPlannerAdapter(getActivity(), listChapters);
               recycler_view.setAdapter(chapterAdapter);
               //noRecordsFound.setVisibility(View.GONE);
               LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
               recycler_view.setLayoutManager(linearLayoutManager);
               chapterAdapter.notifyDataSetChanged();
               //
               sliding_layout.setParallaxOffset(0);
               sliding_layout.setPanelHeight(120);
           } else {
               sliding_layout.setParallaxOffset(0);
               sliding_layout.setPanelHeight(0);
               // String taskCount = "Chapters";
               //  textChapterCount.setText(taskCount);
               //  recycler_view.setVisibility(View.GONE);
               // noRecordsFound.setVisibility(View.VISIBLE);
               // noRecordsFound.setText("No Records Found");
               Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_SHORT).show();
               //recycler_view.setVisibility(View.GONE);
           }
       }
   }
    }
    /*
    "{
              "{
  ""SCH-9273-CL-2-SEC-1-SUB-2"": [
    {
      ""_id"": ""592d161816b61d077cc5351b"",
      ""lesson_id"": ""SCH-9273-CL-2-SEC-1-SUB-2-LES-3"",
      ""subject_id"": ""SCH-9273-CL-2-SEC-1-SUB-2"",
      ""title"": ""Test title"",
      ""code"": ""123456789"",
      ""no_of_topics"": ""5"",
      ""description"": ""this is test description"",
      ""status"": 1
    }
  ]
}"
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

