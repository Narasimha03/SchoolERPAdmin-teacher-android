package com.example.medianet.proschool.teacher.teacherdashboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.BookListFragment;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.HR.HrFragment;
import com.example.medianet.proschool.suresh.MainActivity;
import com.example.medianet.proschool.suresh.QuickDashboardClass;
import com.example.medianet.proschool.suresh.QuickDashboardDataModel;
import com.example.medianet.proschool.suresh.QuickDashboardRecyclerViewAdapter;
import com.example.medianet.proschool.suresh.activity.AllAssignmentFragment;
import com.example.medianet.proschool.suresh.activity.AllEmployeeDetails;
import com.example.medianet.proschool.suresh.activity.AllStudentDetails;
import com.example.medianet.proschool.suresh.alltabsfragment.AcademicsTabsFragment;
import com.example.medianet.proschool.suresh.alltabsfragment.ExaminationTabsFragment;
import com.example.medianet.proschool.suresh.alltabsfragment.StudentAttendanceTabsFragment;
import com.example.medianet.proschool.suresh.alltabsfragment.TransportFabsFragment;
import com.example.medianet.proschool.suresh.customprogress.CustomPrograssActivity;
import com.example.medianet.proschool.suresh.dashboard.NoticeBoardBackTask;
import com.example.medianet.proschool.suresh.dashboard.SchoolEventsBackTask;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.AdminDashboardEventAdapter;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.AdminDashboardEventClass;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.AdminDashboardNoticeBoardAdapter;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.AdminDashboardNoticeBoardClass;
import com.example.medianet.proschool.suresh.employeeattendancetabs.EmployeeAttendanceTabsFragment;
import com.example.medianet.proschool.suresh.employeeattendancetabs.monthlyadapterandbacktask.EmpAttMonthlyBackTask;
import com.example.medianet.proschool.suresh.feemodule.FeeModuleTabFragment;
import com.example.medianet.proschool.suresh.graphs.DemoBase;
import com.example.medianet.proschool.suresh.graphs.StackedBarActivityNegative;
import com.example.medianet.proschool.suresh.studentprofile.employeeprofile.EmployeeProfileAttendanceFragment;
import com.example.medianet.proschool.suresh.timetable.ViewTimeTableClassFragment;
import com.example.medianet.proschool.suresh.wordquote.WordQuoteBackgroundTask;
import com.example.medianet.proschool.teacher.teacherdashboard.feemodule.FeeModuleTeacherTabsFragment;
import com.example.medianet.proschool.teacher.teacherdashboard.teacherprofile.TeacherProfileAndAttFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED;

public class TeacherQuickDashboardClass extends AppCompatActivity implements
        QuickDashboardRecyclerViewAdapter.ItemListener, SchoolEventsBackTask.SchoolEvents,
        NoticeBoardBackTask.NoticeBoard, EmpAttMonthlyBackTask.AllEmpAttendanceByMonth, WordQuoteBackgroundTask.WordQuoteResponse,TextToSpeech.OnInitListener {

    private static String TAG = "AttendancePieChart";

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    private float[] yData = {25.3f, 10.6f, 66.6f};
    private String[] xData = {"Mitch", "Jessica", "Mohammad"};
    PieChart pieChart;

    //Assignment Text Below

    private List<DashboardAssignmentClass> asList = new ArrayList<>();
    private RecyclerView assignmentRecyclerView;
    private DashboardAssignmentAdapter mAdapter;

    //NoticeBoard Text Below

    private List<AdminDashboardNoticeBoardClass> noticeList = new ArrayList<>();
    private RecyclerView noticeRecyclerView;
    private AdminDashboardNoticeBoardAdapter mNoticeAdapter;

    //Event Date and Text Below

    private List<AdminDashboardEventClass> eventList = new ArrayList<>();
    private RecyclerView eventRecyclerView;
    private AdminDashboardEventAdapter mEventAdapter;

    //Curriculum Below

    private List<DashboardCurriculumClass> ccList = new ArrayList<>();
    private RecyclerView ccRecyclerView;
    private DashboardCurriculumAdapter mCcAdapter;
    Context mContext = this;
    String schoolId;
    String teacherId;
    FrameLayout frameLayout;

    private SlidingUpPanelLayout mLayout;

    TextView textQuote, textWord, wordMeaningText;
    ImageView imgSound;


    private TextToSpeech tts;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_dashboard);
        frameLayout = findViewById(R.id.frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.test_toolabr);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Dashboard");

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        teacherId = sharedPreferences.getString(Constants.empId, "");
        System.out.println("employee id:" + teacherId);
        new WordQuoteBackgroundTask(this, TeacherQuickDashboardClass.this).execute(schoolId);

        textQuote = (TextView) findViewById(R.id.textQuote);
        wordMeaningText = (TextView) findViewById(R.id.wordMeaning);
        textWord = (TextView) findViewById(R.id.textWord);
        imgSound = (ImageView) findViewById(R.id.imgSound);


        imgSound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                tts = new TextToSpeech(mContext, TeacherQuickDashboardClass.this);

                speakOut();
            }

        });


        Calendar c = Calendar.getInstance();
        // int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String currentMonth = String.valueOf(month);
        System.out.println("current month:" + month);

        new SchoolEventsBackTask(getApplicationContext(), this).execute(schoolId);
        new NoticeBoardBackTask(getApplicationContext(), this).execute(schoolId);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        new EmpAttMonthlyBackTask(getApplicationContext(), this).execute(currentMonth, teacherId, schoolId);

        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recycler_view);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        ArrayList<QuickDashboardDataModel> allItems = new ArrayList<>();

        allItems.add(new QuickDashboardDataModel("Student", R.drawable.academic, R.drawable.icon_seven));
        allItems.add(new QuickDashboardDataModel("Employee", R.drawable.employee, R.drawable.icon_employee));
        allItems.add(new QuickDashboardDataModel("Student Attendance", R.drawable.conduct_card, R.drawable.icon_one));
        // allItems.add(new QuickDashboardDataModel("Employee Attendance", R.drawable.employee_attendance, R.drawable.icon_employee_attendance));
        // allItems.add(new QuickDashboardDataModel("Performance", R.drawable.performance, R.drawable.icon_five));
        allItems.add(new QuickDashboardDataModel("Academics", R.drawable.attendance, R.drawable.icon_academics));
        allItems.add(new QuickDashboardDataModel("Assignments", R.drawable.examination, R.drawable.icon_eleven));
        allItems.add(new QuickDashboardDataModel("Examinations", R.drawable.quiz, R.drawable.icon_examinations));
        allItems.add(new QuickDashboardDataModel("Time Table", R.drawable.scheduler, R.drawable.icon_six));
        allItems.add(new QuickDashboardDataModel("Fee Collection", R.drawable.fee, R.drawable.icon_fee));
        // allItems.add(new QuickDashboardDataModel("HR", R.drawable.assignment, R.drawable.icon_hr));
        allItems.add(new QuickDashboardDataModel("Library", R.drawable.library, R.drawable.icon_ten));
        allItems.add(new QuickDashboardDataModel("Transport", R.drawable.bus_locator, R.drawable.icon_eight));


        QuickDashboardRecyclerViewAdapter adapter = new QuickDashboardRecyclerViewAdapter(this,
                allItems, this);

        //  LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        //AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        //recyclerView.setLayoutManager(layoutManager);


        /**
         Simple GridLayoutManager that spans two columns
         **/
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,
                false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);








          /*mTeachChart code*/
        pieChart = (PieChart) findViewById(R.id.attendance_pie);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setExtraOffsets(5, 5, 5, 5);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleRadius(55f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setRotationAngle(180f);
        pieChart.setCenterTextSize(10);

        // setDataTwo(3, 100);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend lTeach = pieChart.getLegend();
        lTeach.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lTeach.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lTeach.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lTeach.setDrawInside(false);
        lTeach.setXEntrySpace(7f);
        lTeach.setTextSize(12);
        lTeach.setYEntrySpace(0f);
        lTeach.setYOffset(5f);

        pieChart.setEntryLabelTextSize(12f);


        //PieChart Data Below


      /*  Log.d(TAG, "onCreate: starting to create chart");

        pieChart = (PieChart) findViewById(R.id.attendance_pie);

        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

      //  addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("y: ");
                String sales = e.toString().substring(pos1 + 3);

                for (int i = 0; i < yData.length; i++) {
                    if (yData[i] == Float.parseFloat(sales)) {
                        pos1 = i;
                        break;
                    }
                }
                String employee = xData[pos1];
                Toast.makeText(TeacherQuickDashboardClass.this, "Student: " + employee + "\n" + "Attendance: " + sales , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });*/

        //BarData below

        /*BarChart chart = (BarChart) findViewById(R.id.academics_bar);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 40f));
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entries, "");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh*/

        //Assignment RecyclerView

        /*assignmentRecyclerView = (RecyclerView) findViewById(R.id.assignments_recycler_view);

        mAdapter = new DashboardAssignmentAdapter(asList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext
                ());
        assignmentRecyclerView.setLayoutManager(mLayoutManager);
        assignmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        assignmentRecyclerView.setAdapter(mAdapter);

        prepareAssignmentTextData();*/

        //NoticeBoard RecyclerView

        noticeRecyclerView = (RecyclerView) findViewById(R.id.notice_board_recycler_view);

        mNoticeAdapter = new AdminDashboardNoticeBoardAdapter(noticeList);
        RecyclerView.LayoutManager mNoticeLayoutManager = new LinearLayoutManager(getApplicationContext
                ());
        noticeRecyclerView.setLayoutManager(mNoticeLayoutManager);
        noticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noticeRecyclerView.setAdapter(mNoticeAdapter);

        // prepareNoticeTextData();

        //EventBoard RecyclerView

        eventRecyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);

        mEventAdapter = new AdminDashboardEventAdapter(eventList);
        RecyclerView.LayoutManager mEventLayoutManager = new LinearLayoutManager(getApplicationContext
                ());
        eventRecyclerView.setLayoutManager(mEventLayoutManager);
        eventRecyclerView.setItemAnimator(new DefaultItemAnimator());
        eventRecyclerView.setAdapter(mEventAdapter);

        // prepareEventData();

        //Curriculum RecyclerView

        ccRecyclerView = (RecyclerView) findViewById(R.id.curriculum_recycler_view);

        mCcAdapter = new DashboardCurriculumAdapter(ccList);
        RecyclerView.LayoutManager mCcLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ccRecyclerView.setLayoutManager(mCcLayoutManager);
        ccRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ccRecyclerView.setAdapter(mCcAdapter);

        prepareCcData();
    }


    //PieChart Data below

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Student Attendance");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.MAGENTA);
        colors.add(Color.LTGRAY);
        colors.add(Color.CYAN);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();


    }

/*
    @Override
    public void onItemClick(QuickDashboardDataModel item) {

        Toast.makeText(getApplicationContext(), item.getText() + " is clicked", Toast
                .LENGTH_SHORT).show();

    }*/


    @Override
    public void onItemClick(QuickDashboardDataModel item) {
        if (item.text == "Student") {
            //    startActivity(new Intent(QuickDashboardClass.this, VehiclesActivity.class));
            mLayout.setPanelState(COLLAPSED);


            AllStudentDetails allStudentDetails = new AllStudentDetails();
            setFragment(allStudentDetails);

            //  fab.setVisibility(View.VISIBLE);


        }


        if (item.text == "Employee") {
            // startActivity(new Intent(QuickDashboardClass.this, FabMainActivity.class));
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            TeacherProfileAndAttFragment teacherProfileAndAttFragment = new TeacherProfileAndAttFragment();
            setFragment(teacherProfileAndAttFragment);
            //  fab.setVisibility(View.VISIBLE);


        }

        if (item.text == "Student Attendance") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            //StudentAttendenceFragment studentAttendenceFragment=new StudentAttendenceFragment();
            // setFragment(studentAttendenceFragment);
            //  fab.setVisibility(View.GONE);
            StudentAttendanceTabsFragment studentAttendanceTabsFragment = new StudentAttendanceTabsFragment();
            setFragment(studentAttendanceTabsFragment);

        }

       /* if (item.text == "Employee Attendance") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            EmployeeAttendanceTabsFragment employeeAttendanceTabsFragment = new EmployeeAttendanceTabsFragment();
            setFragment(employeeAttendanceTabsFragment);
          *//*  EmpAttendanceFragment empAttendanceFragment = new EmpAttendanceFragment();
            setFragment(empAttendanceFragment);*//*
            // AttendanceReportsByMonth attendanceReportsByMonth=new AttendanceReportsByMonth();
            //  setFragment(attendanceReportsByMonth);
            //  AttendanceReportByDayFragment attendanceReportByDayFragment=new AttendanceReportByDayFragment();
            // setFragment(attendanceReportByDayFragment);
            //  fab.setVisibility(View.GONE);

        }*/

        if (item.text == "Academics") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            AcademicsTabsFragment academicsTabsFragment = new AcademicsTabsFragment();
            setFragment(academicsTabsFragment);

        }

        if (item.text == "Assignments") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            AllAssignmentFragment allAssignmentFragment = new AllAssignmentFragment();
            setFragment(allAssignmentFragment);

        }
        if (item.text == "Examinations") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
         /*   ExamScheduleFragment examScheduleFragment = new ExamScheduleFragment();
            setFragment(examScheduleFragment);*/
            ExaminationTabsFragment examinationTabsFragment = new ExaminationTabsFragment();
            setFragment(examinationTabsFragment);

        }
        if (item.text == "Time Table") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            /*startActivity(new Intent(TeacherQuickDashboardClass.this, StackedBarActivityNegative.class));*/

            HrFragment hrFragment = new HrFragment();
            setFragment(hrFragment);

           /* ViewTimeTableClassFragment classWiseTimetableFragment = new ViewTimeTableClassFragment();
            setFragment(classWiseTimetableFragment);*/
           /* ExamPaperFragment examPaperFragment = new ExamPaperFragment();
            setFragment(examPaperFragment);*/

        }
        if (item.text == "Fee Collection") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        //    FeeModuleTabFragment feeModuleTabFragment = new FeeModuleTabFragment();
         //   setFragment(feeModuleTabFragment);
            FeeModuleTeacherTabsFragment feeModuleTeacherTabsFragment=new FeeModuleTeacherTabsFragment();
            setFragment(feeModuleTeacherTabsFragment);

     /*  ExamEvaluationFragment examEvaluationFragment=new ExamEvaluationFragment();
       setFragment(examEvaluationFragment);*/

        }

       /* if (item.text == "HR") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

          //  startActivity(new Intent(TeacherQuickDashboardClass.this, CustomPrograssActivity.class));
             HrFragment hrFragment = new HrFragment();
            setFragment(hrFragment);

     *//*  ExamEvaluationFragment examEvaluationFragment=new ExamEvaluationFragment();
       setFragment(examEvaluationFragment);*//*

        }*/

        if (item.text == "Library") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            BookListFragment bookListFragment = new BookListFragment();
            setFragment(bookListFragment);
            // startActivity(new Intent(QuickDashboardClass.this, com.example.medianet.proschool.suresh.swipetodelete.MainActivity.class));


        }

        if (item.text == "Transport") {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            // AllFeeCollectionFragment allFeeCollectionFragment=new AllFeeCollectionFragment();
            //setFragment(allFeeCollectionFragment);

            TransportFabsFragment transportFabsFragment = new TransportFabsFragment();
            setFragment(transportFabsFragment);
        }
      /*  if (item.text == " E Connect") {


        }*/

    }


    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)

                .commit();


        /*fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment, getString(R.string.title_dashboard))
                .addToBackStack(getString(R.string.title_dashboard))
                .commit();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else*/

        if (id == R.id.logout) {
            //Context mContext = getActivity() ;
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Log Out");
            builder.setMessage("Are you sure want to Log out?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final ProgressDialog progressDialog = ProgressDialog.show(mContext, "Please wait...", "logging out...", true);
                    Thread thread = new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                SharedPreferences sharedPreferences = mContext.getSharedPreferences(getString(R.string.appinfo), MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                Intent intent = new Intent(mContext, MainActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void prepareAssignmentTextData() {

        DashboardAssignmentClass assnText = new DashboardAssignmentClass("English", "Complete exercise one and exercise two");
        asList.add(assnText);

        assnText = new DashboardAssignmentClass("Maths", "Complete exercise one");
        asList.add(assnText);

        assnText = new DashboardAssignmentClass("Social", "Just do one and fourth exercise");
        asList.add(assnText);
        assnText = new DashboardAssignmentClass("Science", "Complete second lesson answers");
        asList.add(assnText);
        assnText = new DashboardAssignmentClass("Economics", "Answer Shastri's question");
        asList.add(assnText);
        assnText = new DashboardAssignmentClass("Commerce", "Just do what you want");
        asList.add(assnText);
        assnText = new DashboardAssignmentClass("Social", "Just do one and fourth exercise");
        asList.add(assnText);
        assnText = new DashboardAssignmentClass("History", "Tell me your history");
        asList.add(assnText);
        mAdapter.notifyDataSetChanged();
    }

    private void prepareNoticeTextData() {


        AdminDashboardNoticeBoardClass noticeText = new AdminDashboardNoticeBoardClass("Come and meet principal immediately");
        noticeList.add(noticeText);

        noticeText = new AdminDashboardNoticeBoardClass("Your child is not doing homework correctly, come an meet hindi teacher immediately");
        noticeList.add(noticeText);

        noticeText = new AdminDashboardNoticeBoardClass("Your second child is not doing homework correctly, come and meet me");
        noticeList.add(noticeText);

        noticeText = new AdminDashboardNoticeBoardClass("Examinations will start from tomorrow");
        noticeList.add(noticeText);

        mNoticeAdapter.notifyDataSetChanged();
    }

    private void prepareEventData() {

        AdminDashboardEventClass eventTotal = new AdminDashboardEventClass("10th, Nov 2017", "Annual Celebrations");
        eventList.add(eventTotal);

        eventTotal = new AdminDashboardEventClass("1st, Dec 2017", "World Celebration");
        eventList.add(eventTotal);

        eventTotal = new AdminDashboardEventClass("15st, Dec 2017", "China Celebration");
        eventList.add(eventTotal);

        eventTotal = new AdminDashboardEventClass("1st, Jan 2018", "My Celebration");
        eventList.add(eventTotal);

        mEventAdapter.notifyDataSetChanged();
    }

    private void prepareCcData() {

        DashboardCurriculumClass ccTotal = new DashboardCurriculumClass("9:00 - 10:00", "Maths");
        ccList.add(ccTotal);

        ccTotal = new DashboardCurriculumClass("10:00 - 11:00", "Social");
        ccList.add(ccTotal);

        ccTotal = new DashboardCurriculumClass("11:00 - 12:00", "English");
        ccList.add(ccTotal);

        ccTotal = new DashboardCurriculumClass("12:00 - 1:00", "--Lunch Break--");
        ccList.add(ccTotal);

        ccTotal = new DashboardCurriculumClass("1:00 - 2:00", "Science");
        ccList.add(ccTotal);

        ccTotal = new DashboardCurriculumClass("2:00 - 3:00", "Sanskrit");
        ccList.add(ccTotal);

        ccTotal = new DashboardCurriculumClass("3:00 - 4:00", "Sports");
        ccList.add(ccTotal);

        mCcAdapter.notifyDataSetChanged();
    }

    @Override
    public void schoolevents(String schoolevents) throws JSONException {
        if (schoolevents != null && !schoolevents.isEmpty()) {
            String stdName;
            eventList.clear();
            JSONObject jsonObject = new JSONObject(schoolevents);
            System.out.println("Schools" + jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("school_events");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("school events:" + jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                    AdminDashboardEventClass adminEventTotal = new AdminDashboardEventClass(markObject.getString("date"), markObject.getString("event_title"));
                    eventList.add(adminEventTotal);

                    count++;
                }
                mEventAdapter.notifyDataSetChanged();

            } else {
                // sliding_layout.setParallaxOffset(0);
                //  sliding_layout.setPanelHeight(0);
                //  recycler_view.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }


        }


    }

    @Override
    public void noticeBoard(String noticeBoard) throws JSONException {


        if (noticeBoard != null && !noticeBoard.isEmpty()) {
            String stdName;
            noticeList.clear();
            JSONObject jsonObject = new JSONObject(noticeBoard);
            System.out.println("Schools" + jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("messages");

            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("noticeboard data:" + jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                    AdminDashboardNoticeBoardClass adminNoticeText = new AdminDashboardNoticeBoardClass(markObject.getString("date"), markObject.getString("time"), markObject.getString("subject"), markObject.getString("messages"));
                    noticeList.add(adminNoticeText);


                    count++;
                }
                mNoticeAdapter.notifyDataSetChanged();

            } else {
                // sliding_layout.setParallaxOffset(0);
                //  sliding_layout.setPanelHeight(0);
                //  recycler_view.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public void allEmpAttendanceByMonth(String result) throws JSONException {


        if (result != null && !result.isEmpty()) {

            ArrayList<PieEntry> entriesThree = new ArrayList<>();
            entriesThree.clear();
            JSONObject jsonObject = new JSONObject(result);


            JSONArray jsonArray = jsonObject.getJSONArray("donutchart");

            System.out.println("all attendance:" + jsonArray);

            int taskCount = entriesThree.size();
            System.out.println("total entries value:" + taskCount);

            int countPresent1 = 0;
            int countAbsent1 = 0;
            int countOnLeave1 = 0;
            int countTotalJsonArray;


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject element = jsonArray.getJSONObject(i);
                String location = element.getString("status");
                if (location.equals("Present")) {
                    countPresent1++;
                } else if (location.equals("Absent")) {
                    countAbsent1++;
                } else if (location.equals("On Leave")) {
                    countOnLeave1++;
                }
            }
            countTotalJsonArray = jsonArray.length();


            System.out.println("total attendance json Objects:" + countTotalJsonArray);


            System.out.println("Present=" + countPresent1 + "Absent=" + countAbsent1 + "On Leave=" + countOnLeave1);


            Float totalPresents, totalAbsents, totalLeaves;
            Float totalAdminisations = Float.parseFloat(String.valueOf(countTotalJsonArray));
            Float presentCount = Float.parseFloat(String.valueOf(countPresent1));
            Float absentCount = Float.parseFloat(String.valueOf(countAbsent1));
            Float leaveCount = Float.parseFloat(String.valueOf(countOnLeave1));
            totalPresents = presentCount * 100 / totalAdminisations;
            totalAbsents = absentCount * 100 / totalAdminisations;
            totalLeaves = leaveCount * 100 / totalAdminisations;


            System.out.println("attandance report all  pie chart values" + totalPresents + " " + totalAbsents + " " + totalLeaves);


            entriesThree.add(new PieEntry(totalPresents, "Present" + "-" + countPresent1));
            entriesThree.add(new PieEntry(totalAbsents, "Leave" + "-" + countOnLeave1));
            entriesThree.add(new PieEntry(totalLeaves, "Absent" + "-" + countAbsent1));


            //   entriesThree.add(new PieEntry(85f, "Present"));
            //   entriesThree.add(new PieEntry(5f, "Leave"));
            //   entriesThree.add(new PieEntry(10f, "Absent"));

            PieDataSet dataSetTwo = new PieDataSet(entriesThree, "");

            dataSetTwo.setSliceSpace(3f);
            dataSetTwo.setSelectionShift(5f);
            dataSetTwo.setColors(ColorTemplate.PASTEL_COLORS);

            PieData data = new PieData(dataSetTwo);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            dataSetTwo.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            data.setValueTextColor(Color.BLACK);
            pieChart.setData(data);
            pieChart.invalidate();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {

            super.onBackPressed();
            return;

        }
        Intent intent = new Intent(TeacherQuickDashboardClass.this, TeacherQuickDashboardClass.class);

        intent.addCategory(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Press Once Again to Exit!",
                Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
        finish();

    }

    @Override
    public void onWordQuoteResponse(String response) throws JSONException {

        if (response != null && !response.isEmpty()) {
            //   listPaper.clear();
            JSONObject jsonObject = new JSONObject(response);
            System.out.println("Quote" + jsonObject);
            //   JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + subjectkey);


            JSONArray jsonArray = jsonObject.getJSONArray("Quotes");
            System.out.println("exam paper" + jsonArray);
            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject examObject = jsonArray.getJSONObject(count);
                    String quote = examObject.getString("quote") + " ~ " +  examObject.getString("quoteWritten");
                    String word = examObject.getString("word");
                    String wordMeaning = examObject.getString("wordWritten");
                    textQuote.setText(quote);
                    textWord.setText(word);
                    wordMeaningText.setText(wordMeaning);
                    count++;
                }
                //Count....


            }
        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                imgSound.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

        String text = textWord.getText().toString();
        Log.e("word check",text);

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        tts.setPitch(0.6f);

    }

}




