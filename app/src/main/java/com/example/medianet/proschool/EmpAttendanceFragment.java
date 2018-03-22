package com.example.medianet.proschool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.example.medianet.proschool.suresh.checkboxs.DemoCheckAdapter;
import com.example.medianet.proschool.suresh.checkboxs.DemoCheckBox;
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
 * Created by JANI on 13-06-2017.
 */

public class EmpAttendanceFragment extends Fragment implements AllEmpAttBackTask.AllEmployees {

    View empAttView;
    //
    SlidingUpPanelLayout sliding_layout;
    //Emp Layout
    LinearLayout emp_layout;
    TextView textEmpCount;
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
    // Button....
    Button submitAttBtn,selectAllPresent,selectAllAbsent,selectAllLeave,deselectAll;
    //

    FloatingActionButton search;
    RecyclerView recycler_view;
    EmpAttendanceAdapter empAttendanceAdapter;
    ArrayList<StudentsDetailsAttModel> listEmp = new ArrayList<StudentsDetailsAttModel>();
    Context mContext;
    String schoolId;


    public EmpAttendanceFragment() {

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
                listEmp.clear();
                new AllEmpAttBackTask(getActivity(), EmpAttendanceFragment.this).execute(typeKey,schoolId);
            }
        });







      /*  submit = (Button) studentAttendanceView.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data ="";
                String present="";
                String absent="";
                List<DemoCheckBox> stList = ((DemoCheckAdapter) studentAttendanceAdapter)
                        .getStudentsDetailsList();

                System.out.println("student ids:" + data);
                System.out.println("student status:" + present);
                System.out.println("student status:" + absent);

                JSONArray jsonArray = new JSONArray();
                JSONObject cartItemsObjedct;
                for (int n = 0; n < stList.size(); n++) {
                    DemoCheckBox singleStudent1 = stList.get(n);

                    if (singleStudent1.getDemoSelected() == true||singleStudent1.getAbsentSelected()==true||singleStudent1.getLeaveSelected()==true) {


                        // if (singleStudent1.getDemoSelected() == true) {

                        data = singleStudent1.getStdId();

                        present = singleStudent1.getPresentStatus();

                        System.out.println("present data" + present + "" + data);




                        if (data != null && present!=null) {


                            cartItemsObjedct = new JSONObject();
                            try {

                                cartItemsObjedct.putOpt("student_id",
                                        data);

                                cartItemsObjedct.putOpt("status", present);
                                jsonArray.put(cartItemsObjedct);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject singleObject = new JSONObject();
                            try {
                                singleObject.put("students", jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String str = singleObject.toString();
                            System.out.println("data is:" + str);


                            AddStudentBulkAttendence addStudentBulkAttendence = new AddStudentBulkAttendence(getActivity());

                            addStudentBulkAttendence.execute(String.valueOf(singleObject), classKey, sectionKey, Constants.schoolId);
                        }
                        else
                        {

                            Toast.makeText(getActivity(),
                                    "Please Select Atleast one student: " + singleStudent1.getPresentStatus(), Toast.LENGTH_LONG).show();


                        }
                    }

                    else
                    {

                        Toast.makeText(getActivity(),
                                "Please Select students " + singleStudent1.getPresentStatus(), Toast.LENGTH_LONG).show();

                    }

                }
            }
            //   }
        });*/


        selectAllPresent = (Button) empAttView.findViewById(R.id.btnPresent);
        selectAllPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (typeKey != null && !typeKey.isEmpty()) {

                    listEmp = getModel(true, false, false);
                    //  listStudents = getModel1(false);
                    //   listStudents = getModel2(false);

                    empAttendanceAdapter = new EmpAttendanceAdapter(getActivity(), listEmp);
                    recycler_view.setAdapter(empAttendanceAdapter);
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);
                    // studentAttendanceAdapter.notifyDataSetChanged();
             /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();*/

                }
                else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectAllAbsent = (Button) empAttView.findViewById(R.id.btnAbsent);
        selectAllAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // listStudents = getModel1(true);
                if (typeKey != null && !typeKey.isEmpty()) {

                    listEmp = getModel(false, true, false);
                    // listStudents = getModel2(false);

                    empAttendanceAdapter = new EmpAttendanceAdapter(getActivity(), listEmp);
                    recycler_view.setAdapter(empAttendanceAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recycler_view.setLayoutManager(linearLayoutManager);
                    //  studentAttendanceAdapter.notifyDataSetChanged();
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);

                }
                else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectAllLeave = (Button) empAttView.findViewById(R.id.btnLeave);
        selectAllLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (typeKey != null && !typeKey.isEmpty()) {

                    //listStudents = getModel2(true);
                    listEmp = getModel(false, false, true);
                    //   listStudents = getModel1(false);

                    empAttendanceAdapter = new EmpAttendanceAdapter(getActivity(), listEmp);
                    recycler_view.setAdapter(empAttendanceAdapter);
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);
                    // studentAttendanceAdapter.notifyDataSetChanged();

              /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();*/

                }
                else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deselectAll = (Button) empAttView.findViewById(R.id.btnDeselect);
        deselectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (typeKey != null && !typeKey.isEmpty()) {


                    listEmp = getModel(false, false, false);
                    //  listStudents = getModel1(false);
                    //  listStudents = getModel2(false);


                    empAttendanceAdapter = new EmpAttendanceAdapter(getActivity(), listEmp);
                    recycler_view.setAdapter(empAttendanceAdapter);
                    sliding_layout.setParallaxOffset(0);
                    sliding_layout.setPanelHeight(120);
                    // studentAttendanceAdapter.notifyDataSetChanged();
              /*  LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                studentAttendanceAdapter.notifyDataSetChanged();*/
                }
                else {
                    Toast.makeText(getActivity(), "Please Select AtLeast One Student To Deselect...!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submitAttBtn = (Button) empAttView.findViewById(R.id.submitAtt);
        submitAttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String first = null;
                String presentSend = null;
                String absentSend = null;
                String leaveSend = null;
                String statusSend = "";
                JSONArray jsonArray = new JSONArray();
                JSONObject cartItemsObjedct;
                String present = "Present";
                String absent = "Absent";
                String onLeave = "On Leave";
                // stList1 = DemoCheckAdapter.studentsDetailsArrayList;
               /* List<DemoCheckBox> stList1 = ((DemoCheckAdapter) studentAttendanceAdapter)
                        .getStudentsDetailsList();*/
                //  if (stList1.size() > 0) {
                for (int i = 0; i < EmpAttendanceAdapter.empListArray.size(); i++) {
                    //   DemoCheckBox singleStudent1 = stList1.get(i);

                    StudentsDetailsAttModel singleStudent1 = EmpAttendanceAdapter.empListArray.get(i);

                    if (singleStudent1.getDemoSelected() == true) {

                        first = singleStudent1.getStdId();

                        //presentSend = singleStudent1.getPresentStatus();
                        statusSend = present;
                        //   absentSend=absentSend=singleStudent1.getAbsentStatus();
                        // leaveSend=leaveSend=singleStudent1.getOnLeaveStatus();


                        System.out.println("Check Latest Present data" + first + "" + presentSend);


                    } else if (singleStudent1.getAbsentSelected() == true) {
                        first = singleStudent1.getStdId();

                        //   absentSend = singleStudent1.getAbsentStatus();
                        statusSend = absent;


                        System.out.println("Check Latest Absent data" + first + "" + absentSend);


                    } else if (singleStudent1.getLeaveSelected() == true) {
                        first = singleStudent1.getStdId();

                        //  leaveSend = singleStudent1.getOnLeaveStatus();
                        statusSend = onLeave;


                        System.out.println("Check Latest on leave  data" + first + "" + leaveSend);

                    } else {

                    }

                    System.out.println("Check Latest on final array  data" + first + "" + presentSend + "" + absentSend + "" + leaveSend);


                    if (first != null) {


                        cartItemsObjedct = new JSONObject();
                        try {


                            cartItemsObjedct.putOpt("employee_id",
                                    first);

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


                        AddEmployeeBulkAttendence addEmployeeBulkAttendence = new AddEmployeeBulkAttendence(getActivity());

                        addEmployeeBulkAttendence.execute(String.valueOf(singleObject), schoolId);
                    } else {

                        Toast.makeText(getActivity(),


                                "Please Select Atleast one Employee: " + singleStudent1.getPresentStatus(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });







        recycler_view = (RecyclerView) empAttView.findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Employee Attendance");

        return empAttView;
    }

    private ArrayList<StudentsDetailsAttModel> getModel(boolean isSelect,boolean isAbsent,boolean isLeave){

        System.out.println("method");

        ArrayList<StudentsDetailsAttModel> list = new ArrayList<>();

        String check;



        for(int i = 0; i < EmpAttendanceAdapter.empList.size(); i++){
            System.out.println("loop"+i);

            StudentsDetailsAttModel model1 = EmpAttendanceAdapter.empList.get(i);
            check = model1.getStdName();

            model1.setDemoSelected(isSelect);
            model1.setAbsentSelected(isAbsent);
            model1.setLeaveSelected(isLeave);




            model1.setStdName(check);

            System.out.println("looping data name"+model1);
            list.add(model1);
        }
        return list;
    }


    @Override
    public void allEmployees(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            listEmp.clear();
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


                    StudentsDetailsAttModel studentsDetails = new StudentsDetailsAttModel(jsonObject.getString("employee_id"), jsonObject.getString("school_id"),
                           jsonObject.getString("job_category"), jsonObject.getString("qualification"),jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("joined_on"), jsonObject.getString("phone"), jsonObject.getString("phone"),
                            jsonObject.getString("experience"), jsonObject.getString("status"),false,false,false,demo,present,absent,onLeave);
                    listEmp.add(studentsDetails);

               /*     StudentsDetailsAttModel studentsDetails = new StudentsDetailsAttModel(jsonObject.getString("employee_id"), jsonObject.getString("school_id"),
                            empName, jsonObject.getString("job_category"), jsonObject.getString("qualification"), jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("experience"), jsonObject.getString("phone"), jsonObject.getString("joined_on"), jsonObject.getString("status"),false,jsonObject.getString("status"),false,false,false,present,absent,onLeave);
                    listEmp.add(studentsDetails);*/

                    count++;
                }
                //Count....
                String taskCount = "Employees (" + String.valueOf(listEmp.size()) + ")";
                textEmpCount.setText(taskCount);
                //
                empAttendanceAdapter = new EmpAttendanceAdapter(getActivity(), listEmp);
                recycler_view.setAdapter(empAttendanceAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                empAttendanceAdapter.notifyDataSetChanged();
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
    /*
    "{
  ""employee"": [
{
      ""_id"": ""59288dd7118a200cd45bc821"",
      ""employee_id"": ""SCH-EMP-12"",
      ""school_id"": ""SCH-9273"",
      ""first_name"": ""Jagadeesh"",
      ""last_name"": ""babu"",
      ""surname"": ""a"",
      ""dob"": ""20-12-1970"",
      ""gender"": ""Male"",
      ""qualification"": ""B.tech"",
      ""job_category"": ""test"",
      ""experience"": ""5 Years"",
      ""phone"": ""9999999999"",
      ""email"": ""test@mailinator.com"",
      ""profile_image"": ""test"",
      ""website"": ""https://www.google.co.in"",
      ""joined_on"": ""20-05-2017"",
      ""status"": 1,
      ""current_address"": [
        {
          ""cur_address"": ""test,Test ,xyx"",
          ""cur_city"": ""visakhapatnam"",
          ""cur_state"": ""Andhra pradesh"",
          ""cur_pincode"": ""530002"",
          ""cur_long"": ""83.3091597"",
          ""cur_lat"": ""17.7249237""
        }
      ],
      ""permanent_address"": [
        {
          ""perm_address"": ""test,test,xyz "",
          ""perm_city"": ""visakhapatnam"",
          ""perm_state"": ""Andhra pradesh"",
          ""perm_pincode"": ""530002"",
          ""perm_long"": ""83.3091597"",
          ""perm_lat"": ""83.3091597""
        }
      ]
    }
]
}"
     */
}
