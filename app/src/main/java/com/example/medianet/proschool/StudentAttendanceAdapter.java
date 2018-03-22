package com.example.medianet.proschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 24-06-2017.
 */

public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.ViewHolder>{

    Context mContext;
    List<StudentAttandenceModel> studentsDetailsList = new ArrayList<StudentAttandenceModel>();
    ArrayList<StudentAttandenceModel> studentsDetailsArrayList;

    public StudentAttendanceAdapter(Context mContext, ArrayList<StudentAttandenceModel> studentsDetailsList){
        this.mContext = mContext;
        this.studentsDetailsList = studentsDetailsList;
        this.studentsDetailsArrayList = new ArrayList<StudentAttandenceModel>();
        this.studentsDetailsArrayList.addAll(studentsDetailsList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.std_attendance_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentAttandenceModel studentsDetails = studentsDetailsList.get(position);
        if (studentsDetails != null){
            holder.addNo.setText(studentsDetails.getStdAdNo());
            holder.rollNo.setText(studentsDetails.getStdRollNo());
            holder.name.setText(studentsDetails.getStdName());
        }

      final int pos = position;
      studentsDetailsList.get(position).getStdName();
        holder.chkSelected.setChecked(studentsDetailsList.get(position).isSelected());

        holder.chkSelected.setTag(studentsDetailsList.get(position));


        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;


                StudentAttandenceModel contact = (StudentAttandenceModel) cb.getTag();

                contact.setSelected(cb.isChecked());
                studentsDetailsList.get(pos).setSelected(cb.isChecked());
              //  String checkboxname =contact.getStdId().toString();
                //contact.setStdId(checkboxname);


                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });




        final int statusPosition = position;
        studentsDetailsList.get(position).getStdName();
        holder.chkSelectedStatus.setChecked(studentsDetailsList.get(position).isSelectedPresent());

        holder.chkSelectedStatus.setTag(studentsDetailsList.get(position));
        holder.chkSelectedStatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




                holder.chkSelectedAbsent.setChecked(false);

                CheckBox cb = (CheckBox) v;
               String checkboxname =cb.getText().toString();

                StudentAttandenceModel contact = (StudentAttandenceModel) cb.getTag();


                contact.setSelected(cb.isChecked());
                studentsDetailsList.get(statusPosition).setSelected(cb.isChecked());
                contact.setPresentStatus(checkboxname);



                Toast.makeText(
                        v.getContext(),
                        "Clicked on present data: " + contact.getPresentStatus() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });


        final int statusAbsent = position;
        studentsDetailsList.get(position).getStdName();
        holder.chkSelectedAbsent.setChecked(studentsDetailsList.get(position).isSelectedAbsent());

        holder.chkSelectedAbsent.setTag(studentsDetailsList.get(position));
        holder.chkSelectedAbsent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               String checkboxname = ((CheckBox) v).getText().toString();

                holder.chkSelectedStatus.setChecked(false);


                CheckBox cb = (CheckBox) v;
              //  String checkboxname =cb.getText().toString();

                StudentAttandenceModel contact = (StudentAttandenceModel) cb.getTag();



                contact.setSelected(cb.isChecked());


                studentsDetailsList.get(statusAbsent).setSelected(cb.isChecked());
              //  contact.setPresentStatus(checkboxname);
                contact.setPresentStatus(checkboxname);


                Toast.makeText(
                        v.getContext(),
                        "Clicked on absent status: " + contact.getPresentStatus() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });











     /*   studentsDetailsList.get(position).getSelected();
        studentsDetailsList.get(position).getStdName();
        // holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.radioGroup.setTag(position);


        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {





                // find which radio button is selected
                if(checkedId == R.id.circle) {


                    final int statusPosition = position;
                  //  studentsDetailsList.get(position).getStdName();

                    holder.circle.setChecked(studentsDetailsList.get(position).isSelected());

                    //StudentAttandenceModel contact = (StudentAttandenceModel) cb.getTag();

                    Integer pos = (Integer) holder.radioGroup.getTag();
                    Toast.makeText(mContext, studentsDetailsList.get(pos).getStdId() + " clicked!", Toast.LENGTH_SHORT).show();

                    if (studentsDetailsList.get(pos).getSelected()) {
                        studentsDetailsList.get(pos).setSelected(false);
                    } else {
                        studentsDetailsList.get(pos).setSelected(true);
                    }
                    //   Integer pos = (Integer) holder.checkBox.getTag();
                  *//*  Integer pos = (Integer) holder.circle.getTag();
                    Toast.makeText(ctx, imageModelArrayList.get(pos).getAnimal() + " clicked!", Toast.LENGTH_SHORT).show();

                    if (imageModelArrayList.get(pos).getSelected()) {
                        imageModelArrayList.get(pos).setSelected(false);
                    } else {
                        imageModelArrayList.get(pos).setSelected(true);
                    }
*//*

                 *//*   Toast.makeText(ctx, "choice: Circle",
                            Toast.LENGTH_SHORT).show();*//*
                } else if(checkedId == R.id.sqare) {


                    Toast.makeText(mContext, "choice: Square",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "choice: Rectangle",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });*/

    }

    @Override
    public int getItemCount() {
        return studentsDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View Container;
        TextView addNo, rollNo, name, buttonAttend, buttonLeave, buttonPresent;


      //  private RadioGroup radioGroup;
       // private RadioButton circle, square, rectangle;
        public CheckBox chkSelected,chkSelectedStatus,chkSelectedAbsent,chkSelectedOnLeave;

        public ViewHolder(View itemView) {
            super(itemView);
            addNo = (TextView) itemView.findViewById(R.id.addNo);
            rollNo = (TextView) itemView.findViewById(R.id.rollNo);
            name = (TextView) itemView.findViewById(R.id.name);
          //  buttonAttend = (TextView) itemView.findViewById(R.id.buttonAttend);
          //  buttonLeave = (TextView) itemView.findViewById(R.id.buttonLeave);
            buttonPresent = (TextView) itemView.findViewById(R.id.buttonPresent);

          //  radioGroup = (RadioGroup)itemView.findViewById(R.id.myRadioGroup);
          //  circle = (RadioButton)itemView.findViewById(R.id.circle);
          //  square = (RadioButton)itemView.findViewById(R.id.sqare);
          //  rectangle = (RadioButton)itemView.findViewById(R.id.rectangle);
            chkSelected = (CheckBox) itemView
                    .findViewById(R.id.chkSelectedStudent);
            chkSelectedStatus=(CheckBox)itemView.findViewById((R.id.chkSelectedStatus));
            chkSelectedAbsent=(CheckBox)itemView.findViewById((R.id.chkSelectedAbsent));
            chkSelectedOnLeave=(CheckBox)itemView.findViewById((R.id.chkSelectedOnLeave));


          // createListener(chkSelectedStatus);
        //  createListener(chkSelectedAbsent);
         //  createListener(chkSelectedOnLeave);

        }

        private void createListener(CheckBox chkSelectedStatus) {




            chkSelectedStatus.setOnClickListener( new View.OnClickListener() {
                StringBuilder checkeditems = new StringBuilder();
                @Override
                public void onClick(View v) {
                    //is checkbox checked?



                    if (((CheckBox) v).isChecked()) {

                        String checkboxname = ((CheckBox) v).getText().toString();
                        StudentAttandenceModel studentAttandenceModel = new StudentAttandenceModel() ;

                        int position = 0;

                        Toast.makeText(mContext, checkboxname, Toast.LENGTH_LONG).show();

                        if(checkboxname.equalsIgnoreCase("Present"))
                        {

                            studentAttandenceModel.setPresentStatus(checkboxname);
                            StudentAttandenceModel studentsDetails = studentsDetailsList.get(position);



                            final int pos = position;

                            //  String present="Present";

                           CheckBox cb = (CheckBox) v;
                            StudentsDetails contact = (StudentsDetails) cb.getTag();

                            contact.setSelected(cb.isChecked());
                            studentsDetailsList.get(pos).setSelected(cb.isChecked());

                            Toast.makeText(
                                    v.getContext(),
                                    "Clicked on Checkbox: " + cb.getText() + " is "
                                            + cb.isChecked(), Toast.LENGTH_LONG).show();

                            Toast.makeText(
                                    v.getContext(),
                                    "Clicked on status present: " + checkboxname + " is "
                                            + checkboxname, Toast.LENGTH_LONG).show();
                          //  display.setText(display.getText().toString()+"\nEgg");
                        }

                        else if(checkboxname.equalsIgnoreCase("Absent"))
                        {


                            Toast.makeText(
                                    v.getContext(),
                                    "Clicked on status Absent: " + checkboxname + " is "
                                            + checkboxname, Toast.LENGTH_LONG).show();
                          //  display.setText(display.getText().toString()+"\nHotdog");

                            studentAttandenceModel.setPresentStatus(checkboxname);

                        }

                        else if(checkboxname.equalsIgnoreCase("On Leave"))
                        {



                            studentAttandenceModel.setPresentStatus(checkboxname);

                            Toast.makeText(
                                    v.getContext(),
                                    "Clicked on status present: " + checkboxname + " is "
                                            + checkboxname, Toast.LENGTH_LONG).show();
                           // display.setText(display.getText().toString()+"\nCheese");
                        }





                    }

                    else if (((CheckBox) v).isChecked()==false)
                    {
                        String checkboxname = ((CheckBox) v).getText().toString();

                        if(checkboxname.equalsIgnoreCase("Present"))
                        {
                         /*  String present="Present";


                            Toast.makeText(
                                    v.getContext(),
                                    "Clicked on status present: " + present + " is "
                                            + present, Toast.LENGTH_LONG).show();*/
                         //   display.setText(display.getText().toString().replace("\nEgg", ""));
                        }

                       else if(checkboxname.equalsIgnoreCase("Absent"))
                        {
                          //  display.setText(display.getText().toString().replace("\nHotdog", ""));
                        }

                        else if(checkboxname.equalsIgnoreCase("On Leave"))
                        {
                         //   display.setText(display.getText().toString().replace("\nCheese", ""));
                        }
                    }

                }
            });

        }
    }
    public List<StudentAttandenceModel> getStudentsDetailsList(){
      return   studentsDetailsList ;
    }

}
