package com.example.medianet.proschool;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.medianet.proschool.suresh.timetable.ViewTimeTableClassFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 13-06-2017.
 */

public class EmployeeDetailsFragment extends Fragment{

    View empDetailsView;
    //
    EditText editFirstName, editLastName, editMobile, editEmail, editDob, editExp, editDOJ, editJobProfile;
    // Gender Spinner....
    String genderKey, selectedGender;
    Spinner genderSpinner;
    Map<String, String> genderMap = new LinkedHashMap<String, String>();
    ArrayList<String> genderList;
    ArrayAdapter<String> genderAdapter;
    // Qualification Spinner...
    String qualKey, qualSelection;
    Spinner qualificationSpinner;
    Map<String, String> qualMap = new LinkedHashMap<String, String>();
    ArrayList<String> qualList;
    ArrayAdapter<String> qualAdapter;
    // Subject Spinner...
    MultiSelectionSpinner subjectSpinner;
    Map<String, String> mapSubject = new HashMap<String, String>();
    Map<String, String> treeSubject;
    ArrayList<String> arraySubject;
    // Button...
    Button saveBtn;
    String schoolId;
    Context mContext;

    public EmployeeDetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        empDetailsView = inflater.inflate(R.layout.employee_details_layout, container, false);
        mContext=getActivity();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");

        // section spinner....
        genderSpinner = (Spinner) empDetailsView.findViewById(R.id.genderSpinner);
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
                selectedGender = genderSpinner.getSelectedItem().toString();
                genderKey = (String) getKeyFromValue(genderMap, selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // qualification spinner....
        qualificationSpinner = (Spinner) empDetailsView.findViewById(R.id.qualificationSpinner);
        qualMap.put("", "-- Select --");
        qualMap.put("M.Tech", "M.Tech");
        qualMap.put("B.Tech", "B.Tech");
        qualList = new ArrayList<String>(qualMap.values());

        qualAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, qualList);
        qualAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qualificationSpinner.setAdapter(qualAdapter);
        qualAdapter.notifyDataSetChanged();
        qualificationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                qualSelection = qualificationSpinner.getSelectedItem().toString();
                qualKey = (String) getKeyFromValue(qualMap, qualSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // subject spinner...
        subjectSpinner = (MultiSelectionSpinner) empDetailsView.findViewById(R.id.subjectSpinner);
        mapSubject.put("", "-- Select");
        mapSubject.put("Telugu", "Telugu");
        mapSubject.put("Hindi", "Hindi");
        mapSubject.put("English", "English");
        mapSubject.put("Maths", "Maths");
        mapSubject.put("Science", "Science");
        mapSubject.put("Social", "Social");
        treeSubject = new TreeMap<String, String>(mapSubject);
        arraySubject = new ArrayList<String>(mapSubject.values());
        Collections.sort(arraySubject);
        subjectSpinner.setItems(arraySubject);
        subjectSpinner.setMap(mapSubject);
        //dob
        editDob = (EditText) empDetailsView.findViewById(R.id.editDob);
        editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerFragement(), years, month, day);
                datePickerDialog.show();
            }
        });
        //admission date
        editDOJ = (EditText) empDetailsView.findViewById(R.id.editDOJ);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        editDOJ.setText(date);
        editDOJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerFragement2(), years, month, day);
                datePickerDialog.show();
            }
        });
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Employee Details");

        return empDetailsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editFirstName = (EditText) empDetailsView.findViewById(R.id.editFirstName);
        editLastName = (EditText) empDetailsView.findViewById(R.id.editLastName);
        editMobile = (EditText) empDetailsView.findViewById(R.id.editMobile);
        editEmail = (EditText) empDetailsView.findViewById(R.id.editEmail);
        editExp = (EditText) empDetailsView.findViewById(R.id.editExp);
        editJobProfile = (EditText) empDetailsView.findViewById(R.id.editJobProfile);
        saveBtn = (Button) empDetailsView.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject saveObject = new JSONObject();
                try {
                    saveObject.put("first_name", editFirstName.getText().toString());
                    saveObject.put("last_name", editLastName.getText().toString());
                    saveObject.put("gender", selectedGender);
                    saveObject.put("dob", editDob);
                    saveObject.put("gender", genderKey);
                    saveObject.put("qualification", qualKey);
                    saveObject.put("experience", editExp.getText().toString());
                    saveObject.put("phone", editMobile.getText().toString());
                    saveObject.put("email", editEmail.getText().toString());
                    saveObject.put("joined_on", editDOJ);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (saveObject.length() > 0){
                    EmpADMBackTask empADMBackTask = new EmpADMBackTask(getActivity());
                    empADMBackTask.execute(String.valueOf(saveObject), schoolId);
                   /* StdADMBackTask stdADMBackTask = new StdADMBackTask(getActivity());
                    stdADMBackTask.execute(String.valueOf(saveObject), sectionKey); */
                }
            }
        });
    }

    public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editDob.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }

    public class DatePickerFragement2 implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editDOJ.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }
}
