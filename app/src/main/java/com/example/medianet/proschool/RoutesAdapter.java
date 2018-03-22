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

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder>{
    private Context mContext;
    private List<Routes> routesList;
    private ArrayList<Routes> routesArrayList;

    public RoutesAdapter(Context mContext, ArrayList<Routes> routesArrayList){
        this.mContext = mContext;
        this.routesList = routesArrayList;
        this.routesArrayList = new ArrayList<Routes>();
        this.routesArrayList.addAll(routesList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Routes routes = routesList.get(position);
        if (routes != null){
            holder.title.setText(routes.getTitle());
            holder.vehicle.setText(routes.getCode());
            holder.code.setText(routes.getPickup_time());
            holder.fare.setText(routes.getDrop_time());
        }
    }

    @Override
    public int getItemCount() {
        return routesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView title, vehicle, code, fare;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            vehicle = (TextView) itemView.findViewById(R.id.vehicle);
            code = (TextView) itemView.findViewById(R.id.code);
            fare = (TextView) itemView.findViewById(R.id.fare);
        }
    }
}
