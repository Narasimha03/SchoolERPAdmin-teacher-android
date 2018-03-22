package com.example.medianet.proschool;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JANI on 21-04-2017.
 */

public class MenuExpandableAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<String> menuListTitle;
    private ArrayList<Integer> menuListIcon = new ArrayList<Integer>();
    private HashMap<String, List<String>> menuListDetail;
    MenuExpandableAdapter(Context context, List<String> menuListTitle, HashMap<String, List<String>> menuListDetail,
                          ArrayList<Integer> menuListIcon){
        this.context = context;
        this.menuListTitle = menuListTitle;
        this.menuListDetail = menuListDetail;
        this.menuListIcon = menuListIcon;
    }
    @Override
    public int getGroupCount() {
        return this.menuListDetail.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.menuListDetail.get(this.menuListTitle.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.menuListTitle.get(groupPosition);
    }

    public Object getGroupIcon(int groupPosition) {
        return this.menuListIcon.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.menuListDetail.get(this.menuListTitle.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        Integer listIcon = (Integer) getGroupIcon(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        icon.setImageResource(listIcon);
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.title);
        listTitleTextView.setText(listTitle);
        ImageView listTitleImageView = (ImageView) convertView.findViewById(R.id.imgPlus);
        // To hide group indicator when childs are not available....
        if (getChildrenCount(groupPosition) == 0){
            listTitleImageView.setVisibility(View.GONE);
            convertView.setClickable(false);
        } else {
            listTitleImageView.setVisibility(View.VISIBLE);
            listTitleImageView.setImageResource(isExpanded ? R.drawable.minus : R.drawable.add);
            listTitleTextView.setTypeface(null, isExpanded ? Typeface.BOLD : Typeface.NORMAL);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.itemName);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
