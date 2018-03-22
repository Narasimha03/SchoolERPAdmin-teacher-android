package com.example.medianet.proschool.suresh.employeeattendancetabs.monthlyadapterandbacktask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.employeeattendancetabs.EmpAttModel;

import java.util.ArrayList;
import java.util.List;

public class EmpAttMonthlyAdapter extends RecyclerView.Adapter<EmpAttMonthlyAdapter.ViewHolder>{

    Context mContext;
    List<EmpAttModel> attendanceList;
    ArrayList<EmpAttModel> attendanceArrayList;

    public EmpAttMonthlyAdapter(Context mContext, ArrayList<EmpAttModel> evalMarksList){
        this.mContext = mContext;
        this.attendanceList = evalMarksList;
        this.attendanceArrayList = new ArrayList<EmpAttModel>();
        this.attendanceArrayList.addAll(evalMarksList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emp_attendance_month_wise_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EmpAttModel evalMarks = attendanceList.get(position);
        if (evalMarks != null){
            holder.empName.setText(evalMarks.getEmpName());
            holder.attStatus.setText(evalMarks.getAttendanceStatus());


        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView empName, attStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            empName = itemView.findViewById(R.id.empAttDate);
            attStatus = itemView.findViewById(R.id.empAttStatus);

        }
    }
}

