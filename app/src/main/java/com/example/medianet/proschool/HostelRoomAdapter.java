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

public class HostelRoomAdapter extends RecyclerView.Adapter<HostelRoomAdapter.ViewHolder>{
    Context mContext;
    List<HostelRoom> hostelRoomList;
    ArrayList<HostelRoom> hostelRoomArrayList;

    public HostelRoomAdapter(Context mContext, ArrayList<HostelRoom> hostelRoomList){
        this.mContext = mContext;
        this.hostelRoomList = hostelRoomList;
        this.hostelRoomArrayList = new ArrayList<HostelRoom>();
        this.hostelRoomArrayList.addAll(hostelRoomList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hostel_room_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HostelRoom hostelRoom = hostelRoomList.get(position);
        if (hostelRoom != null){
            holder.roomNo.setText(hostelRoom.getNo());
            holder.hostel.setText(hostelRoom.getHostel());
            holder.roomType.setText(hostelRoom.getType());
            holder.noBed.setText(hostelRoom.getNobed());
            holder.cost.setText(hostelRoom.getCost());
        }
    }

    @Override
    public int getItemCount() {
        return hostelRoomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView roomNo, hostel, roomType, noBed, cost;
        public ViewHolder(View itemView) {
            super(itemView);
            roomNo = (TextView) itemView.findViewById(R.id.roomNo);
            hostel = (TextView) itemView.findViewById(R.id.hostel);
            roomType = (TextView) itemView.findViewById(R.id.roomType);
            noBed = (TextView) itemView.findViewById(R.id.noBed);
            cost = (TextView) itemView.findViewById(R.id.cost);
        }
    }
}
