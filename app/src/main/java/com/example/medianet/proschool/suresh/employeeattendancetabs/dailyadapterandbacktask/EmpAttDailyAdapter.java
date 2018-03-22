package com.example.medianet.proschool.suresh.employeeattendancetabs.dailyadapterandbacktask;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.StudentAttandenceModel;
import com.example.medianet.proschool.suresh.employeeattendancetabs.EmpAttModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 17-06-2017.
 */

public class EmpAttDailyAdapter extends RecyclerView.Adapter<EmpAttDailyAdapter.ViewHolder>{

    Context mContext;
    List<EmpAttModel> attendanceList;
    ArrayList<EmpAttModel> attendanceArrayList;

    public EmpAttDailyAdapter(Context mContext, ArrayList<EmpAttModel> evalMarksList){
        this.mContext = mContext;
        this.attendanceList = evalMarksList;
        this.attendanceArrayList = new ArrayList<EmpAttModel>();
        this.attendanceArrayList.addAll(evalMarksList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emp_attendance_day_wise_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EmpAttModel evalMarks = attendanceList.get(position);
        if (evalMarks != null){
            holder.empName.setText(evalMarks.getEmpName());
            holder.empId.setText(evalMarks.getEmpId());
            holder.empType.setText(evalMarks.getEmployeeType());
            holder.gender.setText(evalMarks.getEmpGender());
            holder.attStatus.setText(evalMarks.getAttendanceStatus());


        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView empName, empId, empType, gender, attStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            empName = itemView.findViewById(R.id.empName);
            empId = itemView.findViewById(R.id.empId);
            empType = itemView.findViewById(R.id.empType);
            gender = itemView.findViewById(R.id.gender);
            attStatus = itemView.findViewById(R.id.attStatus);
            empId.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        }
    }
}

