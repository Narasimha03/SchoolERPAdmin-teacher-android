/*
package com.example.medianet.proschool.suresh.examination;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medianet.proschool.R;

class MyListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if(arrText != null && arrText.length != 0){
                return arrText.length;    
            }
            return 0;
        }
 
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arrText[position];
        }
 
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            //ViewHolder holder = null;
            final ViewHolder holder;
            if (convertView == null) {
 
                holder = new ViewHolder();

              //  LayoutInflater inflater = BulkEvaluationFragment.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.bulk_eval_mark_list_layout, null);

                // convertView = inflater.inflate(R.layout.bulk_eval_mark_list_layout, parent, false);
               // BulkMarksAdpter.MyViewHolder holder = new BulkMarksAdpter.MyViewHolder(view);
              //  LayoutInflater inflater = this.getLayoutInflater();
             //   convertView = inflater.inflate(R.layout.bulk_eval_mark_list_layout, null);
              holder.textView1 = (TextView) convertView.findViewById(R.id.student);
                holder.editText1 = (EditText) convertView.findViewById(R.id.marksObtained2);
 
                convertView.setTag(holder);
 
            } else {
 
                holder = (ViewHolder) convertView.getTag();
            }
 
            holder.ref = position;
 
            holder.textView1.setText(arrText[position]);
            holder.editText1.setText(arrTemp[position]);
            holder.editText1.addTextChangedListener(new TextWatcher() {
 
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
 
                }
 
                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                        int arg3) {
                    // TODO Auto-generated method stub
 
                }
 
                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    arrTemp[holder.ref] = arg0.toString();
                }
            });
 
            return convertView;
        }
 
        private class ViewHolder {
            TextView textView1;
            EditText editText1;
            int ref;
        }
 
 
    }*/
