package com.example.medianet.proschool.suresh.timetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.timetable.ViewTimeClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 03-06-2017.
 */

public class ViewTimeAdapter extends RecyclerView.Adapter<ViewTimeAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ViewTimeClass> assignmentList;
    private List<ViewTimeClass> listAssignment;

    public ViewTimeAdapter(List<ViewTimeClass> assignmentList){
        //this.mContext = ctx;
        this.listAssignment = assignmentList;
        /*this.assignmentList = new ArrayList<ViewTimeClass>();
        this.assignmentList.addAll(listAssignment);*/
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_time_table_recycler_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewTimeClass assignments = listAssignment.get(position);
        if (assignments != null){
            holder.adminDays.setText(assignments.getDay());
            holder.adminSubOne.setText(assignments.getSubOne());
            holder.adminSubTwo.setText(assignments.getSubTwo());
            holder.adminSubThree.setText(assignments.getSubThree());
            holder.adminSubFour.setText(assignments.getSubFour());
            holder.adminSubFive.setText(assignments.getSubFive());
            holder.adminSubSix.setText(assignments.getSubSix());
            holder.adminSubSeven.setText(assignments.getSubSeven());

        }
    }

    @Override
    public int getItemCount() {
        return listAssignment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;

        public TextView adminDays;
        public TextView adminSubOne;
        public TextView adminSubTwo;
        public TextView adminSubThree;
        public TextView adminSubFour;
        public TextView adminSubFive;
        public TextView adminSubSix;
        public TextView adminSubSeven;
        public ViewHolder(View itemView) {
            super(itemView);
            adminDays = itemView.findViewById(R.id.tt_day);
            adminSubOne = itemView.findViewById(R.id.tt_sub_one);
            adminSubTwo = itemView.findViewById(R.id.tt_sub_two);
            adminSubThree = itemView.findViewById(R.id.tt_sub_three);
            adminSubFour = itemView.findViewById(R.id.tt_sub_four);
            adminSubFive = itemView.findViewById(R.id.tt_sub_five);
            adminSubSix = itemView.findViewById(R.id.tt_sub_six);
            adminSubSeven = itemView.findViewById(R.id.tt_sub_seven);
        }
    }
}




/*
package com.example.medianet.proschool.suresh.timetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.medianet.proschool.R;

import java.util.List;

*/
/**
 * Created by harish on 07-11-2017.
 *//*


public class ViewTimeAdapter extends RecyclerView.Adapter<ViewTimeAdapter
        .MyViewHolder> {


    private List<ViewTimeClass> ttList;
    //private final OnItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView adminDays;
        public TextView adminSubOne;
        public TextView adminSubTwo;
        public TextView adminSubThree;
        public TextView adminSubFour;
        public TextView adminSubFive;
        public TextView adminSubSix;
        public TextView adminSubSeven;


        public MyViewHolder(View view) {
            super(view);
            adminDays = view.findViewById(R.id.tt_day);
            adminSubOne = view.findViewById(R.id.tt_sub_one);
            adminSubTwo = view.findViewById(R.id.tt_sub_two);
            adminSubThree = view.findViewById(R.id.tt_sub_three);
            adminSubFour = view.findViewById(R.id.tt_sub_four);
            adminSubFive = view.findViewById(R.id.tt_sub_five);
            adminSubSix = view.findViewById(R.id.tt_sub_six);
            adminSubSeven = view.findViewById(R.id.tt_sub_seven);
        }

        public void bind(final ViewTimeClass item) {

            //final OnItemClickListener listener

            adminDays.setText(item.getDay());
            adminSubOne.setText(item.getSubOne());
            adminSubTwo.setText(item.getSubTwo());
            adminSubThree.setText(item.getSubThree());
            adminSubFour.setText(item.getSubFour());
            adminSubFive.setText(item.getSubFive());
            adminSubSix.setText(item.getSubSix());
            adminSubSeven.setText(item.getSubSeven());
            */
/*adminCurClass.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });*//*

        }
    }

    */
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
    }*//*



    public ViewTimeAdapter(List<ViewTimeClass> ttList) {
        this.ttList = ttList;
        //this.listener = listener;
    }

    */
/*public interface OnItemClickListener {
        void onItemClick(AdminCurriculumClass item);
    }*//*



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_time_table_recycler_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(ttList.get(position));

        */
/*AdminCurriculumClass ccBind = adminCurClassList.get(position);
        holder.adminCurClass.setText(ccBind.getAdminCurClasses(),listener);*//*

    }



    @Override
    public int getItemCount() {
        return ttList.size();
    }





}
*/
