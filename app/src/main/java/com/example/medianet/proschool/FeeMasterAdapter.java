package com.example.medianet.proschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 09-05-2017.
 */

public class FeeMasterAdapter extends RecyclerView.Adapter<FeeMasterAdapter.ViewHolder>{
    private Context mcontext;
    private ArrayList<FeeMaster> feeMasterArrayList;
    private List<FeeMaster> feeMasterList;

    public FeeMasterAdapter(Context mcontext, ArrayList<FeeMaster> feeMasterArrayList){
        this.mcontext = mcontext;
        this.feeMasterList = feeMasterArrayList;
        this.feeMasterArrayList = new ArrayList<FeeMaster>();
        this.feeMasterArrayList.addAll(feeMasterList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fees_master_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeeMaster feeMaster = feeMasterList.get(position);
        if (feeMaster != null){
            holder.className.setText(feeMaster.getStdClass());
            holder.feeType.setText(feeMaster.getName());
            holder.amount.setText(feeMaster.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return feeMasterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView className, feeType, amount;
        public ImageView edit, cancel;
        public ViewHolder(View itemView) {
            super(itemView);
            className = (TextView) itemView.findViewById(R.id.className);
            feeType = (TextView) itemView.findViewById(R.id.feeType);
            amount = (TextView) itemView.findViewById(R.id.amount);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            cancel = (ImageView) itemView.findViewById(R.id.cancel);
        }
    }
}
