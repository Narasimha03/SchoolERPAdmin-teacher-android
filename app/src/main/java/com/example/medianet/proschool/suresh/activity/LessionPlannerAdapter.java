package com.example.medianet.proschool.suresh.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medianet.proschool.Chapters;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.examination.BulkEvaluationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 16-06-2017.
 */

public class LessionPlannerAdapter extends RecyclerView.Adapter<LessionPlannerAdapter.ViewHolder> {

    Context mContext;
    List<Chapters> chaptersList;
    ArrayList<Chapters> chaptersArrayList;
    private String compTopics [];
    private String chapterId [];


    public LessionPlannerAdapter(Context mContext, ArrayList<Chapters> chaptersList){
        this.mContext = mContext;
        this.chaptersList = chaptersList;
        this.compTopics = new String[getItemCount()];
        this.chapterId = new String[getItemCount()];

        this.chaptersArrayList = new ArrayList<Chapters>();
        this.chaptersArrayList.addAll(chaptersList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lession_planner_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chapters chapters = chaptersList.get(position);
        if (chapters != null){
            holder.name.setText(chapters.getTitle());
            holder.code.setText(chapters.getCode());
            //holder.subject.setText(chapters.getSubject_id());
            holder.completedTopics.setText(chapters.getDesc());

            holder.topics.setText(chapters.getNoTopics());
            chapterId[holder.getAdapterPosition()] =
                    chapters.getLesson_id();


            holder.completedTopics.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                   /* compTopics[holder.getAdapterPosition()] =
                            holder.completedTopics.getText().toString();*/



                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    compTopics[holder.getAdapterPosition()] =
                            holder.completedTopics.getText().toString();


                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });



        }
    }

    @Override
    public int getItemCount() {
        return chaptersList.size();
    }

    public String [] getEtCompTopics(){
        return this.compTopics;
    }
    public String [] getStChapterId(){
        return this.chapterId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView name, code, subject, topics;
        public EditText completedTopics;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.chapterName);
            code = (TextView) itemView.findViewById(R.id.chapterCode);
            //subject = (TextView) itemView.findViewById(R.id.subject);
            topics = (TextView) itemView.findViewById(R.id.totalTopics);
            completedTopics =  itemView.findViewById(R.id.completedTopics);
        }
    }
}
