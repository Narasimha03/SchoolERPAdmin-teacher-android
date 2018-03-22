package com.example.medianet.proschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 24-04-2017.
 */

public class TestingAdapter extends RecyclerView.Adapter<TestingAdapter.ViewHolder> {
    Context mContext;
    List<String> listTime;
    List<String> listName;
    ArrayList<String> allTime;
    ArrayList<String> allName;

    public TestingAdapter(Context mContext, ArrayList<String> allTime, ArrayList<String> allName) {
        this.mContext = mContext;
        this.listTime = allTime;
        this.listName = allName;
        this.allTime = new ArrayList<String>();
        this.allTime.addAll(listTime);
        this.allName = new ArrayList<String>();
        this.allName.addAll(listName);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recentuserView = LayoutInflater.from(parent.getContext()).inflate(R.layout.testing_layout, parent, false);
        return new ViewHolder(recentuserView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(listName.get(position));
        holder.time.setText(listTime.get(position));
    }

    @Override
    public int getItemCount() {
        return listName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View Container;
        TextView time, name;

        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
