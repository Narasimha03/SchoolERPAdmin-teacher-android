package com.example.medianet.proschool.suresh.swipetodelete;

/**
 * Created by ravi on 26/09/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.Vehicles;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {
    private Context context;
    private List<Vehicles> cartList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);


            name = (TextView)view.findViewById(R.id.name);
            description = (TextView)view.findViewById(R.id.description);
            price = (TextView)view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            viewBackground = (RelativeLayout)view.findViewById(R.id.view_background);
            viewForeground =(RelativeLayout)view.findViewById(R.id.view_foreground);
        }
    }


    public CartListAdapter(Context context, List<Vehicles> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_cart_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Vehicles item = cartList.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getCode());
        holder.price.setText("₹" + item.getStatus());

       /* Glide.with(context)
                .load(item.getThumbnail())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position) {
        cartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Vehicles item, int position) {
        cartList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
