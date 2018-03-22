package com.example.medianet.proschool;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 24-06-2017.
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder>{

    Context mContext;
    List<Vehicles> vehiclesList = new ArrayList<Vehicles>();
    ArrayList<Vehicles> vehiclesArrayList;
   // OnItemClickListener listener;
    private ArrayList countries;



     public VehicleAdapter(Context mContext, ArrayList<Vehicles> vehiclesList){
        this.mContext = mContext;
        this.vehiclesList = vehiclesList;
        this.vehiclesArrayList = new ArrayList<Vehicles>();
        this.vehiclesArrayList.addAll(vehiclesList);

    }


   /* public VehicleAdapter(Context mContext, ArrayList<Vehicles> vehiclesList,OnItemClickListener listener){
        this.mContext = mContext;
        this.vehiclesList = vehiclesList;
        this.vehiclesArrayList = new ArrayList<Vehicles>();
        this.vehiclesArrayList.addAll(vehiclesList);
        this.listener = listener;

    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_cart_list_item, parent, false);
       // ViewHolder viewHolder=new ViewHolder(v);
      /*  v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getPosition());
            }
        });*/

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


       // holder.bind(vehiclesList.get(position), listener);

        Vehicles vehicles = vehiclesList.get(position);
        if (vehicles != null){
            holder.name.setText(vehicles.getName());
            holder.description.setText(vehicles.getCode());
          //  holder.price.setText(vehicles.getCode());
        }
    }




    @Override
    public int getItemCount() {
        return vehiclesList.size();
    }


    public void addItem(Vehicles country) {
        vehiclesList.add(country);
      //  notifyItemInserted(vehiclesList.size());
      //  mAdapter.notifyItemInserted(mMessages.size() - 1);
    }
    public void removeItem(int position) {
        vehiclesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, vehiclesList.size());
    }
    public void restoreItem(Vehicles item, int position) {
        vehiclesList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
/*

    public VehicleAdapter(Context mContext, ArrayList<Vehicles> data, CustomItemClickListener listener) {
        this.vehiclesList = data;
        this.mContext = mContext;
        this.listener = listener;
    }
*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
      //  public TextView  code;

        public TextView name, description, price;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
         //   price = (TextView)itemView.findViewById(R.id.price);
           // thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            viewBackground = (RelativeLayout)itemView.findViewById(R.id.view_background);
            viewForeground =(RelativeLayout)itemView.findViewById(R.id.view_foreground);

        }

      /*  public void bind(final Vehicles item, final OnItemClickListener listener) {

          name.setText(item.getName());
          description.setText(item.getCode());
          //  price.setText(item.getStatus());
         //   Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {

                    listener.onItemClick(item);

                }
            });

        }*/

    }
    /*public interface OnItemClickListener {
        void onItemClick(Vehicles item);

    }*/
}
