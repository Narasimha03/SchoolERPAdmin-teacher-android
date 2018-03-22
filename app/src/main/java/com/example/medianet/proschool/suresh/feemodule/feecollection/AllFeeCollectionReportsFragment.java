package com.example.medianet.proschool.suresh.feemodule.feecollection;

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

import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.suresh.feemodule.FeeAmountByFeeTypeBackTask;
import com.example.medianet.proschool.suresh.feemodule.FeeTypesByClassBackTask;
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

public class AllFeeCollectionReportsFragment extends Fragment implements  ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,FeeTypesByClassBackTask.FeeTypesByClassId,AllFeeCollectionReportsBackTask.AllFeeCollectionReports{

    View examEvalView;
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
    String conductkey,feeTypeKey, selectedConduct,selectedConduct1;
    Spinner conductSpinner,feeTypeSpinner;
    Map<String, String> conductMap = new LinkedHashMap<String, String>();
    Map<String, String> conductMap1 = new LinkedHashMap<String, String>();

    ArrayList<String> conductList,conductList1;
    ArrayAdapter<String> conductAdapter,conductAdapter1;
    //
    Button addMarks;
    FloatingActionButton select;
    //date
    EditText editDate;

    FloatingActionButton fab;

    LinearLayout fabLayout1;
    View fabBGLayout;
    FrameLayout frameLayout;
    boolean isFABOpen=false;
    //
    RecyclerView recycler_view;
    ArrayList<FeeCollectionReports> listMarks = new ArrayList<FeeCollectionReports>();
    FeeCollectionReportsAdapter feeCollectionReportsAdapter;
    Context mContext;
    String schoolId;
    String role;


    public AllFeeCollectionReportsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  new AllExamSchBackTask(getActivity(), AllFeeCollectionFragment.this).execute(Constants.schoolId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        examEvalView = inflater.inflate(R.layout.collect_fee_reports_layout, container, false);
       mContext=getActivity();


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        new ClassBackGroundTask(getActivity(), AllFeeCollectionReportsFragment.this).execute(schoolId);
        //new AllFeeTypeBackTask(getActivity(), AllFeeCollectionFragment.this).execute(schoolId);

        sliding_layout = (SlidingUpPanelLayout) examEvalView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        exam_layout = (LinearLayout) examEvalView.findViewById(R.id.std_layout);
        textExamCount = (TextView) examEvalView.findViewById(R.id.textStdCount);
        /*fabLayout1= (LinearLayout) examEvalView.findViewById(R.id.fabLayout1);

        fab = (FloatingActionButton) examEvalView.findViewById(R.id.fab);

        frameLayout=(FrameLayout)examEvalView.findViewById(R.id.frameLayout);
        fabBGLayout=examEvalView.findViewById(R.id.fabBGLayout);*/
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
                    new SectionBackGroundTask(getActivity(), AllFeeCollectionReportsFragment.this).execute(classKey);
                    new FeeTypesByClassBackTask(getActivity(),AllFeeCollectionReportsFragment.this).execute(classKey);

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
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                  //  new AllStudentsBackTask(getActivity(), AllFeeCollectionReportsFragment.this).execute(sectionKey);
                    //  new AllExamPaperBackTask(getActivity(), ExamEvaluationFragment.this).execute(examScheduleKey, sectionKey);

                } else {
                    Toast.makeText(getActivity(), "Please Select Section.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        // Student Spinner...
        feeTypeSpinner = (Spinner) examEvalView.findViewById(R.id.feeType);
        feeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedConduct1 = feeTypeSpinner.getSelectedItem().toString();
                feeTypeKey = (String) getKeyFromValue(conductMap1, selectedConduct1);

             /*   if (feeTypeKey != null && !feeTypeKey.isEmpty()) {
                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                   // new FeeAmountByFeeTypeBackTask(getActivity(), AddFeeCollectionFragment.this).execute(feeTypeKey);

                } else {
                    Toast.makeText(getActivity(), "Please Select Section.", Toast.LENGTH_LONG).show();
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        select = examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMarks.clear();
                if (feeTypeKey != null) {
                    new AllFeeCollectionReportsBackTask(getActivity(), AllFeeCollectionReportsFragment.this).execute(sectionKey,feeTypeKey);
                } else {
                    Toast.makeText(getActivity(), "Please select ", Toast.LENGTH_LONG).show();
                }
            }
        });




       /* // Student Spinner...
        studentSpinner = (Spinner) examEvalView.findViewById(R.id.studentSpinner);
        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStudent = studentSpinner.getSelectedItem().toString();
                studentkey = (String) getKeyFromValue(studentMap, selectedStudent);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        /*// Conduct Spinner....
        conductSpinner = (Spinner) examEvalView.findViewById(R.id.conductSpinner);
        conductMap.put("", "-- select --");
        conductMap.put("Cash", "Cash");
        conductMap.put("Cheque", "Cheque");
        conductMap.put("paytm", "paytm");

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



        // Student Spinner...
        feeTypeSpinner = (Spinner) examEvalView.findViewById(R.id.feeType);
        feeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedConduct1 = feeTypeSpinner.getSelectedItem().toString();
                feeTypeKey = (String) getKeyFromValue(conductMap1, selectedConduct1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

      /*  // Conduct Spinner....
        feeTypeSpinner = (Spinner) examEvalView.findViewById(R.id.feeType);
        conductMap1.put("", "-- select --");
        conductMap1.put("library", "library");
        conductMap1.put("books", "books");
        conductMap1.put("data", "data");

        conductList1 = new ArrayList<>(conductMap1.values());
        conductAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, conductList1);
        conductAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feeTypeSpinner.setAdapter(conductAdapter1);
        conductAdapter1.notifyDataSetChanged();

        feeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedConduct1 = feeTypeSpinner.getSelectedItem().toString();
                feeTypeKey = (String) getKeyFromValue(conductMap1, selectedConduct1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

*/
        /*fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  showFABMenu();
                AddFeeCollectionFragment addFeeCollectionFragment = new AddFeeCollectionFragment();
                setFragment(addFeeCollectionFragment);
                //   closeFABMenu();
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
        }

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                //animateFAB();
            }
        });*/


        /*editDate = (EditText) examEvalView.findViewById(R.id.editDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new AllFeeCollectionFragment.DatePickerFragement(), years, month, day);
                datePickerDialog.show();
            }
        });*/


        //
        /*select = examEvalView.findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMarks.clear();
                if (studentkey != null) {
                    new AllFeeCollectionBackTask(getActivity(), AllFeeCollectionReportsFragment.this).execute(studentkey);
                } else {
                    Toast.makeText(getActivity(), "Please select STUDENT", Toast.LENGTH_LONG).show();
                }
            }
        });*/
        //
        recycler_view = (RecyclerView) examEvalView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
    //    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Fee Collection");


        return examEvalView;
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                //.addToBackStack(null)
                .commit();
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
            public void onAnimationStart(Animator animator)
            {

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


    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        editMarks = (EditText) examEvalView.findViewById(R.id.editMarks);
        editPercent = (EditText) examEvalView.findViewById(R.id.editPercent);
        //
        addMarks = (Button) examEvalView.findViewById(R.id.addMarks);




        addMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject addObject = new JSONObject();
                try {
                    addObject.put("fine", editMarks.getText().toString());
                    addObject.put("discount", editPercent.getText().toString());
                   addObject.put("fee_type", feeTypeKey);
                    addObject.put("payment_mode", conductkey);
                    addObject.put("date", editDate.getText().toString());


                    System.out.println("addFeeCollection"+addObject);

                    // addObject.put("conduct", "comment");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AddFeeCollectionBackTask addFeeCollectionBackTask = new AddFeeCollectionBackTask(getActivity());
                addFeeCollectionBackTask.execute(String.valueOf(addObject), studentkey);
            }
              *//*  if (addObject.length() > 0) {
                    if (studentkey!= null) {
                        new AddFeeCollectionBackTask(getActivity()).execute(String.valueOf(addObject),studentkey);
                    } else {
                        Toast.makeText(getActivity(), "Please select Student", Toast.LENGTH_LONG).show();
                    }
                }*//*

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
    public void feeTypesByClassId(String result) throws JSONException {


        if (result != null && !result.isEmpty()) {
            conductMap1.clear();
            conductMap1.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            Log.e("fee type classId", String.valueOf(jsonObject));

            JSONArray jsonArray = jsonObject.getJSONArray("feeTypes");
            Log.e("fee collection type", String.valueOf(jsonArray));
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject stdObject = jsonArray.getJSONObject(count);
                // JSONObject examObject = jsonArray.getJSONObject(count);

                conductMap1.put(stdObject.getString("fee_types_id"), stdObject.getString("fee_type"));


                // String name = stdObject.getString("first_name") + " " + stdObject.getString("last_name");
                //  conductMap1.put(stdObject.getString("student_id"), name);
                count++;
            }
            conductList1 = new ArrayList<String>(conductMap1.values());
            //
            conductAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, conductList1);
            conductAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            feeTypeSpinner.setAdapter(conductAdapter1);
            conductAdapter1.notifyDataSetChanged();
        }


    }

    @Override
    public void allFeeCollectionReports(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            String dueDate="";
            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("fee Reports check"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("studentFee");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("alleval"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                    dueDate=markObject.getString("DueDate");
                    String dueDateDisplay=dueDate.substring(0,10);

                 /*   String a;
                    String b;
                    String c;
                    String d;
                    int total;
                    String totalValue;
                    a = markObject.getString("fee_amount");
                    b = markObject.getString("fine");
                    c = markObject.getString("discount");
                    d= markObject.getString("fee_paid");


                    int amount=Integer.parseInt(a);
                    int fine=Integer.parseInt(b);
                    int discount=Integer.parseInt(c);
                    int feePaid=Integer.parseInt(d);
                    total=((feePaid+fine)-discount);

                    // total=((amount+fine)-feePaid)-discount;
                    totalValue= String.valueOf(total);
                    System.out.println("total"+totalValue);*/
                    //paper_result_id
               /* *//**//*  *//**//**//**//**//**//**//**//*  FeeCollection evalMarks = new FeeCollection(markObject.getString("_id"), markObject.getString("exam_paper_id"),
                    selectedPaper, selectedStudent, markObject.getString("marks"),
                            markObject.getString("percentage"), markObject.getString("conduct"), markObject.getString("paper_name"),
                            markObject.getString("first_name"), markObject.getString("examschedule_name"), selectedExamSchedule, selectedSubject);*//**//**//**//**//**//**//**//**//**//**/
                    FeeCollectionReports feeCollectionmodel=new FeeCollectionReports(markObject.getString("studentName"),markObject.getString("totalFee"),markObject.getString("paidAmount"),markObject.getString("Discount"),markObject.getString("fine"),markObject.getString("Balance"),dueDateDisplay);
                    listMarks.add(feeCollectionmodel);
                    count++;
                }
                //Count....
                String taskCount = "FeeCollection (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);
                feeCollectionReportsAdapter = new FeeCollectionReportsAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(feeCollectionReportsAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                feeCollectionReportsAdapter.notifyDataSetChanged();
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

/*
    @Override
    public void allFeeCollection(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listMarks.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("feecollectionobject"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("student_fee_details");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("alleval"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                    String a;
                    String b;
                    String c;
                    String d;
                    int total;
                    String totalValue;
                    a = markObject.getString("fee_amount");
                    b = markObject.getString("fine");
                    c = markObject.getString("discount");
                    d= markObject.getString("fee_paid");


                    int amount=Integer.parseInt(a);
                    int fine=Integer.parseInt(b);
                    int discount=Integer.parseInt(c);
                    int feePaid=Integer.parseInt(d);
                    total=((feePaid+fine)-discount);

                   // total=((amount+fine)-feePaid)-discount;
                    totalValue= String.valueOf(total);
                    System.out.println("total"+totalValue);
                    //paper_result_id
                *//*  *//**//**//**//*  FeeCollection evalMarks = new FeeCollection(markObject.getString("_id"), markObject.getString("exam_paper_id"),
                            selectedPaper, selectedStudent, markObject.getString("marks"),
                            markObject.getString("percentage"), markObject.getString("conduct"), markObject.getString("paper_name"),
                            markObject.getString("first_name"), markObject.getString("examschedule_name"), selectedExamSchedule, selectedSubject);*//**//**//**//**//*
                  FeeCollection feeCollectionmodel=new FeeCollection(markObject.getString("current_date"),markObject.getString("fee_category"),markObject.getString("payment_mode"),markObject.getString("fee_amount"),markObject.getString("fine"),markObject.getString("discount"),totalValue,markObject.getString("fee_paid"));
                    listMarks.add(feeCollectionmodel);
                    count++;
                }
                //Count....
                String taskCount = "FeeCollection (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);
                feeCollectionAdapter = new FeeCollectionAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(feeCollectionAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                feeCollectionAdapter.notifyDataSetChanged();
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

    }*/


    /*@Override
    public void allVehicle(String result) throws JSONException {

        if (result != null && !result.isEmpty()) {
            conductMap1.clear();
            conductMap1.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("feetypes");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject stdObject = jsonArray.getJSONObject(count);
               // JSONObject examObject = jsonArray.getJSONObject(count);


                conductMap1.put(stdObject.getString("_id"), stdObject.getString("fee_type"));


               // String name = stdObject.getString("first_name") + " " + stdObject.getString("last_name");
              //  conductMap1.put(stdObject.getString("student_id"), name);
                count++;
            }
            conductList1 = new ArrayList<String>(conductMap1.values());
            //
            conductAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, conductList1);
            conductAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            feeTypeSpinner.setAdapter(conductAdapter1);
            conductAdapter1.notifyDataSetChanged();
        }



    }


    public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
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
}
