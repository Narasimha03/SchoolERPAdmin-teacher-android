package com.example.medianet.proschool.suresh.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.StudentAdmissionActivity;
import com.example.medianet.proschool.StudentAdmissionFragment;
import com.example.medianet.proschool.StudentDetailsAdapter;
import com.example.medianet.proschool.StudentsDetails;
import com.example.medianet.proschool.suresh.QuickDashboardClass;
import com.example.medianet.proschool.suresh.images.AllImageBackTask;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileTabsFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by personal on 23-04-2017.
 */

public class AllStudentDetails extends Fragment implements ClassBackGroundTask.OnClassResponse, AllStudentsBackTask.AllStudents, SectionBackGroundTask.OnSectionResponse, View.OnClickListener,AllImageBackTask.AllImages,StudentDetailsAdapter.ItemClickStudentProfileListener {
    Context mContext;
    SendMessage sendStudentId;

    //
    //Student Layout
    LinearLayout std_layout;
    TextView textStdCount;
    // Edit Search....
    EditText editSearch;
    ImageView imgSearch;
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
    // Year Spinner....
    String yearKey, selectedYear;
    Spinner yearSpinner;
    Map<String, String> yearMap = new LinkedHashMap<String, String>();
    ArrayList<String> yearList;
    ArrayAdapter<String> yearAdapter;
    // Button....
    FloatingActionButton search;

    //Sliding Panel

    SlidingUpPanelLayout sliding_layout;
    // Recyclerview....
    RecyclerView recycler_view;
    StudentDetailsAdapter studentDetailsAdapter;
    ArrayList<StudentsDetails> studentsDetailsArrayList = new ArrayList<StudentsDetails>();

    //fab

    FloatingActionButton fab, fab1, fab2, fab3;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    FrameLayout frameLayout;
    // FrameLayout fabBGLayout;
    boolean isFABOpen = false;
    public static int navigationItemIndex = 0;


    private Boolean isFabOpen = false;
    //   private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    String schoolId;
    String className, sectionName;

    SharedPreferences sharedPreferences;
    String role;


    public AllStudentDetails() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View studentDetails = inflater.inflate(R.layout.all_student_details_layout, container, false);
        sliding_layout = (SlidingUpPanelLayout) studentDetails.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        std_layout = (LinearLayout) studentDetails.findViewById(R.id.std_layout);
        textStdCount = (TextView) studentDetails.findViewById(R.id.textStdCount);
        //
        // editSearch = (EditText) studentDetails.findViewById(R.id.editSearch);
        // imgSearch = (ImageView) studentDetails.findViewById(R.id.imgSearch);


      /*  fab = (FloatingActionButton) studentDetails.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) studentDetails.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) studentDetails.findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);*/


        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        Log.e("schoolId dashBaord", schoolId);

        new ClassBackGroundTask(getActivity(), AllStudentDetails.this).execute(schoolId);

/*
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.schoolIdInfo, MODE_PRIVATE);
     String  schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
     Log.e("schoolId student",schoolId);
     System.out.println("schoolIdstudent"+schoolId);*/


//fab
        fabLayout1 = (LinearLayout) studentDetails.findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) studentDetails.findViewById(R.id.fabLayout2);
        fabLayout3 = (LinearLayout) studentDetails.findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) studentDetails.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) studentDetails.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) studentDetails.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) studentDetails.findViewById(R.id.fab3);
        frameLayout = (FrameLayout) studentDetails.findViewById(R.id.frameLayout);
        fabBGLayout = studentDetails.findViewById(R.id.fabBGLayout);

        //   fabBGLayout=studentDetails.findViewById(R.id.fabBGLayout);


        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  showFABMenu();
                /*StudentAdmissionFragment studentAdmissionFragment = new StudentAdmissionFragment();
                setFragment(studentAdmissionFragment);*/
                //   closeFABMenu();

                Intent i = new Intent(getActivity(),StudentAdmissionActivity.class);
                startActivity(i);

                closeFABMenu();
            }


        });

        /*fabLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              //  StudentAttendenceFragment studentAttendenceFragment = new StudentAttendenceFragment();
              //  setFragment(studentAttendenceFragment);
                //   closeFABMenu();
            }


        });*/

        /*fabLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StudentAdmissionFragment studentAdmissionFragment = new StudentAdmissionFragment();
                setFragment(studentAdmissionFragment);
                              //StudentMoreFragment studentMoreFragment = new StudentMoreFragment();
                //setFragment(studentMoreFragment);

                //   closeFABMenu();
            }


        });*/


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
            classSpinner = (Spinner) studentDetails.findViewById(R.id.classSpinner);
             classMap.put("", "-- Select --");

            classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedClass = classSpinner.getSelectedItem().toString();
                    classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classKey = (String) getKeyFromValue(classMap, selectedClass);
                    // Getting Sections From Server....
                    if (classKey != null && !classKey.isEmpty()) {
                        new SectionBackGroundTask(getActivity(), AllStudentDetails.this).execute(classKey);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            // section spinner....
            sectionSpinner = (Spinner) studentDetails.findViewById(R.id.sectionSpinner);

            sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedSection = sectionSpinner.getSelectedItem().toString();
                    sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
       /* // year spinner....
        yearSpinner = (Spinner) studentDetails.findViewById(R.id.yearSpinner);
        yearMap.put("", "-- select --");
        yearMap.put("2017", "2017");
        yearMap.put("2016", "2016");
        yearMap.put("2015", "2015");
        yearList = new ArrayList<String>(yearMap.values());
        yearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearAdapter.notifyDataSetChanged();
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = yearSpinner.getSelectedItem().toString();
                yearKey = (String) getKeyFromValue(yearMap, selectedYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
            recycler_view = (RecyclerView) studentDetails.findViewById(R.id.recycler_view);
            recycler_view.setNestedScrollingEnabled(false);
            // To Search Student....
            search = studentDetails.findViewById(R.id.search);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    studentsDetailsArrayList.clear();
                    if (classKey.isEmpty() || sectionKey.isEmpty()) {
                        Toast.makeText(getActivity(), "Please provide all details...!", Toast.LENGTH_LONG).show();
                    } else {
                        new AllStudentsBackTask(getActivity(), AllStudentDetails.this).execute(sectionKey);
                    }

                }
            });

            // to set action bar title....
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Student Details");


            return studentDetails;


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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // To Display Classes....
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

                    //classMap.put(cObject.getString("class_id"), cObject.getString("name"));

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

    // To Display Sections....
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

   /* @Override
    public void onSearchStd(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            studentsDetailsArrayList.clear();
            String stdName;
            String parentName = "";
            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    stdName = jsonObject.getString("first_name") + jsonObject.getString("last_name");
                    // Parent Array....
                    if (!jsonObject.isNull("parent")) {
                        JSONArray parentArray = jsonObject.getJSONArray("parent");
                        int p = 0;
                        while (p < parentArray.length()) {
                            JSONObject parentObject = parentArray.getJSONObject(p);
                            parentName = parentObject.getString("parent_name");
                            p++;
                        }
                    }

                    StudentsDetails studentsDetails = new StudentsDetails(jsonObject.getString("student_id"), jsonObject.getString("admission_no"),
                            stdName, jsonObject.getString("class_id"), parentName, jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("category"), jsonObject.getString("phone"), jsonObject.getString("status"),
                            jsonObject.getString("roll_no"));
                    studentsDetailsArrayList.add(studentsDetails);

                    count++;
                }
                //Count....
                String taskCount = "Students (" + String.valueOf(studentsDetailsArrayList.size()) + ")";
                textStdCount.setText(taskCount);
                //Recyclerview....
                recycler_view.setVisibility(View.VISIBLE);
                studentDetailsAdapter = new StudentDetailsAdapter(getActivity(), studentsDetailsArrayList);
                recycler_view.setAdapter(studentDetailsAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentDetailsAdapter.notifyDataSetChanged();
                //Sliding Panel
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(320);
            } else {
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }
    }*/

    @Override
    public void allStudents(String result) throws JSONException {

        System.out.println("hello");

        if (result != null && !result.isEmpty()) {
            studentsDetailsArrayList.clear();
            String stdName;
            String parentName = "";
            String imageName= "";
            String imageurl;


            JSONObject student = new JSONObject(result);
            JSONArray jsonArray = student.getJSONArray("students");
            System.out.println("json data" + jsonArray);

            // JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    stdName = jsonObject.getString("first_name") + jsonObject.getString("last_name");


                    // Parent Array....
                    if (!jsonObject.isNull("parents")) {
                        JSONArray parentArray = jsonObject.getJSONArray("parents");
                        int p = 0;
                        while (p < parentArray.length()) {
                            JSONObject parentObject = parentArray.getJSONObject(p);
                            parentName = parentObject.getString("parent_name");
                            p++;
                        }


                    }
                    //studentImage
                    if (!jsonObject.isNull("studentImage")) {
                        JSONArray imageArray = jsonObject.getJSONArray("studentImage");
                        int p = 0;
                        while (p < imageArray.length()) {
                            JSONObject parentObject = imageArray.getJSONObject(p);
                            imageName = parentObject.getString("filename");
                            p++;
                        }



                    }
                    imageurl=Constants.singleImage+imageName;
                   /* String s=new AllImageBackTask(getActivity(),AllStudentDetails.this).execute(imageName));
                     System.out.println("image data names"+s);*/

                    if (!jsonObject.isNull("school_classes")) {
                        JSONArray classNameArray = jsonObject.getJSONArray("school_classes");
                        int p = 0;
                        while (p < classNameArray.length()) {
                            JSONObject parentObject = classNameArray.getJSONObject(p);
                            className = parentObject.getString("name");
                            p++;
                        }

                    }


                    if (!jsonObject.isNull("sections")) {
                        JSONArray sectionNameArray = jsonObject.getJSONArray("sections");
                        int p = 0;
                        while (p < sectionNameArray.length()) {
                            JSONObject parentObject = sectionNameArray.getJSONObject(p);
                            sectionName = parentObject.getString("name");
                            p++;
                        }

                    }

/*
                    "studentImage": [
                    {
                        "filename": "Rithu.jpg",
                            "originalname": "Rithu.jpg",
                            "imagePath": "uploads\\Rithu.jpg",
                            "mimetype": "image/jpeg"
                    }
            ]*/
                    StudentsDetails studentsDetails = new StudentsDetails(jsonObject.getString("student_id"), jsonObject.getString("admission_no"),
                            stdName, className+" ("+sectionName+")", parentName, jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("category"), jsonObject.getString("phone"), jsonObject.getString("roll_no"),
                            jsonObject.getString("roll_no"),false,imageurl);
                    studentsDetailsArrayList.add(studentsDetails);

                    count++;



                    //"studentImage":[{"filename":"Rithu.jpg","originalname":"Rithu.jpg","imagePath":"uploads\\Rithu.jpg","mimetype":"image\/jpeg"}
                }
                //Count....
                String taskCount = "Students (" + String.valueOf(studentsDetailsArrayList.size()) + ")";
                textStdCount.setText(taskCount);
                //Recyclerview....
                recycler_view.setVisibility(View.VISIBLE);
                studentDetailsAdapter = new StudentDetailsAdapter(getActivity(), studentsDetailsArrayList);
                recycler_view.setAdapter(studentDetailsAdapter);
                studentDetailsAdapter.setClickListener(this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentDetailsAdapter.notifyDataSetChanged();

                //Sliding Panel
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(120);
            } else {
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }

    /*    if (result != null && !result.isEmpty()) {
            String stdName;
            String parentName = "";
            studentsDetailsArrayList.clear();
            JSONObject student = new JSONObject(result);
            JSONArray jsonArray = student.getJSONArray("students");
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    stdName = jsonObject.getString("first_name") + jsonObject.getString("last_name");
                    if (!jsonObject.isNull("parent")) {
                        JSONArray parentArray = jsonObject.getJSONArray("parent");
                        int p = 0;
                        while (p < parentArray.length()) {
                            JSONObject parentObject = parentArray.getJSONObject(p);
                            parentName = parentObject.getString("parent_name");
                            p++;
                        }
                    }

                    StudentsDetails studentsDetails = new StudentsDetails(jsonObject.getString("student_id"), jsonObject.getString("admission_no"),
                            stdName, jsonObject.getString("class_id"), parentName, jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("category"), jsonObject.getString("phone"), jsonObject.getString("status"),
                            jsonObject.getString("roll_no"));
                    studentsDetailsArrayList.add(studentsDetails);
                    count++;
                }

                studentDetailsAdapter = new StudentDetailsAdapter(getActivity(), studentsDetailsArrayList);
                recycler_view.setAdapter(studentDetailsAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentDetailsAdapter.notifyDataSetChanged();
            } else {
                recycler_view.setVisibility(View.GONE);
            }
        }*/

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:
                StudentAdmissionFragment studentAdmissionFragment=new StudentAdmissionFragment();
                setFragment(studentAdmissionFragment);

                break;
            case R.id.fab2:

                Log.d("Raj", "Fab 2");
                break;

        }
    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }

    @Override
    public void allImages(String allImages) throws JSONException {



    }






    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            sendStudentId = (SendMessage) getContext();
        } catch (ClassCastException e) {
//            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    @Override
    public void onClickProfile(View view, int position) {
        final StudentsDetails city = studentsDetailsArrayList.get(position);
 String studentId=city.getStdId();

        sharedPreferences = mContext.getSharedPreferences("studentInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.studentId, studentId);
        editor.commit();
        startActivity(new Intent(mContext, StudentProfileTabsFragment.class));

       /* Intent intent = new Intent(mContext, StudentProfileTabsFragment.class);
        mContext.startActivity(intent);*/
     //   setFragment(studentProfileTabsFragment);
        //sendStudentId.sendData(studentName);

        Log.e("student click",studentId);
    }

    public  interface SendMessage {
        void sendData(String message);
    }

    public void onBackPressed() {
        Intent myIntent = new Intent(getContext(), QuickDashboardClass.class);
        startActivityForResult(myIntent, 0);

    }


}





















    /*

    {"class_sections":[{"_id":"5927f96b5e22d92b8ae23036","section_id":"SCH-9271-CL-1-SEC-1","class_id":"SCH-9271-CL-1","name":"section A","status":1}]}
            {"school_classes":[{"_id":"59140a7d816fb10c35252493","class_id":"SCH-9271-CL-1","school_id":"SCH-9271","name":"class 1","status":1},{"_id":"59140b14816fb10c35252494","class_id":"SCH-9271-CL-2","school_id":"SCH-9271","name":"class 2","status":1},{"_id":"59154c04816fb10c35252499","class_id":"SCH-9271-CL-3","school_id":"SCH-9271","name":"2nd Class","status":1},{"_id":"59154c25816fb10c3525249a","class_id":"SCH-9271-CL-4","school_id":"SCH-9271","name":"2nd Class","status":1}]}
             */

