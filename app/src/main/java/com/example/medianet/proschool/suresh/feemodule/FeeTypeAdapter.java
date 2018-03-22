package com.example.medianet.proschool.suresh.feemodule;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.feemodule.FeeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 24-06-2017.
 */

public class FeeTypeAdapter extends RecyclerView.Adapter<FeeTypeAdapter.ViewHolder>{

    Context mContext;
    List<FeeType> feeTypeList = new ArrayList<FeeType>();
    ArrayList<FeeType> vehiclesArrayList;

    public FeeTypeAdapter(Context mContext, ArrayList<FeeType> vehiclesList){
        this.mContext = mContext;
        this.feeTypeList = vehiclesList;
        this.vehiclesArrayList = new ArrayList<FeeType>();
        this.vehiclesArrayList.addAll(vehiclesList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fee_type_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeeType vehicles = feeTypeList.get(position);
        if (vehicles != null){
            holder.feeClass.setText(vehicles.getClassName());
            holder.feeType.setText(vehicles.getFeeType());
            holder.feeAmount.setText(vehicles.getFeeAmount());
        }
    }

    @Override
    public int getItemCount() {
        return feeTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView feeCategory,feeType, feeClass, feeAmount;
        public ViewHolder(View itemView) {
            super(itemView);
            feeClass = (TextView) itemView.findViewById(R.id.feeClass);
            feeType = (TextView) itemView.findViewById(R.id.feeType);
            feeAmount = (TextView) itemView.findViewById(R.id.feeAmount);
        }
    }
}
