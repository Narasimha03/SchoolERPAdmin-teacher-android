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
 * Created by JANI on 07-06-2017.
 */

public class MarksGradeAdapter extends RecyclerView.Adapter<MarksGradeAdapter.ViewHolder>{
    private Context mContext;
    private List<MarksGrade> marksGradeList;
    private ArrayList<MarksGrade> marksGradeArrayList;

    public MarksGradeAdapter(Context mContext, ArrayList<MarksGrade> marksGradeList){
        this.mContext = mContext;
        this.marksGradeList = marksGradeList;
        this.marksGradeArrayList = new ArrayList<MarksGrade>();
        this.marksGradeArrayList.addAll(marksGradeList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stations_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MarksGrade marksGrade = marksGradeList.get(position);
        if (marksGrade != null){
            holder.name.setText(marksGrade.getName());
            holder.from.setText(marksGrade.getFrom());
            holder.to.setText(marksGrade.getTo());
        }
    }

    @Override
    public int getItemCount() {
        return marksGradeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView name, from, to;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            from = (TextView) itemView.findViewById(R.id.code);
            to = (TextView) itemView.findViewById(R.id.geoLocation);
        }
    }
}
