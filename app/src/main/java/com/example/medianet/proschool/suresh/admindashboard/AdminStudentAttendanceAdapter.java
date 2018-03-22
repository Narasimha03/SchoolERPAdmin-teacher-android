package com.example.medianet.proschool.suresh.admindashboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.medianet.proschool.R;

import java.util.List;

/**
 * Created by harish on 07-11-2017.
 */

public class AdminStudentAttendanceAdapter extends RecyclerView.Adapter<AdminStudentAttendanceAdapter
        .MyViewHolder> {


    private List<AdminStudentAttendanceClass> adminStuAttClassList;
    private final OnItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView adminStuAttClass;

        public MyViewHolder(View view) {
            super(view);
                adminStuAttClass = view.findViewById(R.id.admin_curriculum_text);
        }

        public void bind(final AdminStudentAttendanceClass item, final OnItemClickListener listener) {

            adminStuAttClass.setText(item.getAdminStuAttClasses());
            adminStuAttClass.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    /*static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView adminCurClass;

        public ViewHolder(View itemView) {
            super(itemView);
            adminCurClass = view.findViewById(R.id.admin_curriculum_text);
        }

        public void bind(final ContentItem item, final OnItemClickListener listener) {

            name.setText(item.name);
            Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }*/


    public AdminStudentAttendanceAdapter(List<AdminStudentAttendanceClass> adminStuAttClassList,
                                         OnItemClickListener listener) {
        this.adminStuAttClassList = adminStuAttClassList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(AdminStudentAttendanceClass item);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_curriculum_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(adminStuAttClassList.get(position), listener);

        /*AdminCurriculumClass ccBind = adminCurClassList.get(position);
        holder.adminCurClass.setText(ccBind.getAdminCurClasses(),listener);*/
    }



    @Override
    public int getItemCount() {
        return adminStuAttClassList.size();
    }





}
