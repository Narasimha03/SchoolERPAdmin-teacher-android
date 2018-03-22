package com.example.medianet.proschool.suresh.checkboxs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.checkattendance.AttSubModel;
import com.example.medianet.proschool.checkattendance.StudentCustomAdapter;

import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {
 
    private TextView tv;
    private ArrayList<AttSubModel> countryList;



    StudentCustomAdapter studentCustomAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        tv = (TextView) findViewById(R.id.tv);
        countryList= studentCustomAdapter.imageModelArrayList;
//countryList.clear();


        String statusSend = "";
        String present = "Present";
        String absent = "Absent";
        String onLeave = "On Leave";
        for (int i = 0; i < countryList.size(); i++){
            AttSubModel attSubModel = countryList.get(i);
            if(attSubModel.getSelected()){
                tv.setText(tv.getText() + "present " + attSubModel.getStudentId());
            }
            else if(attSubModel.getSelectedAbsent())
            {
                tv.setText(tv.getText() + "absent " + attSubModel.getStudentId());

            }
            else if(attSubModel.getSelectedLeave())


            {
                tv.setText(tv.getText() + "leave " + attSubModel.getStudentId());

            }


           /* if(StudentCustomAdapter.imageModelArrayList.get(i).getSelected()) {

               // if(StudentCustomAdapter.imageModelArrayList.get(i).getSelected()||StudentCustomAdapter.imageModelArrayList.get(i).getSelectedAbsent()||StudentCustomAdapter.imageModelArrayList.get(i).getSelectedLeave()) {
                tv.setText(tv.getText() + " " + StudentCustomAdapter.imageModelArrayList.get(i).getStudentId());
            }*/
        }

/*
        countryList1= studentCustomAdapter.imageModelArrayList;

        for (int i = 0; i < countryList1.size(); i++){
            AttSubModel attSubModel = countryList1.get(i);
            if(attSubModel.getSelectedAbsent()){
                tv.setText(tv.getText() + "Absent data " + attSubModel.getStudentId());
            }

        }


        countryList2= studentCustomAdapter.imageModelArrayList;
        for (int i = 0; i < countryList2.size(); i++){
            AttSubModel attSubModel = countryList2.get(i);
            if(attSubModel.getSelectedLeave()){
                tv.setText(tv.getText() + "On Leave " + attSubModel.getStudentId());
            }

        }*/
    }
}