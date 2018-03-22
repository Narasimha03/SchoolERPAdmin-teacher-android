package com.example.medianet.proschool.suresh.feemodule.feecollection;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.Harish.AddScheduleFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.suresh.feemodule.AllFeeTypeBackTask;
import com.example.medianet.proschool.suresh.feemodule.FeeAmountByFeeTypeBackTask;
import com.example.medianet.proschool.suresh.feemodule.FeeTypeFragmentMain;
import com.example.medianet.proschool.suresh.feemodule.FeeTypesByClassBackTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by Harish on 29-12-2017.
 */

public class AddFeeCollectionFragment extends Fragment implements ClassBackGroundTask.OnClassResponse, SectionBackGroundTask.OnSectionResponse,

        AllStudentsBackTask.AllStudents, AllFeeCollectionBackTask.AllFeeCollection,FeeTypesByClassBackTask.FeeTypesByClassId,FeeAmountByFeeTypeBackTask.FeeAmountByFeeType {

    // Button...
    View addFeeView;
    TextView textExamCount,amount;
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

    String amountKey, selectedAmount;
    Spinner amountSpinner;
    Map<String, String> amountMap = new LinkedHashMap<String, String>();
    ArrayList<String> amountList;
    ArrayAdapter<String> amountAdapter;

    //
    EditText editMarks, editPercent,feePaid;
    //
    String conductkey,feeTypeKey, selectedConduct,selectedConduct1;
    Spinner conductSpinner,feeTypeSpinner,feeAmount;
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
    ArrayList<FeeCollection> listMarks = new ArrayList<FeeCollection>();
    FeeCollectionAdapter feeCollectionAdapter;
    Context mContext;
    String schoolId;

    public AddFeeCollectionFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addFeeView = inflater.inflate(R.layout.all_fee_collection_add_fee, container, false);
        mContext = getActivity();
        feePaid = (EditText) addFeeView.findViewById(R.id.feePaid);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        new ClassBackGroundTask(getActivity(), AddFeeCollectionFragment.this).execute(schoolId);
       // new AllFeeTypeBackTask(getActivity(), AddFeeCollectionFragment.this).execute(schoolId);
        // class spinner....
        classSpinner = (Spinner) addFeeView.findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(getActivity(), AddFeeCollectionFragment.this).execute(classKey);
                    new FeeTypesByClassBackTask(getActivity(),AddFeeCollectionFragment.this).execute(classKey);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // section spinner....
        sectionSpinner = (Spinner) addFeeView.findViewById(R.id.sectionSpinner);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                // Getting subjects, Students....
                if (sectionKey != null && !sectionKey.isEmpty()) {
                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                    new AllStudentsBackTask(getActivity(), AddFeeCollectionFragment.this).execute(sectionKey);
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
        studentSpinner = (Spinner) addFeeView.findViewById(R.id.studentSpinner);
        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStudent = studentSpinner.getSelectedItem().toString();
                studentkey = (String) getKeyFromValue(studentMap, selectedStudent);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Conduct Spinner....
        conductSpinner = (Spinner) addFeeView.findViewById(R.id.conductSpinner);
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
        feeTypeSpinner = (Spinner) addFeeView.findViewById(R.id.feeType);
        feeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedConduct1 = feeTypeSpinner.getSelectedItem().toString();
                feeTypeKey = (String) getKeyFromValue(conductMap1, selectedConduct1);

                if (feeTypeKey != null && !feeTypeKey.isEmpty()) {
                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                    new FeeAmountByFeeTypeBackTask(getActivity(), AddFeeCollectionFragment.this).execute(feeTypeKey,classKey);
                    //  new AllExamPaperBackTask(getActivity(), ExamEvaluationFragment.this).execute(examScheduleKey, sectionKey);

                } else {
                    Toast.makeText(getActivity(), "Please Select Section.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        amountSpinner=(Spinner) addFeeView.findViewById(R.id.feeAmount);
        amountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAmount = amountSpinner.getSelectedItem().toString();
                amountKey = (String) getKeyFromValue(amountMap, selectedAmount);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





      /*  editDate = (EditText) addFeeView.findViewById(R.id.editDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new AddFeeCollectionFragment.DatePickerFragement(), years, month, day);
                datePickerDialog.show();
            }
        });*/

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Fee");
        return addFeeView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //
        editMarks = (EditText) addFeeView.findViewById(R.id.editMarks);
        editPercent = (EditText) addFeeView.findViewById(R.id.editPercent);
        //
        addMarks = (Button) addFeeView.findViewById(R.id.addMarks);




        addMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject addObject = new JSONObject();
                try {
                    addObject.put("fine", editMarks.getText().toString());
                    addObject.put("discount", editPercent.getText().toString());
                    addObject.put("fee_types_id", feeTypeKey);
                    addObject.put("payment_mode", conductkey);
                   addObject.put("fee_paid", feePaid.getText().toString());
                    addObject.put("class_id", classKey);

                    System.out.println("addFeeCollection"+addObject);

                    // addObject.put("conduct", "comment");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AddFeeCollectionBackTask addFeeCollectionBackTask = new AddFeeCollectionBackTask(getActivity());
                addFeeCollectionBackTask.execute(String.valueOf(addObject), studentkey);
            }
              /*  if (addObject.length() > 0) {
                    if (studentkey!= null) {
                        new AddFeeCollectionBackTask(getActivity()).execute(String.valueOf(addObject),studentkey);
                    } else {
                        Toast.makeText(getActivity(), "Please select Student", Toast.LENGTH_LONG).show();
                    }
                }*/

        });
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






    @Override
    public void allStudents(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            studentMap.clear();
            studentMap.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("students");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject stdObject = jsonArray.getJSONObject(count);
                String name = stdObject.getString("first_name") + " " + stdObject.getString("last_name");
                studentMap.put(stdObject.getString("student_id"), name);
                count++;
            }
            studentList = new ArrayList<String>(studentMap.values());
            //
            studentAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, studentList);
            studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            studentSpinner.setAdapter(studentAdapter);
            studentAdapter.notifyDataSetChanged();
        }
    }




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
                    System.out.println("total"+totalValue);
                    //paper_result_id
                  /*  FeeCollection evalMarks = new FeeCollection(markObject.getString("_id"), markObject.getString("exam_paper_id"),
                            selectedPaper, selectedStudent, markObject.getString("marks"),
                            markObject.getString("percentage"), markObject.getString("conduct"), markObject.getString("paper_name"),
                            markObject.getString("first_name"), markObject.getString("examschedule_name"), selectedExamSchedule, selectedSubject);*/
                    FeeCollection feeCollectionmodel=new FeeCollection(markObject.getString("fee_type"),markObject.getString("fee_category"),markObject.getString("payment_mode"),markObject.getString("fee_amount"),markObject.getString("fine"),markObject.getString("discount"),totalValue,markObject.getString("fee_paid"));
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
            } else {
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }

    }

/*    @Override
    public void allVehicle(String result) throws JSONException {

        if (result != null && !result.isEmpty()) {
            conductMap1.clear();
            conductMap1.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("feetypes");
            Log.e("fee collection type", String.valueOf(jsonArray));
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject stdObject = jsonArray.getJSONObject(count);
                // JSONObject examObject = jsonArray.getJSONObject(count);


                conductMap1.put(stdObject.getString("fee_type"), stdObject.getString("fee_type"));


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



    }*/

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
    public void feeAmountByFeeTypeId(String feeTypesId) throws JSONException {

        if (feeTypesId != null && !feeTypesId.isEmpty()) {
           amountMap.clear();
           // conductMap1.put("", "-- select --");
            JSONObject jsonObject = new JSONObject(feeTypesId);
            Log.e("fee type by ", String.valueOf(jsonObject));

            JSONArray jsonArray = jsonObject.getJSONArray("feemaster");
            Log.e("fee collection amount", String.valueOf(jsonArray));
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject stdObject = jsonArray.getJSONObject(count);
                // JSONObject examObject = jsonArray.getJSONObject(count);

               amountMap.put(stdObject.getString("fee_types_id"), stdObject.getString("fee_amount"));
               // stdObject.getString("fee_types_id");
             //  amount.setText(amountShow);
                // String name = stdObject.getString("first_name") + " " + stdObject.getString("last_name");
                //  conductMap1.put(stdObject.getString("student_id"), name);
                count++;
            }

           amountList = new ArrayList<String>(amountMap.values());
            //
            amountAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, amountList);
            amountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            amountSpinner.setAdapter(amountAdapter);
            amountAdapter.notifyDataSetChanged();
        }



    }


  /*  public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }*/
}
