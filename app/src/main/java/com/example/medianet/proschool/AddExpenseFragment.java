package com.example.medianet.proschool;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 06-06-2017.
 */

public class AddExpenseFragment extends Fragment {
    //Content Type Spinner....
    Spinner expenseHeadSpinner;
    List<String> expenseHeadList;
    ArrayAdapter<String> expenseHeadAdapter;
    //Recyclerview....
    RecyclerView expenseRecylerView;
    ExpenseAdapter expenseAdapter;
    ArrayList<Expense> listExpense = new ArrayList<Expense>();
    //FAB...
    FloatingActionButton fab;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    public AddExpenseFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addExpenseView = inflater.inflate(R.layout.add_expense_layout, container, false);
        fab = (FloatingActionButton) addExpenseView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertLayout = layoutInflater.inflate(R.layout.add_expense_dialog, null);
                // content type spinner....
                expenseHeadSpinner = (Spinner) alertLayout.findViewById(R.id.expenseHeadSpinner);
                expenseHeadList = new ArrayList<String>();
                expenseHeadList.add("-- Select --");
                expenseHeadList.add("Books");
                // setting adapter for content type
                expenseHeadAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, expenseHeadList);
                expenseHeadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                expenseHeadSpinner.setAdapter(expenseHeadAdapter);
                expenseHeadAdapter.notifyDataSetChanged();
                //
                dialogBuilder.setTitle("Add Expense");
                dialogBuilder.setView(alertLayout);
                dialogBuilder.setNegativeButton("RESET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogBuilder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
        expenseRecylerView = (RecyclerView) addExpenseView.findViewById(R.id.expenseRecylerView);
        Expense expense = new Expense("Books", "24-04-2017", "JANI", "1200.00");
        if (listExpense.size() == 0) {
            listExpense.add(expense);
        }
        expenseAdapter = new ExpenseAdapter(getActivity(), listExpense);
        expenseRecylerView.setAdapter(expenseAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        expenseRecylerView.setLayoutManager(linearLayoutManager);
        expenseAdapter.notifyDataSetChanged();

        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Expense");
        return addExpenseView;
    }
}
