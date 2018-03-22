package com.example.medianet.proschool.suresh.academics.lessiontracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;

import com.example.medianet.proschool.Subject;
import com.example.medianet.proschool.suresh.academics.lessiontracker.modelclass.Chapters;
import com.example.medianet.proschool.suresh.academics.lessiontracker.modelclass.Subjects;

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
 * Created by JANI on 17-06-2017.
 */

public class LessionTrackerFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,LessionTrackerBackTask.LessionTrackerGraphs {
    // StudentAttendanceGraphBackTask.AllStudentGraphs

    View examEvalView;
    String imageurl = null;
    //
    SlidingUpPanelLayout sliding_layout;
    //Student Layout
    LinearLayout exam_layout;
    TextView textExamCount;
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
    // Exam Schedule Spinner...
    String examScheduleKey, selectedExamSchedule;
    Spinner examScheduleSpinner;
    Map<String, String> examScheduleMap = new LinkedHashMap<String, String>();
    ArrayList<String> examScheduleList;
    ArrayAdapter<String> examScheduleAdapter;
    // Subject Spinner...
    String subjectkey, selectedSubject;
    Spinner subjectSpinner;
    Map<String, String> subjectMap = new LinkedHashMap<String, String>();
    ArrayList<String> subjectList;
    ArrayAdapter<String> subjectAdapter;
    // Paper Spinner.... studentSpinner
    String paperkey, selectedPaper;
    Spinner paperSpinner;
    Map<String, String> paperMap = new LinkedHashMap<String, String>();
    ArrayList<String> paperList;
    ArrayAdapter<String> paperAdapter;
    // Student Spinner...
    String studentkey, selectedStudent;
    Spinner studentSpinner;
    Map<String, String> studentMap = new LinkedHashMap<String, String>();
    ArrayList<String> studentList;
    ArrayAdapter<String> studentAdapter;
    //
    EditText editMarks, editPercent;
    //
    String conductkey, feeTypeKey, selectedConduct, selectedConduct1;
    Spinner conductSpinner, feeTypeSpinner;
    Map<String, String> conductMap = new LinkedHashMap<String, String>();
    Map<String, String> conductMap1 = new LinkedHashMap<String, String>();

    ArrayList<String> conductList, conductList1;
    ArrayAdapter<String> conductAdapter, conductAdapter1;
    //
    Button addMarks;

    FloatingActionButton select;
    //date
    EditText editDate;
    //
    RecyclerView recycler_view;
    ArrayList<Subjects> listMarksSubjectName = new ArrayList<Subjects>();
    ArrayList<Chapters> listChaptersName = new ArrayList<Chapters>();
    Subjects recievedUser;
    Chapters child;

    LessionTrackerAdapter lessionTrackerAdapter;
    Context mContext;
    String schoolId;
    Subjects subjects= new Subjects();

    TextView tvSubjectName, tvChapterName;
    RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public LessionTrackerFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  new AllAttendanceReportsMonthBackTask(getActivity(), AttendanceReportsByMonth.this).execute(Constants.schoolId);

        //  new AllExamSchBackTask(getActivity(), AllFeeCollectionFragment.this).execute(Constants.schoolId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        examEvalView = inflater.inflate(R.layout.all_lession_tracker_layout, container, false);
        mContext = getActivity();


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new ClassBackGroundTask(getActivity(), LessionTrackerFragment.this).execute(schoolId);
        //  tvSubjectName=examEvalView.findViewById(R.id.tvSubjectName);
        //  tvChapterName=examEvalView.findViewById(R.id.tvChapterName);

        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();*/


        sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(180);
        exam_layout = (LinearLayout) examEvalView.findViewById(R.id.exam_layout);
        textExamCount = (TextView) examEvalView.findViewById(R.id.textExamCount);
        // class spinner....
        classSpinner = (Spinner) examEvalView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), LessionTrackerFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) examEvalView.findViewById(R.id.sectionSpinner);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                // Getting subjects, Students....
              /*  if (sectionKey != null && !sectionKey.isEmpty()) {
                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                    new AllStudentsBackTask(getActivity(), StudentAttendanceGraphReportByMonth.this).execute(sectionKey);
                    //  new AllExamPaperBackTask(getActivity(), ExamEvaluationFragment.this).execute(examScheduleKey, sectionKey);

                } else {
                    Toast.makeText(getActivity(), "Please Select Section.", Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Conduct Spinner....
        conductSpinner = (Spinner) examEvalView.findViewById(R.id.conductSpinner);
        conductMap.put("", "-- select --");
        conductMap.put("1", "January");
        conductMap.put("2", "February");
        conductMap.put("3", "March");
        conductMap.put("4", "April");
        conductMap.put("5", "May");
        conductMap.put("6", "June");
        conductMap.put("7", "July");
        conductMap.put("8", "August");
        conductMap.put("9", "September");
        conductMap.put("10", "October");
        conductMap.put("11", "November");
        conductMap.put("12", "December");


        conductList = new ArrayList<>(conductMap.values());
        conductAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, conductList);
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


        //
        select = (FloatingActionButton) examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMarksSubjectName.clear();
                if (sectionKey != null) {
                    new LessionTrackerBackTask(getActivity(), LessionTrackerFragment.this).execute(sectionKey);
                } else {
                    Toast.makeText(getActivity(), "Please select STUDENT", Toast.LENGTH_LONG).show();
                }
            }
        });
        //
        recycler_view = (RecyclerView) examEvalView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        //    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Attebdance Reports");


        return examEvalView;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void lessionTrackerGraphs(String result) throws JSONException {


        if (result != null && !result.isEmpty()) {
            String subjectName = "", chapterName = "", noTopics = "", completedTopics = "", remTopics = "", chapterCode = "";

            ArrayList<Subjects> subjectsData =new ArrayList<>();
            ;
           // listMarksSubjectName.clear();
          //  listChaptersName.clear();
          //  subjectsData.clear();

            // JSONObject category = null;

            JSONObject jsonObject = new JSONObject(result);
            System.out.println("section report" + jsonObject);


            JSONArray jsonArray = jsonObject.getJSONArray("subjects");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject entryJson = jsonArray.getJSONObject(i);

                subjectName = entryJson.getString("subjectName");
                subjects.setSubjectName(subjectName);

              //  Log.e("subjectName Count first", subjectName);
              //  listMarksSubjectName.add(subjects);
                JSONArray taskArray = entryJson.getJSONArray("chapters");

                if (taskArray != null && taskArray.length() > 0) {

                    ArrayList<Chapters> list = new ArrayList<>();

                    for (int a = 0; a < taskArray.length(); a++) {
                        JSONObject parentObject = taskArray.getJSONObject(a);
                        Chapters chapters = new Chapters();

                        //  Log.d("tag", "chapter Name->" + parentObject.optString("chapter_code"));

                        chapterName = parentObject.getString("chapterName");
                        noTopics = parentObject.getString("no_of_topics");
                        completedTopics = parentObject.getString("completed_topics");
                        remTopics = parentObject.getString("remaining_topics");
                        chapterCode = parentObject.getString("chapter_code");

                        //  Log.e("chapter Count first", chapterCode);

                        chapters.setChapterName(chapterName);
                        chapters.setNo_of_topics(noTopics);
                        chapters.setCompleted_topics(completedTopics);
                        chapters.setRemaining_topics(remTopics);
                        chapters.setChapter_code(chapterCode);//  ChildItems childItems = new ChildItems();
                        list.add(chapters);
                        subjects.setChildList(list);

                    }

                }

                String data=     subjects.getSubjectName();
                System.out.println("Subjects Name start-->" + data);
                listMarksSubjectName.add(subjects);

            }


            lessionTrackerAdapter = new LessionTrackerAdapter(getActivity(), listMarksSubjectName);
            recycler_view.setAdapter(lessionTrackerAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recycler_view.setLayoutManager(linearLayoutManager);
            lessionTrackerAdapter.notifyDataSetChanged();
            recycler_view.destroyDrawingCache();
            sliding_layout.setParallaxOffset(0);
            sliding_layout.setPanelHeight(120);

        }
    }
}




           /* String data=     subjects.getSubjectName();
            System.out.println("Subjects Name start-->" + data);

            for (int s = 0; s < subjectsData.size(); s++) {
                String subName=    subjectsData.get(s).setSubjectName(subjectName);
                System.out.println("Subjects Name-->" + subName);


            }*/





            // for()
            //     {
            //  System.out.println("Subjects Data Array"+subjectsData.get());


            //  }

                      






                       /*   for(int k = 0; k < subjectsData.size(); k++) {
                              System.out.print("another"+subjectsData.get(i));
                          }*/
                      /*    ArrayList<Chapters> chapters=new ArrayList<Chapters>();
                          chapters=subjects.getChildList();
                          System.out.println("chapters Array"+chapters);*/


            //   Subjects mLog = new Subjects();
            //   mLog.setSubjectName(subjectName);
            // mLog.setDescription(description);
            // myList.add(mLog);
                        /*  listMarksSubjectName.add(subjects);
                          mRecyclerAdapter.notifyDataSetChanged()(myList);
                          etTitle.setText("");
                          etDescription.setText("");*/



                 /*       recycler_view.setVisibility(View.VISIBLE);

                         lessionTrackerAdapter = new LessionTrackerAdapter(getActivity(), listMarksSubjectName);
                          recycler_view.setAdapter(lessionTrackerAdapter);
                          LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                          recycler_view.setLayoutManager(linearLayoutManager);
                          lessionTrackerAdapter.notifyDataSetChanged();
                          recycler_view.destroyDrawingCache();
                          sliding_layout.setParallaxOffset(0);
                          sliding_layout.setPanelHeight(120);*/


            //   String taskCount = "Chapter Records (" + String.valueOf(listMarks.size()) + ")";
            //  textExamCount.setText(taskCount);
            //

/*
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int cnt = 0; cnt < jsonArray.length(); cnt++) {
                    JSONObject jsonObj1 = null;
                    jsonObj1 = jsonArray.getJSONObject(cnt);
                    recievedUser = new Subjects();

                    subjectName = jsonObj1.getString("subjectName");
                    recievedUser.setSubjectName(subjectName);
                    Log.e("subjectName Count first", subjectName);
                    Log.d("tag", "jsonObj subjectName->" + jsonObj1.optString("subjectName"));
                  //  JSONArray jsonArrSubCat = new JSONArray(jsonObject.get("chapters"));
                    JSONArray jsonArrSubCat = jsonObj1.getJSONArray("chapters");

                    if (jsonArrSubCat != null && jsonArrSubCat.length() > 0) {

                        for (int subCnt = 0; subCnt < jsonArrSubCat.length(); subCnt++) {
                            JSONObject parentObject = jsonArrSubCat.getJSONObject(subCnt);
                            child = new Chapters();

                            Log.d("tag", "chapter Name->" + parentObject.optString("chapter_code"));

                            chapterName = parentObject.getString("chapterName");
                            noTopics = parentObject.getString("no_of_topics");
                            completedTopics = parentObject.getString("completed_topics");
                            remTopics = parentObject.getString("remaining_topics");
                            chapterCode = parentObject.getString("chapter_code");
                            child.setChapterName(chapterName);
                            child.setNo_of_topics(noTopics);
                         //   child.setCompletedTopics(completedTopics);
                          //  child.setRemTopics(remTopics);
                            child.setChapter_code(chapterCode);
                            listChaptersName.add(child);
                        }

                    }
                    listMarksSubjectName.add(recievedUser);

                    //   String taskCount = "Chapter Records (" + String.valueOf(listMarks.size()) + ")";
                    //  textExamCount.setText(taskCount);
                    //
                    recycler_view.setVisibility(View.VISIBLE);

                    lessionTrackerAdapter = new LessionTrackerAdapter(getActivity(), listMarksSubjectName,listChaptersName);
                    recycler_view.setAdapter(lessionTrackerAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recycler_view.setLayoutManager(linearLayoutManager);
                    lessionTrackerAdapter.notifyDataSetChanged();
                    recycler_view.destroyDrawingCache();
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);
                }*/
         //   }






              /*    for (int i = 0; i < jsonArray.length(); i++) {
                 JSONObject     jsonObject1 = jsonArray.getJSONObject(i);
                      recievedUser = new LessionTrackerModel();
                     subjectName= jsonObject1.getString("subjectName");
                     Log.e("SubjectName-->",subjectName);
                      recievedUser.setSubjectName(subjectName);


                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                          categories = new JSONArray(jsonObject.get("chapters"));
                          //  JSONArray categories = jsonObject.getJSONArray("chapters");


                          for (int j = 0; j < categories.length(); j++) {
                              //     category = category.getJSONobject(j);
                              JSONObject category = categories.getJSONObject(i);
                              child = new LessionTrackerModel();
                              chapterName = category.getString("chapterName");
                              noTopics = category.getString("no_of_topics");
                              completedTopics = category.getString("completed_topics");
                              remTopics = category.getString("remaining_topics");
                              chapterCode = category.getString("chapter_code");
                              Log.e("Chapter codes", chapterCode);

                              child.setChapterName(chapterName);
                              child.setNoTopics(noTopics);
                              child.setCompletedTopics(completedTopics);
                              child.setRemTopics(remTopics);
                              child.setChapterCode(chapterCode);

                      *//*    noTopics = category.getString("no_of_topics");
                          completedTopics = category.getString("completed_topics");
                          remTopics = category.getString("remaining_topics");
                          chapterCode = category.getString("chapter_code");*//*


                              listChaptersName.add(child);
                          }
                      }

                      listMarksSubjectName.add(recievedUser);
                  }*/



/*

          @Override
          public void lessionTrackerGraphs(String result) throws JSONException {

              if (result != null && !result.isEmpty()) {
                  String  subjectName = "",chapterName="",noTopics= "",completedTopics= "",remTopics= "",chapterCode= "";





                  listMarksSubjectName.clear();


                  JSONObject jsonObject = new JSONObject(result);
                  System.out.println("section report"+jsonObject);
                  JSONArray jsonArray = jsonObject.getJSONArray("subjects");

                  System.out.println("all chapters:"+jsonArray);

                  if (jsonArray != null && jsonArray.length() > 0) {
                      for (int cnt = 0; cnt < jsonArray.length(); cnt++) {
                          JSONObject jsonObj = null;
                          jsonObj = jsonArray.getJSONObject(cnt);
                          subjectName=jsonObj.getString("subjectName");
                          lessionTrackerModel.setSubjectName(subjectName);
                          Log.e("subjectName Count first",subjectName);
                          Log.d("tag", "jsonObj subjectName->" + jsonObj.optString("subjectName"));
                          JSONArray jsonArrSubCat = jsonObj.getJSONArray("chapters");
                          if (jsonArrSubCat != null && jsonArrSubCat.length() > 0) {

                              for (int subCnt = 0; subCnt < jsonArrSubCat.length(); subCnt++) {
                                  JSONObject parentObject = jsonArrSubCat.getJSONObject(subCnt);
                                  Log.d("tag", "chapter Name->" + parentObject.optString("chapter_code"));

                                  chapterName=parentObject.getString("chapterName");
                                  noTopics = parentObject.getString("no_of_topics");
                                  completedTopics = parentObject.getString("completed_topics");
                                  remTopics = parentObject.getString("remaining_topics");
                                  chapterCode = parentObject.getString("chapter_code");
                                  lessionTrackerModel.setChapterName(chapterName);
                                  lessionTrackerModel.setNoTopics(noTopics);
                                  lessionTrackerModel.setCompletedTopics(completedTopics);
                                  lessionTrackerModel.setRemTopics(remTopics);
                                  lessionTrackerModel.setChapterCode(chapterCode);



                                 */
/*  studentGraphModel=new LessionTrackerModel(subjectName, chapterName,noTopics,completedTopics,remTopics,chapterCode);
                                  listMarks.add(studentGraphModel);

                                  String taskCount = "Chapter Records (" + String.valueOf(listMarks.size()) + ")";
                                  textExamCount.setText(taskCount);
                                  //
                                  recycler_view.setVisibility(View.VISIBLE);

                                  lessionTrackerAdapter = new LessionTrackerAdapter(getActivity(), listMarks);
                                  recycler_view.setAdapter(lessionTrackerAdapter);
                                  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                  recycler_view.setLayoutManager(linearLayoutManager);
                                  lessionTrackerAdapter.notifyDataSetChanged();
                                  recycler_view.destroyDrawingCache();
                                  sliding_layout.setParallaxOffset(0);
                                  sliding_layout.setPanelHeight(120);*//*


                              }

                              listMarksSubjectName.add(lessionTrackerModel);

                              String taskCount = "Chapter Records (" + String.valueOf(listMarksSubjectName.size()) + ")";
                              textExamCount.setText(taskCount);
                              //
                              recycler_view.setVisibility(View.VISIBLE);

                              lessionTrackerAdapter = new LessionTrackerAdapter(getActivity(), listMarksSubjectName);
                              recycler_view.setAdapter(lessionTrackerAdapter);
                              LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                              recycler_view.setLayoutManager(linearLayoutManager);
                              lessionTrackerAdapter.notifyDataSetChanged();
                              recycler_view.destroyDrawingCache();
                              sliding_layout.setParallaxOffset(0);
                              sliding_layout.setPanelHeight(120);



                              // Log.d("tag", "chapterCode loop->" +chapterCode);
                            //  Log.d("tag", "subName loop->" +chapterCode);

                          }










                      }
*/



              //    }


                 // JSONObject subjects=jsonArray.getJSONObject(0);




                 /* if (jsonArray.length() > 0) {
                      int count = 0;
                      while (count < jsonArray.length()) {
                          JSONObject markObject = jsonArray.getJSONObject(count);

                 *//*   stdName = markObject.getString("first_name") + markObject.getString("last_name");
                    date=markObject.getString("date");
                    String firstTen=date.substring(0,10);*//*


                          subjectName=markObject.getString("subjectName");


                          Log.e("subjectName Count att",subjectName);


                          if (!markObject.isNull("chapters")) {
                              JSONArray parentArray = markObject.getJSONArray("chapters");
                              int p = 0;
                              while (p < parentArray.length()) {




                                  JSONObject parentObject = parentArray.getJSONObject(p);
                                  chapterName=parentObject.getString("chapterName");
                                  noTopics = parentObject.getString("no_of_topics");
                                  completedTopics = parentObject.getString("completed_topics");
                                  remTopics = parentObject.getString("remaining_topics");
                                  chapterCode = parentObject.getString("chapter_code");

                                  Log.e("ChaptersData",chapterName+" "+completedTopics+" "+remTopics+" "+subjectName);




*//*
                                  // new StudentProfileBackTask(getActivity(), StudentAttendanceGraphReportByMonth.this).execute(studentId);

                                  studentName=parentObject.getString("Name");
                                  Log.e("StudentAttendanceReport",studentId+" "+studentName);

                                  if (!parentObject.isNull("attendance")) {
                                      JSONObject attendanceObject = parentObject.getJSONObject("attendance");
                                      sPresent = attendanceObject.getString("present");
                                      sAbsent = attendanceObject.getString("absent");
                                      sOnleave = attendanceObject.getString("onLeave");
                                      sPresentPercent = attendanceObject.getString("presentPercent");
                                      sAbsentPercent = attendanceObject.getString("absentPercent");
                                      sOnLeavePercent = attendanceObject.getString("onLeavePercent");
                                  }



                                  Log.e("student graph",studentName+" "+sPresent);
                                  Log.e("Image url",studentName+" "+imageurl);*//*


                                  LessionTracker lessionTracker=new LessionTracker();
                                lessionTracker.getSubjects();

                                 LessionTrackerModel studentGraphModel=new LessionTrackerModel(subjectName, chapterName,noTopics,completedTopics,remTopics,chapterCode);
                                  listMarks.add(studentGraphModel);

                                  p++;

                                  String taskCount = "Chapter Records (" + String.valueOf(listMarks.size()) + ")";
                                  textExamCount.setText(taskCount);
                                  //
                                  recycler_view.setVisibility(View.VISIBLE);

                                  lessionTrackerAdapter = new LessionTrackerAdapter(getActivity(), listMarks);
                                  recycler_view.setAdapter(lessionTrackerAdapter);
                                  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                  recycler_view.setLayoutManager(linearLayoutManager);
                                  lessionTrackerAdapter.notifyDataSetChanged();
                                  recycler_view.destroyDrawingCache();



                                  //
                                  sliding_layout.setParallaxOffset(0);
                                  sliding_layout.setPanelHeight(120);
                              }




                          }

                          count++;
                      }
                      //Count....

                  } else {
                      sliding_layout.setParallaxOffset(0);
                      sliding_layout.setPanelHeight(0);
                      recycler_view.setVisibility(View.GONE);
                      Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
                  }*/







/*



    @Override
    public void allStudentGraphs(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            String studentId = "",studentName = "",totalCount = "",totalPresent = "",totalAbsent = "",totalLeave = "",sPresent = "",sAbsent = "",sOnleave = "",sPresentPercent = "",sAbsentPercent = "",sOnLeavePercent = "", imageDisplay = "", employeeId = "";
            String imageName="";
            String imageurlData="";

            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("section report"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("sectionMonthlyAttendence");


            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("all attendance:"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                 */
/*   stdName = markObject.getString("first_name") + markObject.getString("last_name");
                    date=markObject.getString("date");
                    String firstTen=date.substring(0,10);*//*



                    totalCount=markObject.getString("totalCount");
                    totalPresent=markObject.getString("totalPresent");
                    totalAbsent=markObject.getString("totaAbsent");
                    totalLeave=markObject.getString("totalOnLeave");

                    Log.e("student Count att",totalCount+" "+totalPresent);


                    if (!markObject.isNull("StudentAttendanceReport")) {
                        JSONArray parentArray = markObject.getJSONArray("StudentAttendanceReport");
                        int p = 0;
                        while (p < parentArray.length()) {
                            JSONObject parentObject = parentArray.getJSONObject(p);
                            studentId=parentObject.getString("studentId");
                            imageName = parentObject.getString("image");
                            imageurlData = Constants.singleImage + imageName;



                            // new StudentProfileBackTask(getActivity(), StudentAttendanceGraphReportByMonth.this).execute(studentId);

                            studentName=parentObject.getString("Name");
                            Log.e("StudentAttendanceReport",studentId+" "+studentName);

                            if (!parentObject.isNull("attendance")) {
                                JSONObject attendanceObject = parentObject.getJSONObject("attendance");
                                sPresent = attendanceObject.getString("present");
                                sAbsent = attendanceObject.getString("absent");
                                sOnleave = attendanceObject.getString("onLeave");
                                sPresentPercent = attendanceObject.getString("presentPercent");
                                sAbsentPercent = attendanceObject.getString("absentPercent");
                                sOnLeavePercent = attendanceObject.getString("onLeavePercent");
                            }



                            Log.e("student graph",studentName+" "+sPresent);
                            Log.e("Image url",studentName+" "+imageurl);


                            StudentGraphModel studentGraphModel=new StudentGraphModel(studentId, employeeId,studentName,totalCount,totalPresent,totalAbsent,totalLeave,sPresent,sAbsent,sOnleave,sPresentPercent,sAbsentPercent,sOnLeavePercent, imageurlData);
                            listMarks.add(studentGraphModel);
                            p++;
                        }


                    }






                 */
/*   String a;
                    String b;
                    String c;
                    int total;
                    String totalValue;
                    a = markObject.getString("fee_amount");
                    b = markObject.getString("fine");
                    c = markObject.getString("discount");
                    int amount=Integer.parseInt(a);
                    int fine=Integer.parseInt(b);
                    int discount=Integer.parseInt(c);
                    total=(amount+fine)-discount;
                    totalValue= String.valueOf(total);
                    System.out.println("total"+totalValue);*//*

                    //paper_result_id
                  */
/*  FeeCollection evalMarks = new FeeCollection(markObject.getString("_id"), markObject.getString("exam_paper_id"),
                            selectedPaper, selectedStudent, markObject.getString("marks"),
                            markObject.getString("percentage"), markObject.getString("conduct"), markObject.getString("paper_name"),
                            markObject.getString("first_name"), markObject.getString("examschedule_name"), selectedExamSchedule, selectedSubject);*//*


                    count++;
                }
                //Count....
                String taskCount = "Attendance Records (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);

                allStudentMonthGraphAdapter = new AllStudentMonthGraphAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(allStudentMonthGraphAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                allStudentMonthGraphAdapter.notifyDataSetChanged();
                recycler_view.destroyDrawingCache();
                //
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
*/



 /*   public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }*/
    /*
    "{
  ""SCH-9273-EXM_SCH-3-SCH-9273-CL-2-SEC-1-SUB-1-EXM-5-SCH-9273-CL-2-STD-5"": [
    {
      ""_id"": ""592bf135501c1018082fd58e"",
      ""paper_result_id"": ""SCH-9273-EXM_SCH-3-SCH-9273-CL-2-SEC-1-SUB-1-EXM-5-SCH-9273-CL-2-STD-5-EVAL-3"",
      ""exam_paper_id"": ""SCH-9273-EXM_SCH-3-SCH-9273-CL-2-SEC-1-SUB-1-EXM-5"",
      ""student_id"": ""SCH-9273-CL-2-STD-5"",
      ""marks"": ""80"",
      ""percentage"": ""80"",
      ""conduct"": ""conduct"",
      ""comment"": ""test comment"",
      ""date"": ""2017-05-29T10:00:21.645Z"",
      ""status"": 1
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

