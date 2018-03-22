package com.example.medianet.proschool.suresh.attendancemodule.daywisereport;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.StudentAttandenceModel;
import com.example.medianet.proschool.checkattendance.StudentCustomAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 17-06-2017.
 */

public class AllAttendanceReportByDayAdapter extends RecyclerView.Adapter<AllAttendanceReportByDayAdapter.ViewHolder>{

    Context mContext;
    List<StudentAttandenceModel> attendanceList;
    ArrayList<StudentAttandenceModel> attendanceArrayList;
    private AllAttendanceReportByDayAdapter.ItemClickStudentProfileListener itemClickStudentProfileListener;


    public AllAttendanceReportByDayAdapter(Context mContext, ArrayList<StudentAttandenceModel> evalMarksList){
        this.mContext = mContext;
        this.attendanceList = evalMarksList;
        this.attendanceArrayList = new ArrayList<StudentAttandenceModel>();
        this.attendanceArrayList.addAll(evalMarksList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_day_wise_list_layout, parent, false);
        return new ViewHolder(v);
    }

    public void setClickListener(AllAttendanceReportByDayAdapter.ItemClickStudentProfileListener itemClickListener) {
        this.itemClickStudentProfileListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentAttandenceModel evalMarks = attendanceList.get(position);
        if (evalMarks != null){
            holder.student.setText(evalMarks.getStdName());
            holder.exam.setText(evalMarks.getStdAdNo());
            holder.subject.setText(evalMarks.getStdRollNo());
            //holder.paper.setText(evalMarks.getStdCls());
            holder.marks.setText(evalMarks.getStdGender());
            //holder.conduct.setText(evalMarks.getStdDob());
            holder.statusPresent.setText(evalMarks.getStdAttendance());

        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View container;
        public TextView student, exam, subject, paper, marks, percent, conduct,statusPresent;
        public ViewHolder(View itemView) {
            super(itemView);
            student = itemView.findViewById(R.id.student);
            exam = itemView.findViewById(R.id.exam);
            subject = itemView.findViewById(R.id.subject);
            //paper = itemView.findViewById(R.id.paper);
            marks = itemView.findViewById(R.id.marks);
          //percent = itemView.findViewById(R.id.percent);
            //conduct = itemView.findViewById(R.id.conduct);
            statusPresent= itemView.findViewById(R.id.statusPresent);
            student.setOnClickListener(this);
            student.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }

        @Override
        public void onClick(View view) {
            if (itemClickStudentProfileListener != null) itemClickStudentProfileListener.onClickProfile(view, getAdapterPosition());

        }
    }
    public interface ItemClickStudentProfileListener {
        void onClickProfile(View view, int position);

    }
}

