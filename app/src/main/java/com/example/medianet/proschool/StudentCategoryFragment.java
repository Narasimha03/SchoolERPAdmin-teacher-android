package com.example.medianet.proschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by JANI on 08-05-2017.
 */

public class StudentCategoryFragment extends Fragment {
    //Recylerview....
    RecyclerView recycler_view;
    ArrayList<Categories> categoriesArrayList = new ArrayList<Categories>();
    CategoryAdapter categoryAdapter;

    public StudentCategoryFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View studentCategoryView = inflater.inflate(R.layout.student_category_layout, container, false);
        //Recycler View
        recycler_view = (RecyclerView) studentCategoryView.findViewById(R.id.recycler_view);
        Categories categories = new Categories("1", "General");
        Categories categories1 = new Categories("1", "Written Test");
        Categories categories2 = new Categories("1", "PH");
        Categories categories3 = new Categories("1", "Special Category");
        categoriesArrayList.add(categories);
        categoriesArrayList.add(categories1);
        categoriesArrayList.add(categories2);
        categoriesArrayList.add(categories3);
        categoryAdapter = new CategoryAdapter(getActivity(), categoriesArrayList);
        recycler_view.setAdapter(categoryAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        categoryAdapter.notifyDataSetChanged();
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Student Information");
        return studentCategoryView;
    }
}
