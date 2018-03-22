package com.example.medianet.proschool;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by USER on 11/21/2017.
 */

public class EditorDeleteVehicleFragment extends Fragment {


    View editorDeleteVehicle;
    EditText editCode,editNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        editorDeleteVehicle = inflater.inflate(R.layout.add_vehicle_dialog, container, false);

        editCode = (EditText) editorDeleteVehicle.findViewById(R.id.editCode);
        editNumber = (EditText) editorDeleteVehicle.findViewById(R.id.editNumber);

        Bundle b = getArguments();
        String id = b.getString("id");
        String code = b.getString("code");
        String name = b.getString("name");
        editCode.setText(code);
        editNumber.setText(name);
return editorDeleteVehicle;
    }
}
