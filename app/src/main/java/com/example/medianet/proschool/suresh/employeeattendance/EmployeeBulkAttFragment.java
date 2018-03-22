package com.example.medianet.proschool.suresh.employeeattendance;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AddEmployeeBulkAttendence;
import com.example.medianet.proschool.AddStudentBulkAttendence;
import com.example.medianet.proschool.AllEmpAttBackTask;
import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.EmpAttendanceAdapter;
import com.example.medianet.proschool.EmpAttendanceFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;

import com.example.medianet.proschool.StudentsDetailsAttModel;
import com.example.medianet.proschool.checkattendance.AttSubModel;
import com.example.medianet.proschool.checkattendance.StudentCustomAdapter;
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

/**
 * Created by JANI on 06-06-2017.
 */

public class EmployeeBulkAttFragment extends Fragment implements  AllEmpAttBackTask.AllEmployees,EmployeeCustomAdapter.ItemClickStudentProfileListener {

    private RecyclerView recyclerView;
    private ArrayList<AttSubModel> modelArrayList=new ArrayList<>();
    private EmployeeCustomAdapter customAdapter;
    private Button btnPresent, btndeselect, submitAtt,absentAll,leaveAll;
    private  String[] animallist = new String[]{"Lion", "Tiger", "Leopard", "Cat"};



    ArrayList<AttSubModel> arrPresent;
    ArrayList<AttSubModel> arrAbsent;
    ArrayList<AttSubModel> arrLeave;
    AttSubModel model;
    View empAttView;
    //
    //Emp Layout
    LinearLayout emp_layout;
    TextView textEmpCount;

    // Type Spinner....
    String typeKey, selectedType;
    Spinner typeSpinner;
    Map<String, String> typeMap = new LinkedHashMap<String, String>();
    ArrayList<String> typeList;
    ArrayAdapter<String> typeAdapter;



    View studentAttendanceView;
    //
    SlidingUpPanelLayout sliding_layout;
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
    //
    Button submit, holiday, selectAllPresent, selectAllAbsent, selectAllLeave, deselectAll, btnDemo;
    FloatingActionButton search;
    //
    RecyclerView recycler_view;
    RecyclerView.Adapter mAdapter;


    Context mContext;

    String schoolId;

    private ArrayList<AttSubModel> bulkAttArray;

    EmployeeCustomAdapter employeeCustomAdapter=null;
    SharedPreferences sharedPreferences;




    public EmployeeBulkAttFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        empAttView = inflater.inflate(R.layout.emp_attendance_layout, container, false);
        mContext=getActivity();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        recycler_view = (RecyclerView)empAttView.findViewById(R.id.recycler_view);

        sliding_layout = (SlidingUpPanelLayout) empAttView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        emp_layout = (LinearLayout) empAttView.findViewById(R.id.emp_layout);
        textEmpCount = (TextView) empAttView.findViewById(R.id.textEmpCount);
        //
        //editSearch = (EditText) empAttView.findViewById(R.id.editSearch);
        // imgSearch = (ImageView) empAttView.findViewById(R.id.imgSearch);
        // type spinner....
        typeSpinner = (Spinner) empAttView.findViewById(R.id.typeSpinner);
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
        // gender spinner....
        /*genderSpinner = (Spinner) empAttView.findViewById(R.id.genderSpinner);
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
        // To Search Student....
        search = empAttView.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList.clear();
                new AllEmpAttBackTask(getActivity(), EmployeeBulkAttFragment.this).execute(typeKey,schoolId);
            }
        });



        modelArrayList = getModel(false);
        modelArrayList = getModel1(false);
        modelArrayList = getModel2(false);

        btnPresent = (Button) empAttView.findViewById(R.id.btnPresent);

        btnPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(true);
                //  modelArrayList = getModel1(false);
                //modelArrayList = getModel2(false);

                customAdapter = new EmployeeCustomAdapter(getActivity(),modelArrayList);
                recycler_view.setAdapter(customAdapter);
            }
        });
        absentAll = (Button) empAttView.findViewById(R.id.btnAbsent);
        absentAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel1(true);
                // modelArrayList = getModel(false);
                //modelArrayList = getModel2(false);

                customAdapter = new EmployeeCustomAdapter(getActivity(),modelArrayList);
                recycler_view.setAdapter(customAdapter);
            }
        });
        leaveAll = (Button) empAttView.findViewById(R.id.btnLeave);

        leaveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel2(true);
                // modelArrayList = getModel(false);
                //  modelArrayList = getModel1(false);

                customAdapter = new EmployeeCustomAdapter(getActivity(),modelArrayList);
                recycler_view.setAdapter(customAdapter);
            }
        });

        btndeselect = (Button) empAttView.findViewById(R.id.btnDeselect);
        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(false);
                customAdapter = new EmployeeCustomAdapter(getActivity(),modelArrayList);
                recycler_view.setAdapter(customAdapter);
            }
        });


        submitAtt = (Button) empAttView.findViewById(R.id.submitAtt);
        submitAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(SubmitAttendanceStudentActivity.this,NextActivity.class);
                startActivity(intent);*/

                JSONArray jsonArray = new JSONArray();
                JSONObject cartItemsObjedct;
                String statusSend = "";
                String studentId = null;

                String present = "Present";
                String absent = "Absent";
                String onLeave = "On Leave";
                bulkAttArray= employeeCustomAdapter.imageModelArrayList;

                for (int i = 0; i < bulkAttArray.size(); i++) {
                    AttSubModel attSubModel = bulkAttArray.get(i);
                    if (attSubModel.getSelected()) {
                        //  tv.setText(tv.getText() + "present " + attSubModel.getStudentId());

                        studentId=  attSubModel.getStudentId();
                        statusSend=present;
                    } else if (attSubModel.getSelectedAbsent()) {
                        studentId=  attSubModel.getStudentId();
                        statusSend=absent;
                    } else if (attSubModel.getSelectedLeave())

                    {
                        studentId=  attSubModel.getStudentId();
                        statusSend=onLeave;
                    }




                    cartItemsObjedct = new JSONObject();
                    try {


                        cartItemsObjedct.putOpt("employee_id",
                                studentId);

                        cartItemsObjedct.putOpt("status", statusSend);
                        cartItemsObjedct.putOpt("category", typeKey);
                        jsonArray.put(cartItemsObjedct);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject singleObject = new JSONObject();
                    try {
                        singleObject.put("employees", jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String str = singleObject.toString();
                    System.out.println("data is:" + str);


                    AddEmployeeBulkAttendence addEmployeeBulkAttendence = new AddEmployeeBulkAttendence(mContext);

                    addEmployeeBulkAttendence.execute(String.valueOf(singleObject), schoolId);

                }
            }
        });

        return empAttView;
    }
    private ArrayList<AttSubModel> getModel(boolean isSelect){
        arrPresent = new ArrayList<>();
        for(int i = 0; i <modelArrayList.size(); i++){

            model= new AttSubModel();
            model.setSelected(isSelect);
            // model.setAbsentSelected(isSelect);
            //  model.setLeaveSelected(isSelect);
            model.setStudentId(modelArrayList.get(i).getStudentId());
            model.setStdName(modelArrayList.get(i).getStdName());
            model.setAdmNo(modelArrayList.get(i).getAdmNo());
            model.setRollNo(modelArrayList.get(i).getRollNo());

            arrPresent.add(model);
        }
        return arrPresent;
    }


    private ArrayList<AttSubModel> getModel1(boolean isSelect){
        arrAbsent = new ArrayList<>();
        for(int i = 0; i <modelArrayList.size(); i++){

            model = new AttSubModel();
            // model.setSelected(isSelect);
            model.setAbsentSelected(isSelect);
            // model.setLeaveSelected(isSelect);
            model.setStudentId(modelArrayList.get(i).getStudentId());
            model.setStdName(modelArrayList.get(i).getStdName());
            model.setAdmNo(modelArrayList.get(i).getAdmNo());
            model.setRollNo(modelArrayList.get(i).getRollNo());


            arrAbsent.add(model);
        }
        return arrAbsent;
    }

    private ArrayList<AttSubModel> getModel2(boolean isSelect){
        arrLeave = new ArrayList<>();
        for(int i = 0; i <modelArrayList.size(); i++){

            model = new AttSubModel();
            // model.setSelected(isSelect);
            //  model.setAbsentSelected(isSelect);
            model.setLeaveSelected(isSelect);
            model.setStudentId(modelArrayList.get(i).getStudentId());
            model.setStdName(modelArrayList.get(i).getStdName());
            model.setAdmNo(modelArrayList.get(i).getAdmNo());
            model.setRollNo(modelArrayList.get(i).getRollNo());

            arrLeave.add(model);
        }
        return arrLeave;
    }



    @Override
    public void onClickProfile(View view, int position) {


        final AttSubModel city = modelArrayList.get(position);
        String studentId=city.getStudentId();

        sharedPreferences = mContext.getSharedPreferences("employeeInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.employeeId, studentId);
        editor.commit();
        startActivity(new Intent(mContext, EmployeeProfileTabsFragment.class));

       /* Intent intent = new Intent(mContext, StudentProfileTabsFragment.class);
        mContext.startActivity(intent);*/
        //   setFragment(studentProfileTabsFragment);
        //sendStudentId.sendData(studentName);

        Log.e("student click",studentId);
    }

    @Override
    public void allEmployees(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            modelArrayList.clear();
            String empName;
            String present="present";
            String absent="Absent";
            String onLeave="On Leave";
            String demo="Demo";

            JSONObject empObject = new JSONObject(result);
            System.out.println("employee Top Name"+empObject);
            JSONArray jsonArray = empObject.getJSONArray("employees");
            System.out.println("employee jsonArray"+jsonArray);

            if (empObject.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {


                    JSONObject jsonObject = jsonArray.getJSONObject(count);

                    empName = jsonObject.getString("first_name") + jsonObject.getString("last_name");

AttSubModel attSubModel=new AttSubModel(jsonObject.getString("employee_id"),jsonObject.getString("employee_id"), empName,jsonObject.getString("gender"),jsonObject.getString("gender"),jsonObject.getString("gender"),jsonObject.getString("job_category"),jsonObject.getString("gender"));

                    modelArrayList.add(attSubModel);


                    count++;
                }
                //Count....
                String taskCount = "Employees (" + String.valueOf(modelArrayList.size()) + ")";
                textEmpCount.setText(taskCount);
                //
                employeeCustomAdapter = new EmployeeCustomAdapter(getActivity(), modelArrayList);
                recycler_view.setAdapter(employeeCustomAdapter);
                employeeCustomAdapter.setClickListener(this);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
               employeeCustomAdapter.notifyDataSetChanged();
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
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();

        }
    }

    public SlidingUpPanelLayout getSliding_layout() {
        return sliding_layout;
    }*/
}
