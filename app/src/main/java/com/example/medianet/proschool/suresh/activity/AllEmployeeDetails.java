package com.example.medianet.proschool.suresh.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AllEmpAttBackTask;
import com.example.medianet.proschool.AllEmpBackTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.EmpAttendanceFragment;
import com.example.medianet.proschool.EmployeeDetailsFragment;
import com.example.medianet.proschool.EmployeeDetailsFragmentToActivity;
import com.example.medianet.proschool.EmployeeFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.StudentAdmissionFragment;
import com.example.medianet.proschool.StudentAttendenceFragment;
import com.example.medianet.proschool.StudentDetailsAdapter;
import com.example.medianet.proschool.StudentsDetails;
import com.example.medianet.proschool.suresh.employeeattendancetabs.EmployeeAttendanceTabsFragment;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileTabsFragment;
import com.example.medianet.proschool.suresh.studentprofile.employeeprofile.EmployeeProfileTabsFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

public class AllEmployeeDetails extends Fragment implements AllEmpBackTask.AllEmployees,AllEmpDetailsByCategoryBackTask.AllEmpDetailsByCategory,StudentDetailsAdapter.ItemClickStudentProfileListener {

    View employeeView;
    //
    SlidingUpPanelLayout sliding_layout;
    // Edit Search....
    EditText editSearch;
    ImageView imgSearch;
    // Type Spinner....
    String typeKey, selectedType;
    Spinner typeSpinner;
    Map<String, String> typeMap = new LinkedHashMap<String, String>();
    ArrayList<String> typeList;
    ArrayAdapter<String> typeAdapter;
    // Gender Spinner....
    String genderKey, genderSection;
    Spinner genderSpinner;
    Map<String, String> genderMap = new LinkedHashMap<String, String>();
    ArrayList<String> genderList;
    ArrayAdapter<String> genderAdapter;
    // Year Spinner....
    String yearKey, selectedYear;
    Spinner yearSpinner;
    Map<String, String> yearMap = new LinkedHashMap<String, String>();
    ArrayList<String> yearList;
    ArrayAdapter<String> yearAdapter;
    //Emp Layout
    LinearLayout emp_layout;
    TextView textEmpCount;
    // Recylerview....
    RecyclerView recycler_view;
    StudentDetailsAdapter studentDetailsAdapter;
    ArrayList<StudentsDetails> studentsDetailsArrayList = new ArrayList<StudentsDetails>();
    // Button....
    FloatingActionButton search;


    FloatingActionButton fab, fab1, fab2, fab3;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
     View fabBGLayout;
     FrameLayout frameLayout;
   // FrameLayout fabBGLayout;
    boolean isFABOpen = false;
    String schoolId;
    Context mContext;
    SharedPreferences sharedPreferences;

    public AllEmployeeDetails() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new ClassBackGroundTask(getActivity(), EmployeeFragment.this).execute(Constants.schoolId);
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        employeeView = inflater.inflate(R.layout.employee_layout, container, false);
        //
        mContext=getActivity();



        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
      //  new AllEmpBackTask(getActivity(), AllEmployeeDetails.this).execute(schoolId);

         sliding_layout = (SlidingUpPanelLayout) employeeView.findViewById(R.id.sliding_layout);
         sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        emp_layout = (LinearLayout) employeeView.findViewById(R.id.std_layout);
        textEmpCount = (TextView) employeeView.findViewById(R.id.textStdCount);
        //
       // editSearch = (EditText) employeeView.findViewById(R.id.editSearch);
       // imgSearch = (ImageView) employeeView.findViewById(R.id.imgSearch);


        fabLayout1= (LinearLayout) employeeView.findViewById(R.id.fabLayout1);
      //  fabLayout2= (LinearLayout) employeeView.findViewById(R.id.fabLayout2);
      //  fabLayout3= (LinearLayout) employeeView.findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) employeeView.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) employeeView.findViewById(R.id.fab1);
       // fab2= (FloatingActionButton) employeeView.findViewById(R.id.fab2);
       // fab3 = (FloatingActionButton) employeeView.findViewById(R.id.fab3);
         frameLayout=(FrameLayout)employeeView.findViewById(R.id.frameLayout);
        fabBGLayout=employeeView.findViewById(R.id.fabBGLayout);

        //   fabBGLayout=studentDetails.findViewById(R.id.fabBGLayout);

     /*   SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");

*/


        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*EmployeeDetailsFragmentToActivity employeeDetailsFragment = new EmployeeDetailsFragmentToActivity();
                setFragment(employeeDetailsFragment);*/
                Intent i = new Intent(getActivity(),EmployeeDetailsFragmentToActivity.class);
                startActivity(i);
                closeFABMenu();

                        //EmployeeDetailsFragment
                //  showFABMenu();
              //  EmployeeDetailsFragment employeeDetailsFragment = new EmployeeDetailsFragment();
              //  setFragment(employeeDetailsFragment);
                //   closeFABMenu();
            }

        });

        /*fabLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //EmpAttendanceFragment empAttendanceFragment = new EmpAttendanceFragment();
               // setFragment(empAttendanceFragment);
                //   closeFABMenu();
            }


        });

        fabLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmployeeDetailsFragment employeeDetailsFragment = new EmployeeDetailsFragment();
                setFragment(employeeDetailsFragment);
                //StudentMoreFragment studentMoreFragment = new StudentMoreFragment();
                //setFragment(studentMoreFragment);

                //   closeFABMenu();
            }


        });*/



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //    animateFAB();
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                //animateFAB();
            }
        });






        // type spinner....
        typeSpinner = (Spinner) employeeView.findViewById(R.id.classSpinner);
        typeMap.put("", "-- Select --");
        typeMap.put("teaching", "Teaching");
        typeMap.put("non-teaching", "Non Teaching");
        typeMap.put("administrative", "Administrative");

        typeList = new ArrayList<String>(typeMap.values());

        typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeAdapter.notifyDataSetChanged();
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = typeSpinner.getSelectedItem().toString();
                typeKey = (String) getKeyFromValue(typeMap, selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*// section spinner....
        genderSpinner = (Spinner) employeeView.findViewById(R.id.sectionSpinner);
        genderMap.put("", "-- select --");
        genderMap.put("Male", "Male");
        genderMap.put("Female", "Female");
        genderList = new ArrayList<String>(genderMap.values());

        genderAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderAdapter.notifyDataSetChanged();
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderSection = genderSpinner.getSelectedItem().toString();
                genderKey = (String) getKeyFromValue(genderMap, genderSection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        /*// year spinner....
        yearSpinner = (Spinner) employeeView.findViewById(R.id.yearSpinner);
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
        // To Search Student....
        search = employeeView.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentsDetailsArrayList.clear();
               // if (typeKey.isEmpty() || genderKey.isEmpty() || editSearch.getText().toString().isEmpty()) {
                /*if (typeKey.isEmpty() || genderKey.isEmpty()&&typeKey!=null) {

                    Toast.makeText(getActivity(), "Please provide all details....!", Toast.LENGTH_LONG).show();
                } else {*/
                    new AllEmpDetailsByCategoryBackTask(getActivity(), AllEmployeeDetails.this).execute(typeKey,schoolId);


            }
        });

        recycler_view = (RecyclerView) employeeView.findViewById(R.id.recycler_view);
        recycler_view.setVisibility(View.VISIBLE);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Employee");

        return employeeView;
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
        /*fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);*/
        // fabLayout4.setVisibility(View.VISIBLE);
        // fabLayout5.setVisibility(View.VISIBLE);
        //fabLayout6.setVisibility(View.VISIBLE);

        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(90);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));


        /*fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));*/
        //fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
        //    fabLayout4.animate().translationY(-getResources().getDimension(R.dimen.standard_190));
        //  fabLayout5.animate().translationY(-getResources().getDimension(R.dimen.standard_235));
        //  fabLayout6.animate().translationY(-getResources().getDimension(R.dimen.standard_280));


    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        /*fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);*/
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    /*fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);*/


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
    public void allEmployees(String result) throws JSONException {


        if (result != null && !result.isEmpty()) {
            studentsDetailsArrayList.clear();
            String empName;
            String imageName= "";
            String imageurl;
            JSONObject student = new JSONObject(result);
            JSONArray jsonArray = student.getJSONArray("employee");
            System.out.println("json employee" + jsonArray);
          //  JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                 //   empName = jsonObject.getString("surname") + jsonObject.getString("first_name") + jsonObject.getString("last_name");
                    empName =  jsonObject.getString("first_name") + jsonObject.getString("last_name");

                    //studentImage
                    if (!jsonObject.isNull("employeeImage")) {
                        JSONArray imageArray = jsonObject.getJSONArray("employeeImage");
                        int p = 0;
                        while (p < imageArray.length()) {
                            JSONObject parentObject = imageArray.getJSONObject(p);
                            imageName = parentObject.getString("filename");
                            p++;
                        }



                    }
                    imageurl= Constants.singleImage+imageName;

                    StudentsDetails studentsDetails = new StudentsDetails(jsonObject.getString("employee_id"), jsonObject.getString("school_id"),
                            empName, jsonObject.getString("job_category"), jsonObject.getString("qualification"), jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("experience"), jsonObject.getString("phone"), jsonObject.getString("joined_on"), jsonObject.getString("status"),false,imageurl);
                    studentsDetailsArrayList.add(studentsDetails);

                    count++;
                }
                //Count....
                String taskCount = "Employees (" + String.valueOf(studentsDetailsArrayList.size()) + ")";
                textEmpCount.setText(taskCount);
                //
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

       // "employeeImage":[{"filename":"Lighthouse.jpg","originalname":"Lighthouse.jpg","imagePath":"uploads\\Lighthouse.jpg","mimetype":"image\/jpeg"}]},{"_id":"5a03ec7d505909261c9f9813","employee_id":"SCH-EMP-8","school_id":"SCH-9274","first_name":"phani kumar","last_name":"p","surname":null,"dob":"15-01-1992","gender":"male","qualification":"Bachelors degree","job_category":"administrative","experience":"5","phone":"9638520741","email":"phani@gmail.com","website":"undefined","joined_on":"21-10-2015","status":1,"current_address":[{"cur_address":null,"cur_city":null,"cur_state":null,"cur_pincode":null,"cur_long":null,"cur_lat":null}],"permanent_address":[{"perm_address":null,"perm_city":null,"perm_state":null,"perm_pincode":null,"perm_long":null,"perm_lat":null}],"employeeImage":[{"filename":"417570751.jpg","originalname":"417570751.jpg","imagePath":"uploads\\417570751.jpg","mimetype":"image\/jpeg"}]},{"_id":"5a03ecde505909261c9f9814","employee_id":"SCH-EMP-9","school_id":"SCH-9274","first_name":"surya","last_name":"kiran","surname":null,"dob":"17-01-1992","gender":"male","qualification":"Bachelors degree","job_category":"administrative","experience":"4","phone":"7418520963","email":"surya@gmail.com","website":"undefined","joined_on":"14-10-2015","status":1,"current_address":[{"cur_address":null,"cur_city":null,"cur_state":null,"cur_pincode":null,"cur_long":null,"cur_lat":null}],"permanent_address":[{"perm_address":null,"perm_city":null,"perm_state":null,"perm_pincode":null,"perm_long":null,"perm_lat":null}],"employeeImage":[{"filename":"27851203.jpg","originalname":"27851203.jpg","imagePath":"uploads\\27851203.jpg","mimetype":"image\/jpeg"}]},{"_id":"5a2e57e14c15a0143885206a","employee_id":"SCH-EMP-13","school_id":"SCH-9274","first_name":"Indu","last_name":"Balla","surname":null,"dob":"10-10-1996","gender":"female","qualification":"Pre-Bachelors","job_category":"teaching","experience":"2","phone":"4567578689","email":"indu34@gmail.com","joined_on":"16-08-2017","status":1,"current_address":[{"cur_address":null,"cur_city":null,"cur_state":null,"cur_pincode":null,"cur_long":null,"cur_lat":null}],"permanent_address":[{"perm_address":null,"perm_city":null,"perm_state":null,"perm_pincode":null,"perm_long":null,"perm_lat":null}],"employeeImage":[{"filename":"file-1512986589387.jpg","originalname":"walter_white_by_adamwien-d6zl1gk.jpg","imagePath":"uploads\\file-1512986589387.jpg","mimetype":"image\/jpeg"}]},{"_id":"5a2f73774f48d11da8af270e","employee_id":"SCH-EMP-18","school_id":"SCH-9274","first_name":"joythi","last_name":"thadi","surname":null,"dob":"25-01-1975","gender":"female","qualification":"Bachelors degree","job_category":"non-teaching","experience":"3","phone":"7869799088","email":"joythi@gmail.com","joined_on":"07-11-2017","status":1,"current_address":[{"cur_address":null,"cur_city":null,"cur_state":null,"cur_pincode":null,"cur_long":null,"cur_lat":null}],"permanent_address":[{"perm_address":null,"perm_city":null,"perm_state":null,"perm_pincode":null,"perm_long":null,"perm_lat":null}],"employeeImage":[{"filename":"file-1513059184045.jpg","originalname":"walter_white_by_adamwien-d6zl1gk.jpg","imagePath":"uploads\\file-1513059184045.jpg","mimetype":"image\/jpeg"}]},{"_id":"5a2f73cf4f48d11da8af270f","employee_id":"SCH-EMP-19","school_id":"SCH-9274","first_name":"divya","last_name":"mohi","surname":null,"dob":"25-01-1975","gender":"female","qualification":"Masters degree","job_catego
        //12-14 12:20:40.595 19977-19977/? I/chromium: [INFO:CONSOLE(1)] "Synchronous XMLHttpRequest on the main thread is deprecated because of its detrimental effects to the end loigin_user's experience. For more help, check https://xhr.spec.whatwg.org/.", source: http://cdn2.inner-active.mobi/IA-JSTag/Production/IA-JS-TAG-latest.min.js (1)
    }

    @Override
    public void allEmpDetailsByCategory(String result) throws JSONException {




        if (result != null && !result.isEmpty()) {
            studentsDetailsArrayList.clear();
            String empName;
            String imageName= "";
            String imageurl;
            JSONObject student = new JSONObject(result);
            JSONArray jsonArray = student.getJSONArray("employees");
            System.out.println("json employee category" + jsonArray);
            //  JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    //   empName = jsonObject.getString("surname") + jsonObject.getString("first_name") + jsonObject.getString("last_name");
                    empName =  jsonObject.getString("first_name") + jsonObject.getString("last_name");

                    //studentImage
                    if (!jsonObject.isNull("employeeImage")) {
                        JSONArray imageArray = jsonObject.getJSONArray("employeeImage");
                        int p = 0;
                        while (p < imageArray.length()) {
                            JSONObject parentObject = imageArray.getJSONObject(p);
                            imageName = parentObject.getString("filename");
                            p++;
                        }



                    }
                    imageurl= Constants.singleImage+imageName;

                    StudentsDetails studentsDetails = new StudentsDetails(jsonObject.getString("employee_id"), jsonObject.getString("school_id"),
                            empName, jsonObject.getString("job_category"), jsonObject.getString("qualification"), jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("experience"), jsonObject.getString("phone"), jsonObject.getString("joined_on"), jsonObject.getString("status"),false,imageurl);
                    studentsDetailsArrayList.add(studentsDetails);

                    count++;
                }
                //Count....
                String taskCount = "Employees (" + String.valueOf(studentsDetailsArrayList.size()) + ")";
                textEmpCount.setText(taskCount);
                //

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
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    public void onClickProfile(View view, int position) {
        final StudentsDetails city = studentsDetailsArrayList.get(position);
        String employeeId=city.getStdId();

        sharedPreferences = mContext.getSharedPreferences("employeeInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.employeeId, employeeId);
        editor.commit();
        startActivity(new Intent(mContext, EmployeeProfileTabsFragment.class));

       /* Intent intent = new Intent(mContext, StudentProfileTabsFragment.class);
        mContext.startActivity(intent);*/
        //   setFragment(studentProfileTabsFragment);
        //sendStudentId.sendData(studentName);

        Log.e("student click",employeeId);
    }


}