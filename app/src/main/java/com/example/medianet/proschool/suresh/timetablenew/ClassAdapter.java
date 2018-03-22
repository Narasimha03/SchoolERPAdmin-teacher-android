package com.example.medianet.proschool.suresh.timetablenew;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.medianet.proschool.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by JANI on 11-02-2018.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    private Context mContext;
    private LinkedHashMap<String, String> classMap;
    private OnItemClick mCallback;
    private int row_index;

    // Callback....
    public interface OnItemClick {
        public void OnClick(int position);
    }

    public ClassAdapter(Context mContext, LinkedHashMap<String, String> classMap, OnItemClick mCallback) {
        this.mContext = mContext;
        this.classMap = new LinkedHashMap<String, String>();
        this.classMap.putAll(classMap);
        this.mCallback = mCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View complaintLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        return new ViewHolder(complaintLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.classBTN.setText(getValueByIndex(classMap, position));
       // holder.classBTN.setBackgroundResource(R.drawable.admin_curriculum_round_background);
        if(row_index==position){
            holder.classBTN.setBackgroundResource(R.drawable.admin_curriculum_round_background_selected);
            holder.classBTN.setTextColor(mContext.getResources().getColor(R.color.white));

        }
        else
        {
            holder.classBTN.setBackgroundResource(R.drawable.admin_curriculum_round_background);
            holder.classBTN.setTextColor(mContext.getResources().getColor(R.color.color_dash));

        }

    }

    @Override
    public int getItemCount() {
        return classMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View container;
        TextView classBTN;

        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            container.setClickable(true);
            classBTN = itemView.findViewById(R.id.classBTN);
            classBTN.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mCallback != null) {
                boolean check=false;

                mCallback.OnClick(getAdapterPosition());

                row_index=getAdapterPosition();
                notifyDataSetChanged();

             /*   if(getAdapterPosition()==getItemId()) {
                    classBTN.setBackgroundResource(R.color.colorPrimary);
                }
                else
                {
                    classBTN.setBackgroundResource(R.color.colorPrimary);

                }*/

                {

                }


            }



        }




    }

    public String getValueByIndex(LinkedHashMap<String, String> hMap, int index){
        return (String) hMap.values().toArray()[index];
    }
}
