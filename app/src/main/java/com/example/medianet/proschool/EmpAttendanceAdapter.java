package com.example.medianet.proschool;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.suresh.checkboxs.DemoCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 24-06-2017.
 */

public class EmpAttendanceAdapter extends RecyclerView.Adapter<EmpAttendanceAdapter.ViewHolder> {



    Context mContext;
   /* static ArrayList<DemoCheckBox> studentsDetailsList = new ArrayList<DemoCheckBox>();
    public   static ArrayList<DemoCheckBox> studentsDetailsArrayList;*/


    static List<StudentsDetailsAttModel> empList = new ArrayList<StudentsDetailsAttModel>();
    public   static ArrayList<StudentsDetailsAttModel> empListArray;

    public EmpAttendanceAdapter(Context mContext, ArrayList<StudentsDetailsAttModel> empList) {
        this.mContext = mContext;
        this.empList = empList;
        this.empListArray = new ArrayList<StudentsDetailsAttModel>();
        this.empListArray.addAll(empList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emp_att_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentsDetailsAttModel studentsDetails = empList.get(position);
        if (studentsDetails != null) {
            holder.empId.setText(studentsDetails.getStdId());
            holder.name.setText(studentsDetails.getStdName());
            holder.nameTwo.setText(studentsDetails.getStdRollNo());
        }

        final int statusPresent = position;
        empList.get(position).getStdId();
        holder.buttonPresent.setChecked(empList.get(position).getDemoSelected());

        holder.buttonPresent.setTag(empList.get(position));
        holder.buttonPresent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String checkboxname = ((CheckBox) v).getText().toString();


                holder.buttonAbsent.setChecked(false);
                holder.buttonOnLeave.setChecked(false);




                CheckBox cb = (CheckBox) v;

                StudentsDetailsAttModel contact = (StudentsDetailsAttModel) cb.getTag();


                contact.setDemoSelected(cb.isChecked());


                empList.get(statusPresent).setDemoSelected(cb.isChecked());
                contact.getPresentStatus();


                Toast.makeText(
                        v.getContext(),
                        "Clicked on absent status: " + contact.getPresentStatus() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });





        final int statusAbsent = position;
        empList.get(position).getStdId();
        holder.buttonAbsent.setChecked(empList.get(position).getAbsentSelected());

        holder.buttonAbsent.setTag(empList.get(position));
        holder.buttonAbsent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String checkboxname = ((CheckBox) v).getText().toString();

                holder.buttonPresent.setChecked(false);
                holder.buttonOnLeave.setChecked(false);

                CheckBox cb = (CheckBox) v;

                StudentsDetailsAttModel contact = (StudentsDetailsAttModel) cb.getTag();


                contact.setAbsentSelected(cb.isChecked());


                empList.get(statusAbsent).setAbsentSelected(cb.isChecked());
                // contact.setPresentStatus("Absent");
                contact.getAbsentStatus();


                Toast.makeText(
                        v.getContext(),
                        "Clicked on absent status: " + contact.getAbsentStatus() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });




        final int statusLeave = position;
        empList.get(position).getStdId();
        holder.buttonOnLeave.setChecked(empList.get(position).getLeaveSelected());

        holder.buttonOnLeave.setTag(empList.get(position));
        holder.buttonOnLeave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String checkboxname = ((CheckBox) v).getText().toString();

                holder.buttonPresent.setChecked(false);
                holder.buttonAbsent.setChecked(false);


                CheckBox cb = (CheckBox) v;

                StudentsDetailsAttModel contact = (StudentsDetailsAttModel) cb.getTag();


                contact.setLeaveSelected(cb.isChecked());


                empList.get(statusLeave).setLeaveSelected(cb.isChecked());
                //  contact.setPresentStatus("On Leave");
                contact.getOnLeaveStatus();


                Toast.makeText(
                        v.getContext(),
                        "Clicked on absent status: " + contact.getOnLeaveStatus() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View Container;
        TextView empId, name,nameTwo;
     CheckBox   buttonAbsent, buttonOnLeave, buttonPresent;

        public ViewHolder(View itemView) {
            super(itemView);
            empId = (TextView) itemView.findViewById(R.id.empId);
            name = (TextView) itemView.findViewById(R.id.name);
            nameTwo = itemView.findViewById(R.id.nameTwo);
            buttonPresent = (CheckBox) itemView.findViewById(R.id.buttonPresent);
            buttonAbsent = (CheckBox) itemView.findViewById(R.id.buttonAbsent);
            buttonOnLeave = (CheckBox) itemView.findViewById(R.id.buttonOnLeave);
            nameTwo.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        }
    }
}
