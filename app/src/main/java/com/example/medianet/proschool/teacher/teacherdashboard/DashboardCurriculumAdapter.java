package com.example.medianet.proschool.teacher.teacherdashboard;

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

public class DashboardCurriculumAdapter extends RecyclerView.Adapter<DashboardCurriculumAdapter.MyViewHolder> {


        private List<DashboardCurriculumClass> ccList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView ccPeriod;
            public TextView ccClass;

            public MyViewHolder(View view) {
                super(view);
                ccPeriod = (TextView) view.findViewById(R.id.periods_curriculum);
                ccClass = (TextView) view.findViewById(R.id.class_curriculum);
            }
        }


        public DashboardCurriculumAdapter(List<DashboardCurriculumClass> ccList) {
            this.ccList = ccList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.teacher_curriculum_text_layout, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DashboardCurriculumClass ccBind = ccList.get(position);
            holder.ccPeriod.setText(ccBind.getCcPeriod());
            holder.ccClass.setText(ccBind.getCcClass());
        }

        @Override
        public int getItemCount() {
            return ccList.size();
        }
    }
