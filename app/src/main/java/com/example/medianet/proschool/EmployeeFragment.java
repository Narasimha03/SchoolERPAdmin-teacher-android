package com.example.medianet.proschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 13-06-2017.
 */

public class EmployeeFragment extends Fragment implements SearchEMPBackTask.OnSearchEmp {

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
    Button search;

    public EmployeeFragment() {

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
        sliding_layout = (SlidingUpPanelLayout) employeeView.findViewById(R.id.sliding_layout);
        sliding_layout.setParallaxOffset(0);
        sliding_layout.setPanelHeight(0);
        emp_layout = (LinearLayout) employeeView.findViewById(R.id.emp_layout);
        textEmpCount = (TextView) employeeView.findViewById(R.id.textEmpCount);
        //
        editSearch = (EditText) employeeView.findViewById(R.id.editSearch);
        imgSearch = (ImageView) employeeView.findViewById(R.id.imgSearch);
        // type spinner....
        typeSpinner = (Spinner) employeeView.findViewById(R.id.typeSpinner);
        typeMap.put("", "-- Select --");
        typeMap.put("Teaching", "Teaching");
        typeMap.put("Non Teaching", "Non Teaching");
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
        // section spinner....
        genderSpinner = (Spinner) employeeView.findViewById(R.id.genderSpinner);
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
        });
        // year spinner....
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
        });
        // To Search Student....
        search = (Button) employeeView.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentsDetailsArrayList.clear();
                if (typeKey.isEmpty() || genderKey.isEmpty() || editSearch.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please provide all details....!", Toast.LENGTH_LONG).show();
                } else {
                    new SearchEMPBackTask(getActivity(), EmployeeFragment.this).execute(typeKey, genderKey, editSearch.getText().toString());
                }
            }
        });

        recycler_view = (RecyclerView) employeeView.findViewById(R.id.recycler_view);
        recycler_view.setVisibility(View.VISIBLE);
        recycler_view.setNestedScrollingEnabled(false);
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Employee");

        return employeeView;
    }

    @Override
    public void OnSearchEmp(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            studentsDetailsArrayList.clear();
            String empName;

            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    empName = jsonObject.getString("surname") + jsonObject.getString("first_name") + jsonObject.getString("last_name");

                    StudentsDetails studentsDetails = new StudentsDetails(jsonObject.getString("employee_id"), jsonObject.getString("school_id"),
                            empName, jsonObject.getString("job_category"), jsonObject.getString("qualification"), jsonObject.getString("dob"), jsonObject.getString("gender"),
                            jsonObject.getString("experience"), jsonObject.getString("phone"), jsonObject.getString("joined_on"), jsonObject.getString("status"),false,jsonObject.getString("status"));
                    studentsDetailsArrayList.add(studentsDetails);

                    count++;
                }
                //Count....
                String taskCount = "Employees (" + String.valueOf(studentsDetailsArrayList.size()) + ")";
                textEmpCount.setText(taskCount);
                //
                studentDetailsAdapter = new StudentDetailsAdapter(getActivity(), studentsDetailsArrayList);
                recycler_view.setAdapter(studentDetailsAdapter);
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
    }
    /*
    "[
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
]"
     */
}
