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

public class AdminSectionsAdapter extends RecyclerView.Adapter<AdminSectionsAdapter
        .MyViewHolder> {


    private List<AdminSectionsClass> adminSectionList;
    //private final OnItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView adminSections;
        public TextView adminSubOne;
        public TextView adminSubTwo;
        public TextView adminSubThree;
        public TextView adminSubFour;
        public TextView adminSubFive;
        public TextView adminSubSix;
        public TextView adminSubSeven;


        public MyViewHolder(View view) {
            super(view);
            adminSections = view.findViewById(R.id.admin_section);
            adminSubOne = view.findViewById(R.id.admin_sub_one);
            adminSubTwo = view.findViewById(R.id.admin_sub_two);
            adminSubThree = view.findViewById(R.id.admin_sub_three);
            adminSubFour = view.findViewById(R.id.admin_sub_four);
            adminSubFive = view.findViewById(R.id.admin_sub_five);
            adminSubSix = view.findViewById(R.id.admin_sub_six);
            adminSubSeven = view.findViewById(R.id.admin_sub_seven);
        }

        public void bind(final AdminSectionsClass item) {

            //final OnItemClickListener listener

            adminSections.setText(item.getSection());
            adminSubOne.setText(item.getSubOne());
            adminSubTwo.setText(item.getSubTwo());
            adminSubThree.setText(item.getSubThree());
            adminSubFour.setText(item.getSubFour());
            adminSubFive.setText(item.getSubFive());
            adminSubSix.setText(item.getSubSix());
            adminSubSeven.setText(item.getSubSeven());
            /*adminCurClass.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });*/
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


    public AdminSectionsAdapter(List<AdminSectionsClass> adminSectionsList) {
        this.adminSectionList = adminSectionsList;
        //this.listener = listener;
    }

    /*public interface OnItemClickListener {
        void onItemClick(AdminCurriculumClass item);
    }*/


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_section_items_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(adminSectionList.get(position));

        /*AdminCurriculumClass ccBind = adminCurClassList.get(position);
        holder.adminCurClass.setText(ccBind.getAdminCurClasses(),listener);*/
    }



    @Override
    public int getItemCount() {
        return adminSectionList.size();
    }





}
