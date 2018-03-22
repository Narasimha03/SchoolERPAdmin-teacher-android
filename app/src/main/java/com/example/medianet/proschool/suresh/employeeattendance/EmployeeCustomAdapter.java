package com.example.medianet.proschool.suresh.employeeattendance;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.StudentDetailsAdapter;
import com.example.medianet.proschool.checkattendance.AttSubModel;
import com.example.medianet.proschool.checkattendance.StudentCustomAdapter;
import com.example.medianet.proschool.suresh.checkboxs.DemoCheckBox;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 29-Jun-17.
 */
public class EmployeeCustomAdapter extends RecyclerView.Adapter<EmployeeCustomAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    public static ArrayList<AttSubModel> imageModelArrayList;
    private Context ctx;
    private EmployeeCustomAdapter.ItemClickStudentProfileListener itemClickStudentProfileListener;


    public EmployeeCustomAdapter(Context ctx, ArrayList<AttSubModel> imageModelArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
        this.ctx = ctx;
    }

    @Override
    public EmployeeCustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_item_employee_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }


    public void setClickListener(EmployeeCustomAdapter.ItemClickStudentProfileListener itemClickListener) {
        this.itemClickStudentProfileListener = itemClickListener;
    }
    @Override
    public void onBindViewHolder(final EmployeeCustomAdapter.MyViewHolder holder, int position) {

        //holder.presentAll.setText("Present");AttSubModel studentsDetails = imageModelArrayList.get(position);

        AttSubModel studentsDetails = imageModelArrayList.get(position);

        if (studentsDetails != null) {
            holder.stdName.setText(studentsDetails.getStdName());
            holder.empType.setText(studentsDetails.getRollNo());
            holder.empId.setText(studentsDetails.getAdmNo());


            // holder.stdName.setText(studentsDetails.getStdName());
        }



        holder.presentAll.setChecked(imageModelArrayList.get(position).getSelected());

        //holder.rollNo.setText(imageModelArrayList.get(position).getRollNo());

     /*  holder.addNo.setText(imageModelArrayList.get(position).getAdmNo());
        holder.rollNo.setText(imageModelArrayList.get(position).getRollNo());
        holder.stdName.setText(imageModelArrayList.get(position).getStdName());*/

        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.presentAll.setTag(position);
        holder.presentAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.absentAll.setChecked(false);
                holder.leaveAll.setChecked(false);

                Integer pos = (Integer) holder.presentAll.getTag();
                //Toast.makeText(ctx, imageModelArrayList.get(pos).getStudentId() + " clicked!", Toast.LENGTH_SHORT).show();

                if (imageModelArrayList.get(pos).getSelected()) {
                    imageModelArrayList.get(pos).setSelected(false);
                } else {
                    imageModelArrayList.get(pos).setSelected(true);
                    imageModelArrayList.get(pos).setAbsentSelected(false);
                    imageModelArrayList.get(pos).setLeaveSelected(false);

                }
            }
        });




        holder.absentAll.setChecked(imageModelArrayList.get(position).getSelectedAbsent());
        holder.absentAll.setTag(position);
        holder.absentAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.leaveAll.setChecked(false);
                holder.presentAll.setChecked(false);
                String checkboxAbsent = ((CheckBox) v).getText().toString();


              /*  CheckBox cb = (CheckBox) v;

                DemoCheckBox contact = (DemoCheckBox) cb.getTag();


                contact.setPresentSelected(cb.isChecked());*/
             /* AttSubModel attSubModel=new AttSubModel();
              attSubModel.setAttStatus(checkboxAbsent);
                Log.e("absent Status",checkboxAbsent);*/


                Integer pos = (Integer) holder.absentAll.getTag();
                //Toast.makeText(ctx, imageModelArrayList.get(pos).getStudentId() + " clicked!", Toast.LENGTH_SHORT).show();

                if (imageModelArrayList.get(pos).getSelectedAbsent()) {
                    imageModelArrayList.get(pos).setAbsentSelected(false);
                } else {
                    imageModelArrayList.get(pos).setAbsentSelected(true);
                    imageModelArrayList.get(pos).setLeaveSelected(false);
                    imageModelArrayList.get(pos).setSelected(false);


                }
            }
        });




        // holder.leaveAll.setText("On Leave");
        holder.leaveAll.setChecked(imageModelArrayList.get(position).getSelectedLeave());
        //  holder.rollNo.setText(imageModelArrayList.get(position).getStudentId());

        //holder.rollNo.setText(imageModelArrayList.get(position).getStudentId());
      /*  holder.addNo.setText(imageModelArrayList.get(position).getAdmNo());
        holder.rollNo.setText(imageModelArrayList.get(position).getRollNo());
        holder.stdName.setText(imageModelArrayList.get(position).getStdName());*/
        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.leaveAll.setTag(position);
        holder.leaveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.presentAll.setChecked(false);
                holder.absentAll.setChecked(false);
                String checkboxLeave = ((CheckBox) v).getText().toString();
               /* AttSubModel attSubModel=new AttSubModel();
                attSubModel.setAttStatus(checkboxLeave);
                Log.e("leave Status",checkboxLeave);*/

                Integer pos = (Integer) holder.leaveAll.getTag();
                //Toast.makeText(ctx, imageModelArrayList.get(pos).getStudentId() + " clicked!", Toast.LENGTH_SHORT).show();

                if (imageModelArrayList.get(pos).getSelectedLeave()) {
                    imageModelArrayList.get(pos).setLeaveSelected(false);
                } else {
                    imageModelArrayList.get(pos).setLeaveSelected(true);
                    imageModelArrayList.get(pos).setSelected(false);
                    imageModelArrayList.get(pos).setAbsentSelected(false);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        protected CheckBox presentAll,absentAll,leaveAll;
        private TextView empType,empId,stdName;


        public MyViewHolder(View itemView) {
            super(itemView);

            presentAll = (CheckBox) itemView.findViewById(R.id.presentAll);
            absentAll = (CheckBox) itemView.findViewById(R.id.absentAll);
            leaveAll = (CheckBox) itemView.findViewById(R.id.leaveAll);
            empId = (TextView) itemView.findViewById(R.id.empId);
            empType = (TextView) itemView.findViewById(R.id.empType);
            stdName=(TextView)itemView.findViewById(R.id.nameTwo);
            stdName.setOnClickListener(this);
            stdName.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
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


