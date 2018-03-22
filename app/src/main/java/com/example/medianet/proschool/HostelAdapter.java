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
 * Created by JANI on 03-06-2017.
 */

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.ViewHolder>{

    private Context mContext;
    private List<Hostel> hostelList;
    private ArrayList<Hostel> hostelArrayList;

    public HostelAdapter(Context mContext, ArrayList<Hostel> hostelArrayList){
        this.mContext = mContext;
        this.hostelList = hostelArrayList;
        this.hostelArrayList = new ArrayList<Hostel>();
        this.hostelArrayList.addAll(hostelList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hostel_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hostel hostel = hostelList.get(position);
        if (hostel != null){
            holder.name.setText(hostel.getName());
            holder.type.setText(hostel.getType());
            holder.address.setText(hostel.getAddress());
            holder.intake.setText(hostel.getIntake());
        }
    }

    @Override
    public int getItemCount() {
        return hostelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View Container;
        TextView name, type, address, intake;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            type = (TextView) itemView.findViewById(R.id.type);
            address = (TextView) itemView.findViewById(R.id.address);
            intake = (TextView) itemView.findViewById(R.id.intake);
        }
    }
}
