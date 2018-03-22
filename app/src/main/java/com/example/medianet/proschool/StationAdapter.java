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

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder>{

    private Context mContext;
    private List<Stations> stationsList;
    private ArrayList<Stations> stationsArrayList;

    public StationAdapter(Context mContext, ArrayList<Stations> stationsArrayList){
        this.mContext = mContext;
        this.stationsList = stationsArrayList;
        this.stationsArrayList = new ArrayList<Stations>();
        this.stationsArrayList.addAll(stationsList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stations_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stations stations = stationsList.get(position);
        if (stations != null){
            holder.name.setText(stations.getName());
            holder.code.setText(stations.getCode());
            holder.location.setText(stations.getLocation());
        }
    }

    @Override
    public int getItemCount() {
        return stationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView name, code, location;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            code = (TextView) itemView.findViewById(R.id.code);
            location = (TextView) itemView.findViewById(R.id.geoLocation);
        }
    }
}
