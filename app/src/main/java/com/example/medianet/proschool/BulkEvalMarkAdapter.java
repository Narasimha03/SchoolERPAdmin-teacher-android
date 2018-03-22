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
 * Created by JANI on 17-06-2017.
 */

public class BulkEvalMarkAdapter extends RecyclerView.Adapter<BulkEvalMarkAdapter.ViewHolder>{

    Context mContext;
    List<EvalMarks> evalMarksList;
    ArrayList<EvalMarks> evalMarksArrayList;

    public BulkEvalMarkAdapter(Context mContext, ArrayList<EvalMarks> evalMarksList){
        this.mContext = mContext;
        this.evalMarksList = evalMarksList;
        this.evalMarksArrayList = new ArrayList<EvalMarks>();
        this.evalMarksArrayList.addAll(evalMarksList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eval_mark_list_layout_two, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EvalMarks evalMarks = evalMarksList.get(position);
        if (evalMarks != null){
            holder.student.setText(evalMarks.getStudent_id());
            holder.maxMarks.setText(evalMarks.getExam());
            holder.marksObtained.setText(evalMarks.getSubject());
        }
    }

    @Override
    public int getItemCount() {
        return evalMarksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView student, exam, subject, paper, marks, percent, conduct, maxMarks, marksObtained;
        public ViewHolder(View itemView) {
            super(itemView);
            student = (TextView) itemView.findViewById(R.id.student);
            maxMarks = (TextView) itemView.findViewById(R.id.maxMarks);
            marksObtained = (TextView) itemView.findViewById(R.id.marksObtained);
        }
    }
}
