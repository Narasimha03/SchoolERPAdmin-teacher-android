package com.example.medianet.proschool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JANI on 06-06-2017.
 */

public class SearchExpenseFragment extends Fragment{
    public SearchExpenseFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View searchExpenseView = inflater.inflate(R.layout.search_expense_layout, container, false);
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Search Expense");
        return searchExpenseView;
    }
}
