package com.example.medianet.proschool.suresh.timetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.timetable.EventsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 03-06-2017.
 */

public class TimeTableEventAdapter extends RecyclerView.Adapter<TimeTableEventAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<EventsModel> arrayListBook;
    private List<EventsModel> listBooks;

    public TimeTableEventAdapter(Context ctx, ArrayList<EventsModel> arrayListBook){
        this.mContext = ctx;
        this.listBooks = arrayListBook;
        this.arrayListBook = new ArrayList<EventsModel>();
        this.arrayListBook.addAll(listBooks);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_list_display_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EventsModel books = listBooks.get(position);
        if (books != null){
            holder.title.setText(books.getEventName());
            holder.date.setText(books.getDesc());

        }
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView title, date, subject, author, price, qty, rackNo;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.student);
            date = (TextView) itemView.findViewById(R.id.description);

        }
    }
}
