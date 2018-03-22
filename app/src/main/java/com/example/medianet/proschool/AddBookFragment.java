package com.example.medianet.proschool;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.medianet.proschool.MultiSelectionSpinner.getKeyFromValue;

/**
 * Created by JANI on 03-06-2017.
 */

public class AddBookFragment extends Fragment implements AllSubjectBackTask.OnSubjectResponse{

    View addBookView;
    //
    EditText editTitle, editAuthorName, editPrice, editQTY, editRackNo, editDate, editDescription;
    // Subject Spinner...
    String subjectkey, selectedSubject;
    Spinner subjectSpinner;
    Map<String, String> subjectMap = new LinkedHashMap<String, String>();
    ArrayList<String> subjectList;
    ArrayAdapter<String> subjectAdapter;
    //
    Button addBook;
    Context mContext;
    String schoolId;

    public AddBookFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        addBookView = inflater.inflate(R.layout.add_book_layout, container, false);
        mContext=getActivity();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");

        subjectSpinner = (Spinner) addBookView.findViewById(R.id.subjectSpinner);
        subjectMap.put("", "");
        subjectList = new ArrayList<>(subjectMap.values());
        //
        subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectList);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);
        subjectAdapter.notifyDataSetChanged();
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjectSpinner.getSelectedItem().toString();
                subjectkey = (String) getKeyFromValue(subjectMap, selectedSubject);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Book");

        return addBookView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editTitle = (EditText) addBookView.findViewById(R.id.editTitle);
        editAuthorName = (EditText) addBookView.findViewById(R.id.editAuthorName);
        editPrice = (EditText) addBookView.findViewById(R.id.editPrice);
        editQTY = (EditText) addBookView.findViewById(R.id.editQTY);
        editRackNo = (EditText) addBookView.findViewById(R.id.editRackNo);
        editDate = (EditText) addBookView.findViewById(R.id.editDate);
        editDescription = (EditText) addBookView.findViewById(R.id.editDescription);
        editDate.setOnClickListener(new View.OnClickListener() {
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
        addBook = (Button) addBookView.findViewById(R.id.addBook);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject addBook = new JSONObject();
                try {
                    addBook.put("book_title", editTitle.getText().toString());
                    addBook.put("author_name", editAuthorName.getText().toString());
                    addBook.put("book_price", editPrice.getText().toString());
                    addBook.put("qty", editQTY.getText().toString());
                    addBook.put("rack_number", editRackNo.getText().toString());
                    addBook.put("inward_date", editDate.getText().toString());
                    addBook.put("book_description", editDescription.getText().toString());
                    addBook.put("subject", selectedSubject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (addBook.length() > 0){
                    new AddBookBackTask(getActivity()).execute(String.valueOf(addBook), schoolId);
                }
            }
        });
        /*
        "{
""book_title"":""Test Book"",
""author_name"":""Jagadeesh"",
""book_price"":""500"",
""qty"":""2"",
""rack_number"":""21"",
""inward_date"":""16-05-2017"",
""book_description"":""This is test description"",
""subject"":""Economics""}"
         */
    }

    @Override
    public void OnSubjectResponse(String response) throws JSONException {

    }

    public class DatePickerFragement implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int years = year;
            int month = monthOfYear;
            int day = dayOfMonth;
            editDate.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(years).append(""));
        }
    }
}
