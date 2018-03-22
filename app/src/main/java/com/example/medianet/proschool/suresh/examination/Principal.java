package com.example.medianet.proschool.suresh.examination;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.medianet.proschool.AllExamSchBackTask;
import com.example.medianet.proschool.AllStudentsBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.medianet.proschool.Constants.studentId;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

public class Principal extends AppCompatActivity implements ClassBackGroundTask.OnClassResponse,SectionBackGroundTask.OnSectionResponse,AllStudentsBackTask.AllStudents {

    RecyclerView rvPrueba;
    Button btnCalcular;
    List<BulkEvaluationModel> lista = new ArrayList<BulkEvaluationModel>();
   // PruebaAdapter adapter;
    SelectItemAdapter selectItemAdapter;
    boolean bandera = true;




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
    Context mContext;
    String schoolId;
    String role;
    RecyclerView recycler_view;

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //rvPrueba = (RecyclerView) findViewById(R.id.rvPrueba);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);


        mContext =this;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        role = sharedPreferences.getString(Constants.rolePref, "");
        new ClassBackGroundTask(mContext, Principal.this).execute(schoolId);

        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setNestedScrollingEnabled(false);




        // class spinner....
        classSpinner = (Spinner)findViewById(R.id.classSpinner);
        classMap.put("", "-- Select --");
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = classSpinner.getSelectedItem().toString();
                classKey = (String) getKeyFromValue(classMap, selectedClass);
                // Getting Sections From Server....
                if (classKey != null && !classKey.isEmpty()) {
                    new SectionBackGroundTask(mContext, Principal.this).execute(classKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // section spinner....
        sectionSpinner = (Spinner)findViewById(R.id.sectionSpinner);
        sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSection = sectionSpinner.getSelectedItem().toString();
                sectionKey = (String) getKeyFromValue(sectionMap, selectedSection);
                // Getting subjects, Students....

                if (sectionKey != null && !sectionKey.isEmpty()) {
                    //  new AllSubjectBackTask(getActivity(), ExamEvaluationFragment.this).execute(sectionKey);
                    new AllStudentsBackTask(mContext, Principal.this).execute(sectionKey);
                   // new AllExamSchBackTask(mContext, Principal.this).execute(schoolId);

                    //  new AllExamPaperBackTask(getActivity(), ExamEvaluationFragment.this).execute(examScheduleKey, sectionKey);

                } else {
                    Toast.makeText(mContext, "Please Select Section.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   String[] escrito = adapter.getEscrito();

                lista= selectItemAdapter.lista;

                String studentId;
                for (int i = 0; i < lista.size(); i++) {
                    BulkEvaluationModel bulkEvaluationModel = lista.get(i);

                  //  if (bulkEvaluationModel.getReadEdValue()!=null&&!bulkEvaluationModel.getReadEdValue().isEmpty()) {

                        studentId=bulkEvaluationModel.getStdName();
                     //   statusSend=bulkEvaluationModel.getReadEdValue();
                        Log.e("read data",studentId);

                    }
              SelectItemAdapter selectItemAdapter1 =  new SelectItemAdapter(lista);
                int quantities [] = selectItemAdapter.getQuantities();
                System.out.println("arr: " + Arrays.toString(quantities));

    //       String s=     quantities.toString();
         //       Log.e("check data position", s);
            /*    int resultado = 0;

                for(int i = 0;i< escrito.length;i++){
                    if(escrito[i] == null){
                         bandera = false;
                         return;
                    }
                    else{
                        if(escrito[i].equals("")){
                            bandera=false;
                            return;
                        }
                    }
                }

                if(bandera){
                    for(int i = 0;i< escrito.length;i++){
                      //  resultado+= Integer.parseInt(escrito.get(i).getStdId());
                        String s=escrito[i];
                        Log.e("testing",s);

                    }

                    Toast.makeText(getApplicationContext(),"Resultado : " + resultado,Toast.LENGTH_LONG).show();
                }


            }*/
            }
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
            classAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, classList);
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
            sectionAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, sectionList);
            sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sectionSpinner.setAdapter(sectionAdapter);
            sectionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void allStudents(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {
            lista.clear();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("evalobject"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("students");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("alleval"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);
                    String maxMarks="100";
                    String name = markObject.getString("first_name") + " " + markObject.getString("last_name");

                    //paper_result_id
                    BulkEvaluationModel evalMarks = new BulkEvaluationModel(markObject.getString("student_id"), maxMarks, markObject.getString("student_id"));

                    lista.add(evalMarks);
                    count++;
                }
                //Count....
                //String taskCount = "ExamEvaluations (" + String.valueOf(listMarks.size()) + ")";
                //textExamCount.setText(taskCount);
                //
                recycler_view.setVisibility(View.VISIBLE);
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                recycler_view.setLayoutManager(llm);

                selectItemAdapter = new SelectItemAdapter(lista);
                recycler_view.setAdapter(selectItemAdapter);
                //selectItemAdapter.notifyDataSetChanged();
                /*bulkMarksAdpter = new BulkMarksAdpter(mContext, listMarks);
                recycler_view.setAdapter(bulkMarksAdpter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                bulkMarksAdpter.notifyDataSetChanged();*/
                //
              //  sliding_layout.setParallaxOffset(0);
              //  sliding_layout.setPanelHeight(120);
            } else {
               // sliding_layout.setParallaxOffset(0);
               // sliding_layout.setPanelHeight(0);
               // recycler_view.setVisibility(View.GONE);
                Toast.makeText(mContext, "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }
    }


}
