package com.example.medianet.proschool.suresh.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.medianet.proschool.AddVehicleFragment;
import com.example.medianet.proschool.R;


/**
 * Created by USER on 11/2/2017.
 */


public class VehiclesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.vehicle_frame, new AddVehicleFragment())
                    .commit();
        }
    }

}