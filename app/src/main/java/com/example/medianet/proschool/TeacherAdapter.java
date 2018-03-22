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

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder>{
    private Context mContext;
    private List<Teacher> teacherList;
    private ArrayList<Teacher> teacherArrayList;

    public TeacherAdapter(Context mContext, ArrayList<Teacher> teacherList){
        this.mContext = mContext;
        this.teacherList = teacherList;
        this.teacherArrayList = new ArrayList<Teacher>();
        this.teacherArrayList.addAll(teacherList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        if(teacher != null){
            holder.name.setText(teacher.getName());
            holder.dob.setText(teacher.getDob());
            holder.email.setText(teacher.getEmail());
            holder.phone.setText(teacher.getPhone());
        }
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView name, dob, email, phone;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title);
            email = (TextView) itemView.findViewById(R.id.date);
            dob = (TextView) itemView.findViewById(R.id.type);
            phone = (TextView) itemView.findViewById(R.id.available);
        }
    }
}
