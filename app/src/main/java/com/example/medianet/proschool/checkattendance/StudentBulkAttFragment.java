package com.example.medianet.proschool.checkattendance;

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

import com.example.medianet.proschool.AddStudentBulkAttendence;
import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.StudentAttandenceModel;
import com.example.medianet.proschool.StudentAttendanceAdapter;
import com.example.medianet.proschool.StudentAttendenceFragment;
import com.example.medianet.proschool.StudentDetailsAdapter;
import com.example.medianet.proschool.StudentsDetails;
import com.example.medianet.proschool.suresh.checkboxs.CustomAdapter;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileTabsFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 06-06-2017.
 */

public class StudentBulkAttFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,
        AllStudentsBackTask.AllStudents,StudentCustomAdapter.ItemClickStudentProfileListener {

    private RecyclerView recyclerView;
    private ArrayList<AttSubModel> modelArrayList=new ArrayList<>();
    private StudentCustomAdapter customAdapter;
    private Button btnPresent, btndeselect, submitAtt,absentAll,leaveAll;
    private  String[] animallist = new String[]{"Lion", "Tiger", "Leopard", "Cat"};



    ArrayList<AttSubModel> arrPresent;
    ArrayList<AttSubModel> arrAbsent;
    ArrayList<AttSubModel> arrLeave;
    AttSubModel model;




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

    StudentCustomAdapter studentCustomAdapter=null;
    SharedPreferences sharedPreferences;

    public StudentBulkAttFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        studentAttendanceView = inflater.inflate(R.layout.student_check_attendence_layout, container, false);
        mContext = getActivity();

        sliding_layout = (SlidingUpPanelLayout) studentAttendanceView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        recycler_view = (RecyclerView)studentAttendanceView.findViewById(R.id.recycler_view);
        textStdCount = (TextView)studentAttendanceView.findViewById(R.id.textStdCount);
        std_layout = (LinearLayout) studentAttendanceView.findViewById(R.id.std_layout);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new ClassBackGroundTask(getActivity(), StudentBulkAttFragment.this).execute(schoolId);

        // class spinner....
        classSpinner = (Spinner) studentAttendanceView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        //listStudents = getModel(false);


        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), StudentBulkAttFragment.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner) studentAttendanceView.findViewById(R.id.sectionSpinner);
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
        search = (FloatingActionButton) studentAttendanceView.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList.clear();
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    new AllStudentsBackTask(mContext, StudentBulkAttFragment.this).execute(sectionKey);
                } else {
                    Toast.makeText(getActivity(), "Please Select Section...!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        modelArrayList = getModel(false);
        modelArrayList = getModel1(false);
        modelArrayList = getModel2(false);

        btnPresent = (Button) studentAttendanceView.findViewById(R.id.btnPresent);

        btnPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(true);
                //  modelArrayList = getModel1(false);
                //modelArrayList = getModel2(false);

                customAdapter = new StudentCustomAdapter(getActivity(),modelArrayList);
                recycler_view.setAdapter(customAdapter);
            }
        });
        absentAll = (Button) studentAttendanceView.findViewById(R.id.btnAbsent);
        absentAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel1(true);
                // modelArrayList = getModel(false);
                //modelArrayList = getModel2(false);

                customAdapter = new StudentCustomAdapter(getActivity(),modelArrayList);
                recycler_view.setAdapter(customAdapter);
            }
        });
        leaveAll = (Button) studentAttendanceView.findViewById(R.id.btnLeave);

        leaveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel2(true);
                // modelArrayList = getModel(false);
                //  modelArrayList = getModel1(false);

                customAdapter = new StudentCustomAdapter(getActivity(),modelArrayList);
                recycler_view.setAdapter(customAdapter);
            }
        });

        btndeselect = (Button) studentAttendanceView.findViewById(R.id.btnDeselect);
        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(false);
                customAdapter = new StudentCustomAdapter(getActivity(),modelArrayList);
                recycler_view.setAdapter(customAdapter);
            }
        });


        submitAtt = (Button) studentAttendanceView.findViewById(R.id.holiday);
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
                bulkAttArray= studentCustomAdapter.imageModelArrayList;

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


                        cartItemsObjedct.putOpt("student_id",
                                studentId);

                        cartItemsObjedct.putOpt("status", statusSend);
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


                    AddStudentBulkAttendence addStudentBulkAttendence = new AddStudentBulkAttendence(mContext);

                    addStudentBulkAttendence.execute(String.valueOf(singleObject), classKey, sectionKey, schoolId);

                }
            }
        });

        return studentAttendanceView;
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
            classSpinner.destroyDrawingCache();
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
            sectionSpinner.destroyDrawingCache();
        }
    }

    @Override
    public void allStudents(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            modelArrayList.clear();
            String stdName;
            String parentName = "";
            JSONObject stdObject = new JSONObject(result);
            JSONArray jsonArray = stdObject.getJSONArray("students");
            if (stdObject.length() > 0) {
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
                    String present = "Present";
                    String absent = "Absent";
                    String onLeave = "On Leave";
                    String demo = "Demo";


                    AttSubModel studentsDetails = new AttSubModel(jsonObject.getString("student_id"), jsonObject.getString("admission_no"),
                            stdName, jsonObject.getString("class_id"), jsonObject.getString("dob"), jsonObject.getString("gender"), jsonObject.getString("roll_no"), jsonObject.getString("category"));

                    modelArrayList.add(studentsDetails);

                   /* DemoCheckBox studentsDetails = new DemoCheckBox(jsonObject.getString("student_id"), jsonObject.getString("admission_no"),
                            stdName, jsonObject.getString("class_id"), parentName, jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("category"), jsonObject.getString("phone"), jsonObject.getString("phone"),
                            jsonObject.getString("roll_no"), demo, present, absent, onLeave);
                    listStudents.add(studentsDetails);*/

                    count++;
                }
                //Count....
                String taskCount = "Students (" + String.valueOf(modelArrayList.size()) + ")";
                //   int taskCount1 = listStudents.size();

                textStdCount.setText(taskCount);
                //
                customAdapter = new StudentCustomAdapter(getActivity(), modelArrayList);
                recycler_view.setAdapter(customAdapter);
                customAdapter.setClickListener(this);

                // recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                customAdapter.notifyDataSetChanged();
                recycler_view.destroyDrawingCache();
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

    @Override
    public void onClickProfile(View view, int position) {


        final AttSubModel city = modelArrayList.get(position);
        String studentId=city.getStudentId();

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
