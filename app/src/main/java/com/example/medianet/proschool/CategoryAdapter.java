package com.example.medianet.proschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by personal on 08-05-2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Categories> categoriesArrayList;
    private List<Categories> categoriesList;

    public CategoryAdapter(Context mContext, ArrayList<Categories> categoriesArrayList){
        this.mContext = mContext;
        this.categoriesList = categoriesArrayList;
        this.categoriesArrayList = new ArrayList<Categories>();
        this.categoriesArrayList.addAll(categoriesList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Categories categories = categoriesList.get(position);
        if (categories != null){
            holder.categoryName.setText(categories.getName());
        }
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView categoryName;
        public ImageView edit, cancel;
        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            cancel = (ImageView) itemView.findViewById(R.id.cancel);
        }
    }
}
