package com.example.medianet.proschool.suresh.examination;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medianet.proschool.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Parsania Hardik on 29-Jun-17.
 */
public class BulkMarksAdpter extends RecyclerView.Adapter<BulkMarksAdpter.MyViewHolder> {
    private String quantities [];
    private String marks [];

    private LayoutInflater inflater;
    public static List<BulkEvaluationModel> bulkMarkslArrayList;
    public static List<String> listSend=new ArrayList<String >();
    public String s1;
    public String s2;
    private Context ctx;
    String marksEdit;

    public BulkMarksAdpter(Context ctx, ArrayList<BulkEvaluationModel> bulkMarkslArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.bulkMarkslArrayList = bulkMarkslArrayList;
        this.ctx = ctx;
        this.quantities = new String[getItemCount()];
        this.marks = new String[getItemCount()];

    }

    @Override
    public BulkMarksAdpter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.bulk_eval_mark_list_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final BulkMarksAdpter.MyViewHolder holder, int position) {
        BulkEvaluationModel studentsDetails = bulkMarkslArrayList.get(position);



        if (studentsDetails != null) {
            holder.student.setText(studentsDetails.getStdName());
            holder.maxMarks.setText(studentsDetails.getMaxMarks());
            marks[holder.getAdapterPosition()] =
                  studentsDetails.getStdId();
           // marksEdit=holder.editMarksObt.getText().toString();
           // studentsDetails.setReadEdValue(marksEdit);
          //  studentsDetails.setReadEdValue(test);
            //   holder.addNo.setText(studentsDetails.getAdmNo());
            // holder.rollNo.setText(studentsDetails.getRollNo());

            // holder.stdName.setText(studentsDetails.getStdName());
            holder.editMarksObt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                quantities[holder.getAdapterPosition()] =
                              holder.editMarksObt.getText().toString();


                  //  quantities[holder.getAdapterPosition()] =
                      //      holder.editMarksObt.getText().toString();
                      //      holder.editMarksObt.getText().toString();
                 //   quantities[holder.getAdapterPosition()]=   holder.student.getText().toString();
                    //  escrito[getItemViewType(count)] = s.toString();
                   // listSend.get(count).getStdName();
                   // listSend.get(count).getStdName();

              //   s1=        bulkMarkslArrayList.set(holder.getAdapterPosition(), holder.editMarksObt.getText().toString());
              //  s2=  bulkMarkslArrayList.set(holder.getAdapterPosition(),  holder.student.getText().toString());
                    BulkEvaluationModel bulkEvaluationModel = bulkMarkslArrayList.get(position);



                    //    System.out.println("arr: " + Arrays.toString(new List[]{getQuantities()}));

                    //bulkEvaluationModel.setReadEdValue(String.valueOf(getQuantities()));
                  //  bulkEvaluationModel.setStdName(s1);
                  //  bulkEvaluationModel.setReadEdValue(s2);

                    // bulkEvaluationModel.setReadEdValue(test);

                    // bulkEvaluationModel.setReadEdValue(test);


             /*   String test=s.toString();
                Log.e("test",test);*/


                    //  bulkEvaluationModel.setReadEdValue(s);
               /* BulkEvaluationModel set = bulkMarkslArrayList.set(holder.ref, bulkMarkslArrayList.get(getItemViewType(count)));
                String name=set.getStdId();
                String marks = holder.editMarksObt.getText().toString();

                Log.e("edit value",name+" "+marks);
                set.setReadEdValue(marks);
*/
               /* BulkEvaluationModel bulkEvaluationModel=bulkMarkslArrayList.get(getItemViewType(count));

                String bulk= bulkEvaluationModel.getStdId();
                String marks= s.toString();
                Log.e("student Text",bulk+" "+s+" "+marks);*/
                    // String bulk= bulkEvaluationModel.getStdId();
                    // String marks= s.toString();

                    //  Log.e("student Text",bulk+" "+s);

                    //  bulkMarkslArrayList.set(getAdapterPosition(), s.toString());
                    // bulkMarkslArrayList.set(getAdapterPosition(),);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            //BulkEvaluationModel bulkEvaluationModel = bulkMarkslArrayList.get(getItemViewType(viewType));
            //String std=   bulkEvaluationModel.getStdId();
            //Log.e("std",std);
        }





    }
    @Override
    public int getItemViewType(final int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return bulkMarkslArrayList.size();
    }
    public String [] getQuantities(){
        return this.quantities;
    }
    public String [] getMarks(){
        return this.marks;
    }

    public List<BulkEvaluationModel> getData(){
        return this.bulkMarkslArrayList;
    }
    public List<BulkEvaluationModel> getStName(){
        return this.bulkMarkslArrayList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView student,maxMarks;

        private EditText editMarksObt;
        public int ref;

        public MyViewHolder(View itemView) {
            super(itemView);

            student = (TextView) itemView.findViewById(R.id.student2);
           maxMarks = (TextView) itemView.findViewById(R.id.maxMarks2);
            editMarksObt = (EditText) itemView.findViewById(R.id.marksObtained2);


           // MyTextWatcher textWatcher = new MyTextWatcher(editMarksObt);
           // editMarksObt.addTextChangedListener(textWatcher);
        }

    }
}
















/*
package com.example.medianet.proschool.suresh.examination;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medianet.proschool.EvalMarkAdapter;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.examination.BulkEvaluationModel;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by JANI on 17-06-2017.
 *//*


public class BulkMarksAdpter extends RecyclerView.Adapter<BulkMarksAdpter.ViewHolder>{

    Context mContext;
    ArrayList<BulkEvaluationModel> evalMarksList;
    ArrayList<BulkEvaluationModel> evalMarksArrayList;

    public BulkMarksAdpter(Context mContext, ArrayList<BulkEvaluationModel> evalMarksList){
        this.mContext = mContext;
        this.evalMarksList = evalMarksList;
        this.evalMarksArrayList = new ArrayList<BulkEvaluationModel>();
        this.evalMarksArrayList.addAll(evalMarksList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bulk_eval_mark_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BulkEvaluationModel bulkEvaluationModel = evalMarksList.get(position);
        if (bulkEvaluationModel != null){
            holder.student.setText(bulkEvaluationModel.getStdName());
            holder.maxMarks.setText(bulkEvaluationModel.getMaxMarks());

            holder.editMarksObt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                   */
/* quantities[holder.getAdapterPosition()] =
                            Integer.parseInt(holder.numPicker.getText().toString());*//*

                    //evalMarksList.set(holder.getAdapterPosition(), Integer.parseInt(holder.editMarksObt.getText().toString()));
                    String name = holder.editMarksObt.getText().toString();
                    Log.e("edit value",name);
                    bulkEvaluationModel.setReadEdValue(name);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        // holder.editMarksObt.setText(bulkEvaluationModel.getMaxMarks());


         */
/*   String name = holder.editMarksObt.getText().toString();
            Log.e("edit value",name);
            bulkEvaluationModel.setReadEdValue(name);*//*



        }
    }

    @Override
    public int getItemCount() {
        return evalMarksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView student, maxMarks;
        public EditText editMarksObt;

        public ViewHolder(View itemView) {
            super(itemView);
            student = (TextView) itemView.findViewById(R.id.student2);
            maxMarks = (TextView) itemView.findViewById(R.id.maxMarks2);
            editMarksObt = (EditText) itemView.findViewById(R.id.marksObtained2);

        }
    }
}
*/
