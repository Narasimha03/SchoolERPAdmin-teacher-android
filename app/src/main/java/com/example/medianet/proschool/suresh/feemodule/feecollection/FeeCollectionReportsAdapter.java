package com.example.medianet.proschool.suresh.feemodule.feecollection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.feemodule.feecollection.FeeCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 17-06-2017.
 */

public class FeeCollectionReportsAdapter extends RecyclerView.Adapter<FeeCollectionReportsAdapter.ViewHolder>{

    Context mContext;
    List<FeeCollectionReports> evalMarksList;
    ArrayList<FeeCollectionReports> evalMarksArrayList;

    public FeeCollectionReportsAdapter(Context mContext, ArrayList<FeeCollectionReports> evalMarksList){
        this.mContext = mContext;
        this.evalMarksList = evalMarksList;
        this.evalMarksArrayList = new ArrayList<FeeCollectionReports>();
        this.evalMarksArrayList.addAll(evalMarksList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fee_collection_reports_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeeCollectionReports evalMarks = evalMarksList.get(position);
        if (evalMarks != null){
            holder.student.setText(evalMarks.getStdName());
           // holder.feeType.setText(evalMarks.getFeeType());
            holder.totalFee.setText(evalMarks.getTotalFee());
            holder.feePaid.setText(evalMarks.getFeePaid());
            //holder.paymentMode.setText(evalMarks.getPaymentMode());
            holder.discount.setText(evalMarks.getDiscount());
            holder.fineByDate.setText(evalMarks.getFine());
            holder.balanceFee.setText(evalMarks.getBalanceFee());

            holder.dueDate.setText(evalMarks.getDue_date());
          //  holder.feePaidDate.setText(evalMarks.getFeepaidDate());

            //String percent = evalMarks.getDiscount();

          //  holder.feePaid.setText(evalMarks.getFeePaid());

        }
    }

    @Override
    public int getItemCount() {
        return evalMarksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView student, feeType,totalFee, feePaid, balanceFee,discount, fineByDate, dueDate,feePaidDate;
        public ViewHolder(View itemView) {
            super(itemView);
            student = (TextView) itemView.findViewById(R.id.student);
           // feeType = (TextView) itemView.findViewById(R.id.feeType);
            totalFee = (TextView) itemView.findViewById(R.id.totalFee);
            feePaid = (TextView) itemView.findViewById(R.id.feePaid);
          //  paymentMode = (TextView) itemView.findViewById(R.id.paymentMode);
            discount = (TextView) itemView.findViewById(R.id.discount);
            fineByDate = (TextView) itemView.findViewById(R.id.fine);
            balanceFee = (TextView) itemView.findViewById(R.id.balanceFee);

            dueDate = (TextView) itemView.findViewById(R.id.dueDate);
           // feePaidDate=(TextView)itemView.findViewById(R.id.feePaidDate);
        }
    }
}
