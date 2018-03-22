package com.example.medianet.proschool.suresh.checkboxs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.medianet.proschool.R;

import java.util.ArrayList;
 
public class MainActivity extends AppCompatActivity {
 
    private RecyclerView recyclerView;
    private ArrayList<Model> modelArrayList;
    private CustomAdapter customAdapter;
    private Button btnselect, btndeselect, btnnext,btnSelectPresent,btnRadio;
    private  String[] animallist = new String[]{"Lion", "Tiger", "Leopard", "Cat"};
    private  String[] animallist1 = new String[]{"Present1", "Present2", "Present3", "Present4"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_checkbox);
 
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        btnselect = (Button) findViewById(R.id.select);
        btndeselect = (Button) findViewById(R.id.deselect);
        btnnext = (Button) findViewById(R.id.next);

        btnSelectPresent = (Button) findViewById(R.id.selectPresent);
        btnRadio = (Button) findViewById(R.id.btnRadio);


        modelArrayList = getModel(false);
        modelArrayList = getPresent(false);

        customAdapter = new CustomAdapter(this,modelArrayList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
 
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getPresent(false);

                modelArrayList = getModel(true);

                customAdapter = new CustomAdapter(MainActivity.this,modelArrayList);
                recyclerView.setAdapter(customAdapter);
            }
        });
        btnRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getPresent(false);

                modelArrayList = getModel(true);

                customAdapter = new CustomAdapter(MainActivity.this,modelArrayList);
                recyclerView.setAdapter(customAdapter);
            }
        });


        btnSelectPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(false);
                modelArrayList = getPresent(true);
                customAdapter = new CustomAdapter(MainActivity.this,modelArrayList);
                recyclerView.setAdapter(customAdapter);
            }
        });

        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelArrayList = getModel(false);
                modelArrayList = getPresent(false);

                customAdapter = new CustomAdapter(MainActivity.this,modelArrayList);
                recyclerView.setAdapter(customAdapter);
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NextActivity.class);
                startActivity(intent);
            }
        });
 
 
    }
 
    private ArrayList<Model> getModel(boolean isSelect){
        ArrayList<Model> list = new ArrayList<>();
        for(int i = 0; i < 4; i++){
 
            Model model = new Model();
            model.setSelected(isSelect);
            model.setAnimal(animallist[i]);
            list.add(model);
        }
        return list;
    }
    private ArrayList<Model> getPresent(boolean isSelect){
        ArrayList<Model> list1 = new ArrayList<>();
        for(int i = 0; i < 4; i++){

            Model model = new Model();
            model.setSelected(isSelect);
            model.setAnimal(animallist1[i]);
            list1.add(model);
        }
        return list1;
    }
}