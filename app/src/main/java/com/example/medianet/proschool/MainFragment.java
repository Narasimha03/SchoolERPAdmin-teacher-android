package com.example.medianet.proschool;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by JANI on 22-04-2017.
 */

public class MainFragment extends Fragment{
    public MainFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View displayLayout = inflater.inflate(R.layout.main_layout_fragment, container, false);
        GraphView graph = (GraphView) displayLayout.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        // use static labels for horizontal and vertical labels
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Jan", "Feb", "Mar"});
        staticLabelsFormatter.setVerticalLabels(new String[] {"0", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#000000"));
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#AA66CC"));
        //graph.getGridLabelRenderer().
        //
        graph.setTitle("Fee Collection & Expenses");
        graph.setTitleColor(Color.parseColor("#3F51B5"));
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");

        return displayLayout;
    }
}
