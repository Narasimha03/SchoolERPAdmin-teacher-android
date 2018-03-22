package com.example.medianet.proschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 06-06-2017.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder>{
    private Context mContext;
    private List<Expense> expenseList;
    private ArrayList<Expense> expenseArrayList;

    public ExpenseAdapter(Context mContext, ArrayList<Expense> expenseList){
        this.mContext = mContext;
        this.expenseList = expenseList;
        this.expenseArrayList = new ArrayList<Expense>();
        this.expenseArrayList.addAll(expenseList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        if (expense != null){
            holder.head.setText(expense.getHead());
            holder.date.setText(expense.getDate());
            holder.name.setText(expense.getName());
            holder.amount.setText(expense.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView head, date, name, amount;
        public ViewHolder(View itemView) {
            super(itemView);
            head = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.type);
            amount = (TextView) itemView.findViewById(R.id.available);
        }
    }
}
