package com.example.medianet.proschool.Harish;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.medianet.proschool.AddExamSchBackTask;
import com.example.medianet.proschool.AllChapterBackTask;
import com.example.medianet.proschool.AllExamSchBackTask;
import com.example.medianet.proschool.AllSubjectBackTask;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.ExamScheduleFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.SectionBackGroundTask;
import com.example.medianet.proschool.suresh.activity.AddAssigmentFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by Harish on 27-12-2017.
 */

public class AddScheduleFragment extends AppCompatActivity {

    String schoolId;
    Context mContext;
    EditText editTitle, editStartDate, editEndDate;
    // Button...
    Button addSchedule;
    View addExamScheduleView;

    public AddScheduleFragment() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exam_schedule_layout);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.test_toolabr);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        //new ClassBackGroundTask(getActivity(), AddScheduleFragment.this).execute(schoolId);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editStartDate = (EditText) findViewById(R.id.editStartDate);
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerFragement(), years, month, day);
                datePickerDialog.show();
            }
        });
        editEndDate = (EditText) findViewById(R.id.editEndDate);
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new AddScheduleFragment.DatePickerFragement2(), years, month, day);
                datePickerDialog.show();
            }
        });

        addSchedule = (Button) findViewById(R.id.addSchedule);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject addObject = new JSONObject();
                try {
                    addObject.put("exam_title", editTitle.getText().toString());
                    //addObject.put("exam_classes", classKey);
                    addObject.put("from_date", editStartDate.getText().toString());
                    System.out.println("addexam schedule"+addObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new AddExamSchBackTask(mContext).execute(String.valueOf(addObject), schoolId);
            }
        });
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Exam Schedule");

    }



    public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editStartDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }

    public class DatePickerFragement2 implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editEndDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }
}
