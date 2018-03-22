package com.example.medianet.proschool.suresh.examination;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.medianet.proschool.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by JANI on 11-02-2018.
 */

public class BulkAdapter extends RecyclerView.Adapter<BulkAdapter.ViewHolder> {

    private Context mContext;
    private LinkedHashMap<String, String> classMap;
    private OnEditTextChanged onEditTextChanged;

    // Callback....
    public interface OnEditTextChanged {
        void onTextChanged(String key, String value);
    }

    public BulkAdapter(Context mContext, LinkedHashMap<String, String> classMap, OnEditTextChanged mCallback) {
        this.mContext = mContext;
        this.classMap = new LinkedHashMap<String, String>();
        this.classMap.putAll(classMap);
        this.onEditTextChanged = mCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View complaintLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.bulk_eval_mark_list_layout, parent, false);
        return new ViewHolder(complaintLayout);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.nameTV.setText(getKeyByIndex(classMap, position));
        holder.idET.setText(getValueByIndex(classMap, position));
        // TextWatcher
        holder.idET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                onEditTextChanged.onTextChanged(getKeyByIndex(classMap, holder.getAdapterPosition()), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return classMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View container;
        TextView nameTV;
        EditText idET;

        private ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
            nameTV = (TextView) itemView.findViewById(R.id.student2);
            idET = (EditText) itemView.findViewById(R.id.marksObtained2);
        }
    }

    private String getValueByIndex(LinkedHashMap<String, String> hMap, int index) {
        return (String) hMap.values().toArray()[index];
    }

    private String getKeyByIndex(LinkedHashMap<String, String> hMap, int index) {
        return (String) hMap.keySet().toArray()[index];
    }
}
