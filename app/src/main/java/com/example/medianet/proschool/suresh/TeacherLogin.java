package com.example.medianet.proschool.suresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.medianet.proschool.R;

public class TeacherLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_login);

        Button dashboard = (Button)findViewById(R.id.teacher_signin);

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard = new Intent(TeacherLogin.this, QuickDashboardClass.class);
                startActivity(dashboard);
            }
        });

    }


}