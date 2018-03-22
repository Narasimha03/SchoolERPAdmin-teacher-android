package com.example.medianet.proschool.suresh.academics.lessiontracker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.StudentAttandenceModel;
import com.example.medianet.proschool.StudentAttendanceAdapter;
import com.example.medianet.proschool.suresh.academics.lessiontracker.modelclass.Chapters;
import com.example.medianet.proschool.suresh.academics.lessiontracker.modelclass.Subjects;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.StudentGraphModel;
import com.example.medianet.proschool.suresh.feemodule.feecollection.FeeCollection;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JANI on 17-06-2017.
 */

public class LessionTrackerAdapter extends RecyclerView.Adapter<LessionTrackerAdapter.ViewHolder>{

    Context mContext;
    ArrayList<Subjects> evalMarksList;
  //  ArrayList<Chapters> chaptersArray;

    ArrayList<Subjects> listMarksSubjectName;

    ArrayList<Subjects> evalMarksArrayList;

    ArrayList<Chapters> chaptersArrayList;

    public LessionTrackerAdapter(Context mContext, ArrayList<Subjects> evalMarksList){
        this.mContext = mContext;
        this.evalMarksList = evalMarksList;
      //  this.chaptersArray=chaptersArray;
        this.evalMarksArrayList = new ArrayList<Subjects>();
        this.evalMarksArrayList.addAll(evalMarksList);
       // this.evalMarksList.addAll(chaptersArray);

        // this.evalMarksArrayList1.addAll(listMarksSubjectName);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lession_tracker_list_graph_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //     ArrayList<Chapters> chapters1=new ArrayList<>();
        Subjects evalMarks = evalMarksList.get(position);
        System.out.println("chapter in size"+evalMarksArrayList.size());

        //    Chapters chapters = chaptersArray.get(position);

        holder.subjectTitle.setText(evalMarks.getSubjectName());
     //  ArrayList<Chapters> chaptersData=new ArrayList<Chapters>();
  //    chaptersData=
       // System.out.println("chapter in size"+evalMarks.getChildList().size());

/*

        for (int a = 0; a <  evalMarks.getChildList().size(); a++) {
    String chapterCode=  evalMarks.getChildList().get(a).getChapter_code();
    System.out.println("chapter in adapter"+chapterCode);
            holder.chapterTitle.setText(chapterCode);

        }
*/

/*
        ArrayList<LessionTrackerModel> subjectsData = evalMarksList;
        System.out.println("subjects size" + subjectsData.size());

        for (int i = 0; i < subjectsData.size(); i++) {

            String subjectsDisplay = subjectsData.get(i).getSubjectName();
            System.out.println("subjects adapter data" + subjectsDisplay);

        }*/

      /*  subjectsData.add(evalMarks);

        for (int i = 0; i < subjectsData.size(); i++) {
            String subjectsDisplay=subjectsData.get(i).getSubjectName();*/


            // System.out.println("Subjects Data Array"+name.getSubjectName()+" "+name.getChildList().toString());


          //  String sub = name.getSubjectName();
      //      holder.subjectTitle.setText(evalMarks.getSubjectName());

            // ArrayList<Chapters> chapters3 = name.getChildList();


           // Chapters chapters2 = chapters3.get(position);

            //evalMarks.setChildList(chapters1.get(position));


            //  String chap= String.valueOf(evalMarks.getChildList());

            //  System.out.println("chapterCode adapter"+chap);

            //  Chapters chapters=new Chapters();
            // ArrayList<Chapters> chapters1=new ArrayList<>();
            //  evalMarks.setChildList(chapters.getChapter_code());
            //chapters1.
         //   String chapterCode = chapters2.getChapter_code();
            //  String compTopics = chapters2.getCompleted_topics();
            // String remTopics = chapters2.getRemaining_topics();
         //   System.out.println("chapterCode adapter" + chapterCode);

          /*  if (name != null) {
            //    holder.chapterTitle.setText(chapterCode);
            }*/
        }

            // Log.e("chapterCode adapter",chapterCode);


            //  Chapters chapters=evalMarksList.get(position);
            //  String chapterCode= chapters.getChapter_code();
            // chapters.getCompleted_topics();
            //  chapters.getRemaining_topics();
//evalMarks.getChildList().
            //  evalMarks.setChildList(chapterCode);


       /* Glide.with(mContext)
                .load(evalMarks.getImageDisplay())
                .into(holder.imgUser);
        holder.imgUser.setVisibility(View.VISIBLE);*/
     /*       if (evalMarks != null) {
                holder.subjectTitle.setText(evalMarks.getSubjectName());
                holder.chapterTitle.setText(chapterCode);


                //     holder.mChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) mContext);
                holder.mChart.setDrawGridBackground(false);
                holder.mChart.getDescription().setEnabled(false);
                holder.mChart.setScaleEnabled(false);

                holder.mChart.setPinchZoom(false);
                holder.mChart.setTouchEnabled(false);
                holder.mChart.setDrawBarShadow(false);
                holder.mChart.setDrawValueAboveBar(true);
                holder.mChart.setHighlightFullBarEnabled(false);
                holder.mChart.setVisibleYRangeMaximum(100, YAxis.AxisDependency.LEFT);
                //mChart.setVisibleXRangeMaximum(100);
                holder.mChart.getAxisLeft().setEnabled(false);
                holder.mChart.getAxisRight().setDrawGridLines(false);
                holder.mChart.getAxisRight().setDrawZeroLine(true);
                holder.mChart.getAxisLeft().setLabelCount(0, false);
                holder.mChart.getAxisLeft().setTextSize(20f);
                holder.mChart.setDrawGridBackground(false);

                XAxis xAxis = holder.mChart.getXAxis();
                xAxis.setCenterAxisLabels(true);
                xAxis.setDrawAxisLine(false);
                xAxis.setGranularity(1f);

                xAxis.setGranularityEnabled(true);
                xAxis.setLabelRotationAngle(0f);
                xAxis.setDrawGridLines(false);
                xAxis.setEnabled(false);

                YAxis rightYAxis = holder.mChart.getAxisRight();
                rightYAxis.setEnabled(false);

                holder.mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                holder.mChart.setDrawValueAboveBar(false);
                //  new BarEntry(5, new float[]{85, 7, 8});
                ArrayList<BarEntry> yValues = new ArrayList<>();
                yValues.add(new BarEntry(5, new float[]{Float.parseFloat(compTopics), Float.parseFloat(remTopics)}));           *//* yValues.add(new BarEntry(5, new float[]{85, 7, 8}));
            yValues.add(new BarEntry(15, new float[]{87, 7, 6}));
            yValues.add(new BarEntry(25, new float[]{80, 10, 10}));
            yValues.add(new BarEntry(35, new float[]{79, 11, 10}));*//*

                BarDataSet set = new BarDataSet(yValues, "");
                set.setAxisDependency(YAxis.AxisDependency.RIGHT);
                BarData data = new BarData(set);
                data.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                        if (value > 0) {
                            return Math.round(value) + "";
                        } else {
                            return "";
                        }
                    }
                });
                data.setValueTextSize(15f);
                set.setColors(Color.rgb(95, 182, 156), Color.rgb(209, 190, 168), Color.rgb(110, 127, 128));
                set.setStackLabels(new String[]{
                        "Completed", "Pending"
                });          //  holder.statusPresent.setText(evalMarks.getStdAttendance());

                data.setBarWidth(3f); // set custom bar width
                holder.mChart.setData(data);
                holder.mChart.setFitBars(true); // make the x-axis fit exactly all bars
                holder.mChart.invalidate(); // refresh

                holder.mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
                holder.mChart.setScaleXEnabled(false);

                Legend legend = holder.mChart.getLegend();
                legend.setEnabled(true);
                legend.setTextSize(10);
                legend.setFormSize(10);
                legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                legend.setStackSpace(10);


            }*/







    @Override
    public int getItemCount() {
        return evalMarksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        private HorizontalBarChart mChart;

        public TextView subjectTitle, chapterTitle;
      //  public CircleImageView imgUser;

        public ViewHolder(View itemView) {
            super(itemView);
            subjectTitle = (TextView) itemView.findViewById(R.id.subject_title);
            chapterTitle = (TextView) itemView.findViewById(R.id.chapter_title);
          //  imgUser = itemView.findViewById(R.id.profile_image);


            mChart = (HorizontalBarChart) itemView.findViewById(R.id.student_horizontal);

        }
    }
}

