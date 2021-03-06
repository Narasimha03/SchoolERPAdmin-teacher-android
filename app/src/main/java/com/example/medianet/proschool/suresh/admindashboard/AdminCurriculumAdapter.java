package com.example.medianet.proschool.suresh.admindashboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.medianet.proschool.R;

import java.util.List;



public class AdminCurriculumAdapter extends RecyclerView.Adapter<AdminCurriculumAdapter
        .MyViewHolder> {


    private List<AdminCurriculumClass> adminCurClassList;
    private final OnItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView adminCurClass;

        public MyViewHolder(View view) {
            super(view);
            adminCurClass = view.findViewById(R.id.admin_curriculum_text);
        }

        public void bind(final AdminCurriculumClass item, final OnItemClickListener listener) {

            adminCurClass.setText(item.getAdminCurClasses());
            adminCurClass.setOnClickListener(new View.OnClickListener() {

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


    public AdminCurriculumAdapter(List<AdminCurriculumClass> adminCurClassList,
                                  OnItemClickListener listener) {
        this.adminCurClassList = adminCurClassList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(AdminCurriculumClass item);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_curriculum_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(adminCurClassList.get(position), listener);

        /*AdminCurriculumClass ccBind = adminCurClassList.get(position);
        holder.adminCurClass.setText(ccBind.getAdminCurClasses(),listener);*/
    }



    @Override
    public int getItemCount() {
        return adminCurClassList.size();
    }





}
