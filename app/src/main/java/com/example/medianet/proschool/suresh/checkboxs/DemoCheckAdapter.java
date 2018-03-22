package com.example.medianet.proschool.suresh.checkboxs;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.StudentAttandenceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 24-06-2017.
 */

public class DemoCheckAdapter extends RecyclerView.Adapter<DemoCheckAdapter.ViewHolder> {

    Context mContext;
     static ArrayList<DemoCheckBox> studentsDetailsList = new ArrayList<DemoCheckBox>();
     List<DemoCheckBox> studentsDetailsArrayList;

    public DemoCheckAdapter(Context mContext, ArrayList<DemoCheckBox> studentsDetailsList) {
        this.mContext = mContext;
        this.studentsDetailsList = studentsDetailsList;
        this.studentsDetailsArrayList = new ArrayList<DemoCheckBox>();
        this.studentsDetailsArrayList.addAll(studentsDetailsList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.std_check_attendance_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DemoCheckBox studentsDetails = studentsDetailsList.get(position);
        if (studentsDetails != null) {
            holder.addNo.setText(studentsDetails.getStdAdNo());
            holder.rollNo.setText(studentsDetails.getStdRollNo());
            holder.name.setText(studentsDetails.getStdName());
        }

        /*final int pos = position;
        studentsDetailsList.get(position).getStdName();
        holder.chkSelected.setChecked(studentsDetailsList.get(position).isSelected());

        holder.chkSelected.setTag(studentsDetailsList.get(position));


        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;


                DemoCheckBox contact = (DemoCheckBox) cb.getTag();

                contact.setSelected(cb.isChecked());
                studentsDetailsList.get(pos).setSelected(cb.isChecked());


                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });*/

       /* holder.demotest.setText("Present");
        holder.demotest.setChecked(studentsDetailsList.get(position).getDemoSelected());*/
        //  holder.tvAnimal.setText(imageModelArrayList.get(position).getAnimal());

        // studentsDetailsList.get(position).getStdName();


        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
      /*  holder.demotest.setTag(position);
        holder.demotest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.demotest.getTag();
                Toast.makeText(mContext, studentsDetailsList.get(pos).getStdId() + " clicked!", Toast.LENGTH_SHORT).show();

                if (studentsDetailsList.get(pos).getDemoSelected()) {
                    studentsDetailsList.get(pos).setDemoSelected(false);
                } else {
                    studentsDetailsList.get(pos).setDemoSelected(true);




                }
            }
        });
*/


/*

        holder.chkSelectedAbsent.setText("Absent");
        holder.chkSelectedAbsent.setChecked(studentsDetailsList.get(position).getAbsentSelected());
        //  holder.tvAnimal.setText(imageModelArrayList.get(position).getAnimal());

        // studentsDetailsList.get(position).getStdName();


        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.chkSelectedAbsent.setTag(position);
        holder.chkSelectedAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Integer pos = (Integer) holder.chkSelectedAbsent.getTag();
                Toast.makeText(mContext, studentsDetailsList.get(pos).getStdId() + " clicked!", Toast.LENGTH_SHORT).show();

                if (studentsDetailsList.get(pos).getAbsentSelected()) {
                    studentsDetailsList.get(pos).setAbsentSelected(false);

                } else {
                    studentsDetailsList.get(pos).setAbsentSelected(true);
                }
            }
        });

*/


/*

        holder.chkSelectedOnLeave.setText("On Leave");
        holder.chkSelectedOnLeave.setChecked(studentsDetailsList.get(position).getLeaveSelected());
        //  holder.tvAnimal.setText(imageModelArrayList.get(position).getAnimal());

        // studentsDetailsList.get(position).getStdName();


        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.chkSelectedOnLeave.setTag(position);
        holder.chkSelectedOnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Integer pos = (Integer) holder.chkSelectedOnLeave.getTag();
                Toast.makeText(mContext, studentsDetailsList.get(pos).getStdId() + " clicked!", Toast.LENGTH_SHORT).show();

                if (studentsDetailsList.get(pos).getLeaveSelected()) {
                    studentsDetailsList.get(pos).setLeaveSelected(false);
                } else {
                    studentsDetailsList.get(pos).setLeaveSelected(true);
                }
            }
        });
*/



/*

        final int statusPosition = position;
        studentsDetailsList.get(position).getStdName();
        holder.chkSelectedStatus.setChecked(studentsDetailsList.get(position).isSelectedPresent());

        holder.chkSelectedStatus.setTag(studentsDetailsList.get(position));
        holder.chkSelectedStatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                holder.chkSelectedAbsent.setChecked(false);

                CheckBox cb = (CheckBox) v;
                String checkboxname = cb.getText().toString();

                DemoCheckBox contact = (DemoCheckBox) cb.getTag();


                contact.setSelected(cb.isChecked());
                studentsDetailsList.get(statusPosition).setSelected(cb.isChecked());
                contact.setPresentStatus(checkboxname);


                Toast.makeText(
                        v.getContext(),
                        "Clicked on present data: " + contact.getPresentStatus() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
*/


    //    holder.chkdemo.setChecked(suggestion.isSelected());








     /*   final int statusDemo = position;
        studentsDetailsList.get(position).getStdName();
        holder.chkdemo.setChecked(studentsDetailsList.get(position).getDemoSelected());

        holder.chkdemo.setTag(studentsDetailsList.get(position));
        holder.chkdemo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String checkboxname = ((CheckBox) v).getText().toString();
                holder.chkSelectedPresent.setChecked(false);
                holder.chkSelectedAbsent.setChecked(false);
                holder.chkSelectedOnLeave.setChecked(false);

                CheckBox cb = (CheckBox) v;

                DemoCheckBox contact = (DemoCheckBox) cb.getTag();


                contact.setDemoSelected(cb.isChecked());


                studentsDetailsList.get(statusDemo).setDemoSelected(cb.isChecked());
                // contact.setPresentStatus("Absent");
                contact.getDemo();


                Toast.makeText(
                        v.getContext(),
                        "Clicked on absent status: " + contact.getDemo() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });*/




        final int statusPresent = position;
        studentsDetailsList.get(position).getStdName();
        holder.chkSelectedPresent.setChecked(studentsDetailsList.get(position).getPresentSelected());

        holder.chkSelectedPresent.setTag(studentsDetailsList.get(position));
        holder.chkSelectedPresent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String checkboxname = ((CheckBox) v).getText().toString();

                holder.chkSelectedAbsent.setChecked(false);
                holder.chkSelectedOnLeave.setChecked(false);



                CheckBox cb = (CheckBox) v;

                DemoCheckBox contact = (DemoCheckBox) cb.getTag();


                contact.setPresentSelected(cb.isChecked());


                studentsDetailsList.get(statusPresent).setPresentSelected(cb.isChecked());
                //  contact.setPresentStatus("On Leave");
                contact.getPresentStatus();

            }
        });











        final int statusAbsent = position;
        studentsDetailsList.get(position).getStdName();
        holder.chkSelectedAbsent.setChecked(studentsDetailsList.get(position).getAbsentSelected());

        holder.chkSelectedAbsent.setTag(studentsDetailsList.get(position));
        holder.chkSelectedAbsent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String checkboxname = ((CheckBox) v).getText().toString();

                holder.chkSelectedPresent.setChecked(false);
                holder.chkSelectedOnLeave.setChecked(false);

                CheckBox cb = (CheckBox) v;

                DemoCheckBox contact = (DemoCheckBox) cb.getTag();


                contact.setAbsentSelected(cb.isChecked());


                studentsDetailsList.get(statusAbsent).setAbsentSelected(cb.isChecked());
               // contact.setPresentStatus("Absent");
                contact.getAbsentStatus();


             /*   Toast.makeText(
                        v.getContext(),
                        "Clicked on absent status: " + contact.getAbsentStatus() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();*/
            }
        });




        final int statusLeave = position;
        studentsDetailsList.get(position).getStdName();
        holder.chkSelectedOnLeave.setChecked(studentsDetailsList.get(position).getLeaveSelected());

        holder.chkSelectedOnLeave.setTag(studentsDetailsList.get(position));
        holder.chkSelectedOnLeave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String checkboxname = ((CheckBox) v).getText().toString();

                holder.chkSelectedPresent.setChecked(false);
                holder.chkSelectedAbsent.setChecked(false);


                CheckBox cb = (CheckBox) v;

                DemoCheckBox contact = (DemoCheckBox) cb.getTag();


                contact.setLeaveSelected(cb.isChecked());


                studentsDetailsList.get(statusLeave).setLeaveSelected(cb.isChecked());
              //  contact.setPresentStatus("On Leave");
                contact.getOnLeaveStatus();

            }
        });


    }

    @Override
    public int getItemCount() {
        return studentsDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView addNo, rollNo, name;


        public CheckBox chkSelectedPresent,chkSelectedAbsent, chkSelectedOnLeave;

        public ViewHolder(View itemView) {
            super(itemView);
            addNo = itemView.findViewById(R.id.addNo);
            rollNo = itemView.findViewById(R.id.rollNo);
            name = itemView.findViewById(R.id.name);
            name.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);


            chkSelectedPresent = itemView.findViewById((R.id.chkSelectedPresentStatus));
            chkSelectedAbsent = itemView.findViewById((R.id.chkSelectedAbsent));
            chkSelectedOnLeave = itemView.findViewById((R.id.chkSelectedOnLeave));



        }


    }

    public List<DemoCheckBox> getStudentsDetailsList() {
        return studentsDetailsList;
    }

}
