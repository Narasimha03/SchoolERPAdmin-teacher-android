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
 * Created by JANI on 03-06-2017.
 */

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.ViewHolder>{
    private Context mContext;
    private List<RoomType> roomTypeList;
    private ArrayList<RoomType> roomTypeArrayList;

    public RoomTypeAdapter(Context mContext, ArrayList<RoomType> roomTypeList){
        this.mContext = mContext;
        this.roomTypeList = roomTypeList;
        this.roomTypeArrayList = new ArrayList<RoomType>();
        this.roomTypeArrayList.addAll(roomTypeList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_type_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RoomType roomType = roomTypeList.get(position);
        if (roomType != null){
            holder.name.setText(roomType.getName());
        }
    }

    @Override
    public int getItemCount() {
        return roomTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View Container;
        private TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
