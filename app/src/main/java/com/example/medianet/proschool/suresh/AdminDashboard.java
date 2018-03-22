package com.example.medianet.proschool.suresh;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.admindashboard.AdminCurriculumAdapter;
import com.example.medianet.proschool.suresh.admindashboard.AdminCurriculumClass;
import com.example.medianet.proschool.suresh.admindashboard.AdminSectionsAdapter;
import com.example.medianet.proschool.suresh.admindashboard.AdminSectionsClass;
import com.example.medianet.proschool.suresh.admindashboard.AdminStudentAttendanceAdapter;
import com.example.medianet.proschool.suresh.admindashboard.AdminStudentAttendanceClass;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harish on 29-11-2017.
 */

public class AdminDashboard extends AppCompatActivity implements OnChartValueSelectedListener {

    //Admin Class Title
    private List<AdminCurriculumClass> adminCurriculumClassList = new ArrayList<>();
    private RecyclerView adminCurriculumRecyclerView;
    private AdminCurriculumAdapter mAdminCurAdapter;
    AdminCurriculumAdapter.OnItemClickListener listener;

    //Admin Sections
    private List<AdminSectionsClass> adminSectionsClassList = new ArrayList<>();
    private RecyclerView adminSectionsRecyclerView;
    private AdminSectionsAdapter mAdminSectionAdapter;

    //Admin Class Title for Student Attendance Reports
    private List<AdminStudentAttendanceClass> adminStuAttClassList = new ArrayList<>();
    private RecyclerView adminStudentAttendanceRecyclerView;
    private AdminStudentAttendanceAdapter mAdminStuAttAdapter;
    AdminStudentAttendanceAdapter.OnItemClickListener studentListener;

    //Student Attendance Horizontal Bar
    private HorizontalBarChart mChart;

    //Staff Attendance

    private PieChart mAdminChart;
    private PieChart mTeachChart;
    private PieChart mNonTeachChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_dashboard_layout);

        //Admin Class Title RecyclerView
        adminCurriculumRecyclerView = findViewById(R.id.admin_classes_recyclerview);


        mAdminCurAdapter = new AdminCurriculumAdapter(adminCurriculumClassList, listener);
        RecyclerView.LayoutManager mCcLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adminCurriculumRecyclerView.setLayoutManager(mCcLayoutManager);
        adminCurriculumRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //adminCurriculumRecyclerView.setAdapter(mAdminCurAdapter);
        adminCurriculumRecyclerView.setAdapter(new AdminCurriculumAdapter (adminCurriculumClassList, new AdminCurriculumAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(AdminCurriculumClass item) {
                Toast.makeText(getApplicationContext(), item.getAdminCurClasses(), Toast.LENGTH_SHORT).show();
            }

        }));


        prepareCcData();

        //Admin Sections RecyclerView
        adminSectionsRecyclerView = findViewById(R.id.admin_section_recycler);


        mAdminSectionAdapter = new AdminSectionsAdapter(adminSectionsClassList);
        RecyclerView.LayoutManager mAdminSectionsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adminSectionsRecyclerView.setLayoutManager(mAdminSectionsLayoutManager);
        adminSectionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adminSectionsRecyclerView.setAdapter(mAdminSectionAdapter);
        /*adminSectionsRecyclerView.setAdapter(new AdminSectionsAdapter (adminSectionsClassList, new AdminCurriculumAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(AdminCurriculumClass item) {
                Toast.makeText(getApplicationContext(), item.getAdminCurClasses(), Toast.LENGTH_SHORT).show();
            }

        }));*/


        prepareAdminSectionsData();

        //Admin Class Title RecyclerView for Student Attendance

        adminStudentAttendanceRecyclerView = findViewById(R.id.admin_student_attendance_class_recyclerview);


        mAdminStuAttAdapter = new AdminStudentAttendanceAdapter(adminStuAttClassList, studentListener);
        RecyclerView.LayoutManager mStudentAttendanceLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adminStudentAttendanceRecyclerView.setLayoutManager(mStudentAttendanceLayoutManager);
        adminStudentAttendanceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //adminCurriculumRecyclerView.setAdapter(mAdminCurAdapter);
        adminStudentAttendanceRecyclerView.setAdapter(new AdminStudentAttendanceAdapter (adminStuAttClassList, new AdminStudentAttendanceAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(AdminStudentAttendanceClass item) {
                Toast.makeText(getApplicationContext(), item.getAdminStuAttClasses(), Toast.LENGTH_SHORT).show();
            }

        }));

        prepareAdminStudentAttendanceClassData();

        //Student Attendance Horizontal Bar

        mChart = findViewById(R.id.admin_student_attendance_bar_chart);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);
        mChart.setScaleEnabled(false);

        mChart.setPinchZoom(false);
        mChart.setTouchEnabled(true);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setVisibleYRangeMaximum(100, YAxis.AxisDependency.LEFT);
        mChart.setVisibleXRangeMaximum(100);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisRight().setDrawZeroLine(true);
        mChart.getAxisLeft().setLabelCount(0, false);
        mChart.getAxisLeft().setTextSize(9f);
        mChart.setDrawGridBackground(false);

        YAxis rightYAxis = mChart.getAxisRight();
        rightYAxis.setEnabled(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.setDrawValueAboveBar(false);

        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry(5, new float[]{ 85, 7, 8 }));
        yValues.add(new BarEntry(15, new float[]{ 87, 7, 6 }));
        yValues.add(new BarEntry(25, new float[]{ 80, 10, 10 }));
        yValues.add(new BarEntry(35, new float[]{ 79, 11, 10 }));

        BarDataSet set = new BarDataSet(yValues, "");
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        BarData data = new BarData(set);
        set.setColors(Color.rgb(95,182,156), Color.rgb(209,190,168), Color.rgb(110,127,128));
        set.setStackLabels(new String[]{
                "Present", "Leave", "Absent"
        });
        data.setBarWidth(7f); // set custom bar width
        mChart.setData(data);
        mChart.setFitBars(true); // make the x-axis fit exactly all bars
        mChart.invalidate(); // refresh

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(0f);
        xAxis.setDrawGridLines(false);

        final ArrayList<String> xEntrys = new ArrayList<>();
        xEntrys.add("Section A");
        xEntrys.add("Section B");
        xEntrys.add("Section C");
        xEntrys.add("Section D");

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return xEntrys.get((int) value % xEntrys.size());

            }
        });


        //Staff Attendance Code Starts

        //Staff Attendance ID's

        mAdminChart = findViewById(R.id.administration_attendance_piechart);
        mAdminChart.setBackgroundColor(Color.WHITE);

        mTeachChart = findViewById(R.id.teaching_staff_attendance_piechart);
        mTeachChart.setBackgroundColor(Color.WHITE);

        mNonTeachChart = findViewById(R.id.non_teaching_staff_attendance_piechart);
        mNonTeachChart.setBackgroundColor(Color.WHITE);

        /*mAdminChart Code*/

        mAdminChart.setUsePercentValues(true);
        mAdminChart.getDescription().setEnabled(false);
        mAdminChart.setOnChartValueSelectedListener(this);
        mAdminChart.setTouchEnabled(true);

        //mAdminChart.setCenterTextTypeface(mTfLight);
        //mAdminChart.setCenterText(generateCenterSpannableText());
        mAdminChart.setDrawHoleEnabled(true);
        mAdminChart.setHoleColor(Color.WHITE);
        mAdminChart.setExtraOffsets(5, 5, 5, 5);
        mAdminChart.setTransparentCircleColor(Color.WHITE);
        mAdminChart.setTransparentCircleAlpha(110);
        //mAdminChart.setCenterText("Administrative Staff Attendance");
        mAdminChart.setHoleRadius(50f);
        mAdminChart.setTransparentCircleRadius(55f);
        mAdminChart.setDrawEntryLabels(false);
        mAdminChart.setDrawCenterText(true);
        //mAdminChart.setCenterTextOffset(5,-5);
        mAdminChart.setRotationEnabled(false);
        mAdminChart.setHighlightPerTapEnabled(true);

        //mAdminChart.setMaxAngle(180f); // HALF CHART
        mAdminChart.setRotationAngle(180f);
        mAdminChart.setCenterTextSize(10);
        //mAdminChart.setCenterTextOffset(0, -20);

        setData(3, 100);

        mAdminChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mAdminChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setTextSize(12);
        l.setYEntrySpace(0f);
        l.setYOffset(5f);

        // entry label styling
        //mAdminChart.setEntryLabelColor(Color.BLACK);
        //mAdminChart.setEntryLabelTypeface(mTfRegular);
        mAdminChart.setEntryLabelTextSize(12f);
        /*int colorBlack = Color.parseColor("#000000");
        mAdminChart.setEntryLabelColor(colorBlack);*/

        /*mTeachChart code*/

        mTeachChart.setUsePercentValues(true);
        mTeachChart.getDescription().setEnabled(false);
        mTeachChart.setDrawHoleEnabled(true);
        mTeachChart.setHoleColor(Color.WHITE);
        mTeachChart.setExtraOffsets(5, 5, 5, 5);
        mTeachChart.setTransparentCircleColor(Color.WHITE);
        mTeachChart.setTransparentCircleAlpha(110);
        mTeachChart.setHoleRadius(50f);
        mTeachChart.setTransparentCircleRadius(55f);
        mTeachChart.setDrawEntryLabels(false);
        mTeachChart.setDrawCenterText(true);
        mTeachChart.setRotationEnabled(false);
        mTeachChart.setHighlightPerTapEnabled(true);
        mTeachChart.setRotationAngle(180f);
        mTeachChart.setCenterTextSize(10);

        setDataTwo(3, 100);

        mTeachChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend lTeach = mTeachChart.getLegend();
        lTeach.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lTeach.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lTeach.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lTeach.setDrawInside(false);
        lTeach.setXEntrySpace(7f);
        lTeach.setTextSize(12);
        lTeach.setYEntrySpace(0f);
        lTeach.setYOffset(5f);

        mTeachChart.setEntryLabelTextSize(12f);

        /*mNonTeachChart code*/

        mNonTeachChart.setUsePercentValues(true);
        mNonTeachChart.getDescription().setEnabled(false);
        mNonTeachChart.setDrawHoleEnabled(true);
        mNonTeachChart.setHoleColor(Color.WHITE);
        mNonTeachChart.setExtraOffsets(5, 5, 5, 5);
        mNonTeachChart.setTransparentCircleColor(Color.WHITE);
        mNonTeachChart.setTransparentCircleAlpha(110);
        mNonTeachChart.setHoleRadius(50f);
        mNonTeachChart.setTransparentCircleRadius(55f);
        mNonTeachChart.setDrawEntryLabels(false);
        mNonTeachChart.setDrawCenterText(true);
        mNonTeachChart.setRotationEnabled(false);
        mNonTeachChart.setHighlightPerTapEnabled(true);
        mNonTeachChart.setRotationAngle(180f);
        mNonTeachChart.setCenterTextSize(10);

        setDataThree(3, 100);

        mTeachChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend lNonTeach = mNonTeachChart.getLegend();
        lNonTeach.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lNonTeach.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lNonTeach.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lNonTeach.setDrawInside(false);
        lNonTeach.setXEntrySpace(7f);
        lNonTeach.setTextSize(12);
        lNonTeach.setYEntrySpace(0f);
        lNonTeach.setYOffset(5f);

        mNonTeachChart.setEntryLabelTextSize(12f);


    }



    //Admin Class List Data
    private void prepareCcData() {

        AdminCurriculumClass adminTotal = new AdminCurriculumClass("Class I");
        adminCurriculumClassList.add(adminTotal);

        adminTotal = new AdminCurriculumClass("Class II");
        adminCurriculumClassList.add(adminTotal);

        adminTotal = new AdminCurriculumClass("Class III");
        adminCurriculumClassList.add(adminTotal);

        adminTotal = new AdminCurriculumClass("Class IV");
        adminCurriculumClassList.add(adminTotal);

        adminTotal = new AdminCurriculumClass("Class V");
        adminCurriculumClassList.add(adminTotal);

        adminTotal = new AdminCurriculumClass("Class VI");
        adminCurriculumClassList.add(adminTotal);

        adminTotal = new AdminCurriculumClass("Class VII");
        adminCurriculumClassList.add(adminTotal);

        adminTotal = new AdminCurriculumClass("Class VIII");
        adminCurriculumClassList.add(adminTotal);

        adminTotal = new AdminCurriculumClass("Class IX");
        adminCurriculumClassList.add(adminTotal);

        adminTotal = new AdminCurriculumClass("Class X");
        adminCurriculumClassList.add(adminTotal);

        mAdminCurAdapter.notifyDataSetChanged();
    }

    //Admin Sections Data

    private void prepareAdminSectionsData() {

        AdminSectionsClass adminSectionsTotal = new AdminSectionsClass("Section A", "Telugu", "Maths", "Chemistry", "Lunch", "Physics", "English", "Social");
        adminSectionsClassList.add(adminSectionsTotal);

        adminSectionsTotal = new AdminSectionsClass("Section B", "Social", "Maths", "English", "Lunch", "Chemistry", "Telugu", "Physics");
        adminSectionsClassList.add(adminSectionsTotal);

        adminSectionsTotal = new AdminSectionsClass("Section C", "Maths", "Social", "Chemistry", "Lunch", "English", "Physics", "Telugu");
        adminSectionsClassList.add(adminSectionsTotal);

        adminSectionsTotal = new AdminSectionsClass("Section D", "Social", "Telugu", "Physics", "Lunch", "English", "Telugu", "Chemistry");
        adminSectionsClassList.add(adminSectionsTotal);

        mAdminSectionAdapter.notifyDataSetChanged();
    }

    //Admin Student Attendance Class Data
    private void prepareAdminStudentAttendanceClassData() {

        AdminStudentAttendanceClass adminStudentClassTotal = new AdminStudentAttendanceClass("Class I");
        adminStuAttClassList.add(adminStudentClassTotal);

        adminStudentClassTotal = new AdminStudentAttendanceClass("Class II");
        adminStuAttClassList.add(adminStudentClassTotal);

        adminStudentClassTotal = new AdminStudentAttendanceClass("Class III");
        adminStuAttClassList.add(adminStudentClassTotal);

        adminStudentClassTotal = new AdminStudentAttendanceClass("Class IV");
        adminStuAttClassList.add(adminStudentClassTotal);

        adminStudentClassTotal = new AdminStudentAttendanceClass("Class V");
        adminStuAttClassList.add(adminStudentClassTotal);

        adminStudentClassTotal = new AdminStudentAttendanceClass("Class VI");
        adminStuAttClassList.add(adminStudentClassTotal);

        adminStudentClassTotal = new AdminStudentAttendanceClass("Class VII");
        adminStuAttClassList.add(adminStudentClassTotal);

        adminStudentClassTotal = new AdminStudentAttendanceClass("Class VIII");
        adminStuAttClassList.add(adminStudentClassTotal);

        adminStudentClassTotal = new AdminStudentAttendanceClass("Class IX");
        adminStuAttClassList.add(adminStudentClassTotal);

        adminStudentClassTotal = new AdminStudentAttendanceClass("Class X");
        adminStuAttClassList.add(adminStudentClassTotal);

        mAdminStuAttAdapter.notifyDataSetChanged();
    }

    //Staff Attendance Code Starts

    private void setData(int count, float range) {

        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(89f, "Present"));
        entries.add(new PieEntry(2f, "Leave"));
        entries.add(new PieEntry(9f, "Absent"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        //dataSet.setSelectionShift(0f);
        //dataSet.setColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        data.setValueTextColor(Color.BLACK);
        //data.setValueTypeface(mTfLight);
        mAdminChart.setData(data);
        /*dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);*/
        mAdminChart.invalidate();
    }

    private void setDataTwo(int count, float range) {

        ArrayList<PieEntry> entriesTwo = new ArrayList<PieEntry>();

        entriesTwo.add(new PieEntry(80f, "Present"));
        entriesTwo.add(new PieEntry(10f, "Leave"));
        entriesTwo.add(new PieEntry(10f, "Absent"));

        PieDataSet dataSetTwo = new PieDataSet(entriesTwo, "");

        dataSetTwo.setSliceSpace(3f);
        dataSetTwo.setSelectionShift(5f);
        dataSetTwo.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSetTwo);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        dataSetTwo.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        data.setValueTextColor(Color.BLACK);
        mTeachChart.setData(data);
        mTeachChart.invalidate();
    }

    private void setDataThree (int count, float range) {

        ArrayList<PieEntry> entriesThree = new ArrayList<PieEntry>();

        entriesThree.add(new PieEntry(85f, "Present"));
        entriesThree.add(new PieEntry(5f, "Leave"));
        entriesThree.add(new PieEntry(10f, "Absent"));

        PieDataSet dataSetTwo = new PieDataSet(entriesThree, "");

        dataSetTwo.setSliceSpace(3f);
        dataSetTwo.setSelectionShift(5f);
        dataSetTwo.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData(dataSetTwo);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        dataSetTwo.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        data.setValueTextColor(Color.BLACK);
        mNonTeachChart.setData(data);
        mNonTeachChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


}