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

public class DashboardAssignmentAdapter extends RecyclerView.Adapter<DashboardAssignmentAdapter.MyViewHolder> {


        private List<DashboardAssignmentClass> asList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView subText;
            public TextView asnText;

            public MyViewHolder(View view) {
                super(view);
                subText = (TextView) view.findViewById(R.id.subject_text_view);
                asnText = (TextView) view.findViewById(R.id.assignment_text_view);
            }
        }


        public DashboardAssignmentAdapter (List<DashboardAssignmentClass> asList) {
            this.asList = asList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.teacher_assignment_text_layout, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DashboardAssignmentClass asnBind = asList.get(position);
            holder.subText.setText(asnBind.getSubjectText());
            holder.asnText.setText(asnBind.getAssignmentText());
        }

        @Override
        public int getItemCount() {
            return asList.size();
        }
    }
