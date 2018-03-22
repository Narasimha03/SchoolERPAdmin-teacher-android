package com.example.medianet.proschool.suresh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medianet.proschool.BookListFragment;
import com.example.medianet.proschool.ClassBackGroundTask;
import com.example.medianet.proschool.ClassWiseTimetableFragment;
import com.example.medianet.proschool.Constants;
import com.example.medianet.proschool.EmpAttendanceFragment;
import com.example.medianet.proschool.LoginActivity;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.StudentAttandenceModel;
import com.example.medianet.proschool.checkattendance.StudentBulkAttFragment;
import com.example.medianet.proschool.checkattendance.SubmitAttendanceStudentActivity;
import com.example.medianet.proschool.suresh.HR.HrFragment;
import com.example.medianet.proschool.suresh.activity.AllAssignmentFragment;
import com.example.medianet.proschool.suresh.activity.AllEmployeeDetails;
import com.example.medianet.proschool.suresh.activity.AllStudentDetails;
import com.example.medianet.proschool.suresh.admindashboard.AdminCurriculumAdapter;
import com.example.medianet.proschool.suresh.admindashboard.AdminCurriculumClass;
import com.example.medianet.proschool.suresh.admindashboard.AdminSectionsAdapter;
import com.example.medianet.proschool.suresh.admindashboard.AdminSectionsClass;
import com.example.medianet.proschool.suresh.admindashboard.AdminStudentAttendanceAdapter;
import com.example.medianet.proschool.suresh.admindashboard.AdminStudentAttendanceClass;
import com.example.medianet.proschool.suresh.alltabsfragment.AcademicsTabsFragment;
import com.example.medianet.proschool.suresh.alltabsfragment.ExaminationTabsFragment;
import com.example.medianet.proschool.suresh.alltabsfragment.StudentAttendanceTabsFragment;
import com.example.medianet.proschool.suresh.alltabsfragment.TransportFabsFragment;
import com.example.medianet.proschool.suresh.attendancemodule.AttendanceReportsByMonth;
import com.example.medianet.proschool.suresh.attendancemodule.AttendanceReportsByMonthAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.AllStudentMonthGraphAdapter;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.AllStudentMonthGraphAdapterForSingleStudent;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.StudentAttendanceGraphReportByMonth;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.StudentGraphModel;
import com.example.medianet.proschool.suresh.customprogress.CustomPrograssActivity;
import com.example.medianet.proschool.suresh.dashboard.NoticeBoardBackTask;
import com.example.medianet.proschool.suresh.dashboard.SchoolEventsBackTask;
import com.example.medianet.proschool.suresh.dashboard.StaffAttendanceBackTask;
import com.example.medianet.proschool.suresh.dashboard.StaffNonTeachingBackTask;
import com.example.medianet.proschool.suresh.dashboard.StaffTeachingBackTask;
import com.example.medianet.proschool.suresh.dashboard.StudentAttendanceBackTask;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.AdminDashboardEventAdapter;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.AdminDashboardEventClass;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.AdminDashboardNoticeBoardAdapter;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.AdminDashboardNoticeBoardClass;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.AllSectionsnDayAttendanceBackTask;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.ClassBackGroundTaskForSections;
import com.example.medianet.proschool.suresh.dashboard.noticeboardevents.ClassSectionsAdapter;
import com.example.medianet.proschool.suresh.employeeattendancetabs.EmployeeAttendanceTabsFragment;
import com.example.medianet.proschool.suresh.examination.BulkEvaluationsActivitiy;
import com.example.medianet.proschool.suresh.examination.Principal;
import com.example.medianet.proschool.suresh.feemodule.FeeModuleTabFragment;
import com.example.medianet.proschool.suresh.timetable.SessionTimingsBackGroundTask;
import com.example.medianet.proschool.suresh.timetable.TimetableTabsFragment;
import com.example.medianet.proschool.suresh.timetable.ViewTimeTableClassFragment;
import com.example.medianet.proschool.suresh.timetablenew.ClassAdapter;
import com.example.medianet.proschool.suresh.timetablenew.TimeTableActivity;
import com.example.medianet.proschool.suresh.timetablenew.TimeTableBackGroundTask;
import com.example.medianet.proschool.suresh.wordquote.WordQuoteBackgroundTask;
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
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.sothree.slidinguppanel.SlidingUpPanelLayout.*;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.*;

public class QuickDashboardClass extends AppCompatActivity implements QuickDashboardRecyclerViewAdapter.ItemListener, OnChartValueSelectedListener,
        StaffAttendanceBackTask.StaffAttendanceGraph, StaffTeachingBackTask.StaffTeachingGraph, StaffNonTeachingBackTask.StaffNonTeachingGraph, ClassBackGroundTask.OnClassResponse, SchoolEventsBackTask.SchoolEvents,
        NoticeBoardBackTask.NoticeBoard,ClassAdapter.OnItemClick, TimeTableBackGroundTask.OnTimeTableResponse,SessionTimingsBackGroundTask.OnTimeResponse,WordQuoteBackgroundTask.WordQuoteResponse,TextToSpeech.OnInitListener,
        AllSectionsnDayAttendanceBackTask.AllSectionsGraph,ClassBackGroundTaskForSections.ClassForSections,ClassSectionsAdapter.OnItemClickClassClick
{

    ArrayList<QuickDashboardDataModel> allItems = new ArrayList<>();
    FrameLayout frameLayout;
    private SlidingUpPanelLayout mLayout;
    boolean doubleBackToExitPressedOnce = false;
    boolean onBackPressed = true;
    FloatingActionButton fab, fab1;
    public static int navItemIndex = 0;
    Context mContext1 = this;

    //harish
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

    String admin = "administrative";
    String teching = "teaching";
    String nonTeching = "non-teaching";


    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    String firstClassId;

    String[] xDataL = {"Week 1", "Week 1", "Week 3", "Week 4"};


    //Student Attendance Horizontal Bar
    private HorizontalBarChart mChart;

    //Staff Attendance
    String person = null;
    String checkData = null;

    private PieChart mAdminChart;
    private PieChart mTeachChart;
    private PieChart mNonTeachChart;

    RecyclerView recycler_view;
    RecyclerView recyclerSectionsData;

    ArrayList<StudentAttandenceModel> listMarks = new ArrayList<StudentAttandenceModel>();
    AttendanceReportsByMonthAdapter feeCollectionAdapter;
    Context mContext = this;

    ArrayList<StudentGraphModel> listSections = new ArrayList<StudentGraphModel>();



    //Event Data

    private List<AdminDashboardEventClass> adminEventList = new ArrayList<>();
    private RecyclerView adminEventRecyclerView;
    private AdminDashboardEventAdapter mAdminEventAdapter;

    //NoticeBoard Text Below

    private List<AdminDashboardNoticeBoardClass> adminNoticeList = new ArrayList<>();
    private RecyclerView adminNoticeRecyclerView;
    private AdminDashboardNoticeBoardAdapter mAdminNoticeAdapter;
    String schoolId;

//curriculam

    LinkedHashMap<String, String> classMap = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> classMapSections = new LinkedHashMap<String, String>();

    RecyclerView classRecyclerView,classSectionRecycler;
    ClassAdapter classAdapter;
    ClassSectionsAdapter classSectionsAdapter;

    LinearLayoutManager linearLayoutManager;
    // Timings
    String timeResponse;
    LinearLayout timingsLayout;
    LinearLayout timeTableLayout;
    LinkedHashMap<String, String> listTime = new LinkedHashMap<String, String>();
    String sectionName;
    String date;

    AllStudentMonthGraphAdapterForSingleStudent allDayStudentMonthAdapter;

    //
    TextView textQuote, textWord, wordMeaningText;
    ImageView imgSound;


    private TextToSpeech tts;
    int dayOfWeek1;
    int dayOfWeek;
   // private Button btnSpeak;
   // private EditText txtText;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_dashboard_layout);


        classRecyclerView = (RecyclerView) findViewById(R.id.classRecyclerView);
        // Timings Layout
        timingsLayout = (LinearLayout) findViewById(R.id.timingsLayout);
        timeTableLayout = (LinearLayout) findViewById(R.id.timeTableLayout);
        textQuote = (TextView) findViewById(R.id.textQuote);
        textWord = (TextView) findViewById(R.id.textWord);
        wordMeaningText = (TextView) findViewById(R.id.wordMeaning);
        imgSound = (ImageView) findViewById(R.id.imgSound);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerSectionsData=(RecyclerView) findViewById(R.id.sections_recyclers);

        classSectionRecycler= (RecyclerView) findViewById(R.id.classSectionRecycle);

        imgSound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                tts = new TextToSpeech(mContext, QuickDashboardClass.this);

                speakOut();
            }

        });



         date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Calendar calendar = Calendar.getInstance();
      dayOfWeek= calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("day of Week" + dayOfWeek);
      //  String classId = classMap.keySet().toArray()[position1].toString();
       // new TimeTableBackGroundTask(mContext, QuickDashboardClass.this).execute(String.valueOf(dayOfWeek), classId);



        SharedPreferences sharedPreferences = mContext.getSharedPreferences("AppInfo", MODE_PRIVATE);
        schoolId = sharedPreferences.getString(Constants.schoolIdPref, "");
        Log.e("schoolId dashBaord", schoolId);
        new ClassBackGroundTask(this, QuickDashboardClass.this).execute(schoolId);
        new SessionTimingsBackGroundTask(this, QuickDashboardClass.this).execute(schoolId);
        new WordQuoteBackgroundTask(this, QuickDashboardClass.this).execute(schoolId);
        new ClassBackGroundTaskForSections(this, QuickDashboardClass.this).execute(schoolId);
String classData="SCH-9271-CL-1";



        //  new AllSectionsnDayAttendanceBackTask(this, QuickDashboardClass.this).execute(date);

        //Toast.makeText(this, sharedPreferences.getString(Constants.schoolIdPref, ""), Toast.LENGTH_LONG).show();
     /*   SharedPreferences myPrefs = this.getSharedPreferences(Constants.schoolIdInfo, MODE_PRIVATE);
        Toast.makeText(this, myPrefs.getString(Constants.schoolIdPref, ""), Toast.LENGTH_LONG).show();
*/
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.test_toolabr);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Dashboard");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView recyclerView;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_sliding);
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.fab_layout);

        allItems.add(new QuickDashboardDataModel("Student", R.drawable.academic, R.drawable.icon_seven));
        allItems.add(new QuickDashboardDataModel("Employee", R.drawable.employee, R.drawable.icon_employee));
        allItems.add(new QuickDashboardDataModel("Student Attendance", R.drawable.conduct_card, R.drawable.icon_one));
        allItems.add(new QuickDashboardDataModel("Employee Attendance", R.drawable.employee_attendance, R.drawable.icon_employee_attendance));
        // allItems.add(new QuickDashboardDataModel("Performance", R.drawable.performance, R.drawable.icon_five));
        allItems.add(new QuickDashboardDataModel("Academics", R.drawable.attendance, R.drawable.icon_academics));
        allItems.add(new QuickDashboardDataModel("Assignments", R.drawable.examination, R.drawable.icon_eleven));
        allItems.add(new QuickDashboardDataModel("Examinations", R.drawable.quiz, R.drawable.icon_examinations));
        allItems.add(new QuickDashboardDataModel("Time Table", R.drawable.scheduler, R.drawable.icon_six));
        allItems.add(new QuickDashboardDataModel("Fee Collection", R.drawable.fee, R.drawable.icon_fee));
        allItems.add(new QuickDashboardDataModel("HR", R.drawable.assignment, R.drawable.icon_hr));
        allItems.add(new QuickDashboardDataModel("Library", R.drawable.library, R.drawable.icon_ten));
        allItems.add(new QuickDashboardDataModel("Transport", R.drawable.bus_locator, R.drawable.icon_eight));

        QuickDashboardRecyclerViewAdapter adapter = new QuickDashboardRecyclerViewAdapter(this, allItems, this);

        //  RelativeLayout linearLayoutManager=new RelativeLayout(this);
        // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);


        //Admin Class Title RecyclerView
      /*  adminCurriculumRecyclerView = findViewById(R.id.admin_classes_recyclerview);


        mAdminCurAdapter = new AdminCurriculumAdapter(adminCurriculumClassList, listener);
        RecyclerView.LayoutManager mCcLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adminCurriculumRecyclerView.setLayoutManager(mCcLayoutManager);
        adminCurriculumRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //adminCurriculumRecyclerView.setAdapter(mAdminCurAdapter);
        adminCurriculumRecyclerView.setAdapter(new AdminCurriculumAdapter(adminCurriculumClassList, new AdminCurriculumAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(AdminCurriculumClass item) {
                Toast.makeText(getApplicationContext(), item.getAdminCurClasses(), Toast.LENGTH_SHORT).show();
            }

        }));*/


        //  prepareCcData();

        //Admin Sections RecyclerView
        //  adminSectionsRecyclerView = findViewById(R.id.admin_section_recycler);


      /*  mAdminSectionAdapter = new AdminSectionsAdapter(adminSectionsClassList);
        RecyclerView.LayoutManager mAdminSectionsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adminSectionsRecyclerView.setLayoutManager(mAdminSectionsLayoutManager);
        adminSectionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adminSectionsRecyclerView.setAdapter(mAdminSectionAdapter);*/
        /*adminSectionsRecyclerView.setAdapter(new AdminSectionsAdapter (adminSectionsClassList, new AdminCurriculumAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(AdminCurriculumClass item) {
                Toast.makeText(getApplicationContext(), item.getAdminCurClasses(), Toast.LENGTH_SHORT).show();
            }

        }));*/


        //prepareAdminSectionsData();

        //Admin Class Title RecyclerView for Student Attendance

      /*  adminStudentAttendanceRecyclerView = findViewById(R.id.admin_student_attendance_class_recyclerview);


        mAdminStuAttAdapter = new AdminStudentAttendanceAdapter(adminStuAttClassList, studentListener);
        RecyclerView.LayoutManager mStudentAttendanceLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adminStudentAttendanceRecyclerView.setLayoutManager(mStudentAttendanceLayoutManager);
        adminStudentAttendanceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //adminCurriculumRecyclerView.setAdapter(mAdminCurAdapter);
        adminStudentAttendanceRecyclerView.setAdapter(new AdminStudentAttendanceAdapter(adminStuAttClassList, new AdminStudentAttendanceAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(AdminStudentAttendanceClass item) {
                Toast.makeText(getApplicationContext(), item.getAdminStuAttClasses(), Toast.LENGTH_SHORT).show();
            }

        }));*/

        // prepareAdminStudentAttendanceClassData();

        //Student Attendance Horizontal Bar

   /*     mChart = findViewById(R.id.admin_student_attendance_bar_chart);
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
        //mChart.setVisibleXRangeMaximum(100);
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
        yValues.add(new BarEntry(5, new float[]{85, 7, 8}));
        yValues.add(new BarEntry(15, new float[]{87, 7, 6}));
        yValues.add(new BarEntry(25, new float[]{80, 10, 10}));
        yValues.add(new BarEntry(35, new float[]{79, 11, 10}));

        BarDataSet set = new BarDataSet(yValues, "");
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        BarData data = new BarData(set);
        set.setColors(Color.rgb(95, 182, 156), Color.rgb(209, 190, 168), Color.rgb(110, 127, 128));
        set.setStackLabels(new String[]{
                "Present", "Leave", "Absent"
        });
        data.setBarWidth(7f); // set custom bar width
        mChart.setData(data);
        mChart.setFitBars(true); // make the x-axis fit exactly all bars
        mChart.invalidate(); // refresh

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        mChart.setScaleXEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);

        xAxis.setGranularityEnabled(true);
        xAxis.setLabelRotationAngle(0f);
        xAxis.setDrawGridLines(false);

        final ArrayList<String> xEntrys = new ArrayList<>();
        xEntrys.add("Section A");
        xEntrys.add("");
        xEntrys.add("Section B");
        xEntrys.add("");
        xEntrys.add("Section C");
        xEntrys.add("");
        xEntrys.add("Section D");
        xEntrys.add("");
        xEntrys.add("Section E");
        xEntrys.add("");
    *//* final ArrayList<String> xEntrys = new ArrayList<>();
        for(int i = 1; i < xDataL.length; i++){
            xEntrys.add(xDataL[i]);
        }*//*

        // xEntrys.add("");

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

             *//* for (int i = 0; i <= xEntrys.size(); i++) {
                     person = (String) xEntrys.get(i);

                     System.out.println("person"+person);

                    }*//*

              *//*  for(int i = 1; i < xEntrys.size(); i++){
                    xEntrys.add(xEntrys.get(i));
                }*//*

              *//*  for (int i = 0; i < xEntrys.size(); i++) {
                    person = (String) xEntrys.get((i));
checkData=
                    System.out.println("person"+person);*//*


                return xEntrys.get((int) value % xEntrys.size());
            }

            //  String   s =xEntrys.get((int) value);
            //  System.out.println("person"+s);


*//*
               return String.valueOf(xEntrys).;
*//*


        });*/

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

        // setData(3, 100);

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
        String staffString = "";
      /*  String admin="administrative";
        String teching="teaching";
        String nonTeching="non teaching";*/
        new StaffAttendanceBackTask(this, this).execute(admin, date, schoolId);
        new StaffTeachingBackTask(this, this).execute(teching, date, schoolId);
        new StaffNonTeachingBackTask(this, this).execute(nonTeching, date, schoolId);
        //new StudentAttendanceBackTask(this,this).execute(date,)
       // new ClassBackGroundTask(this, this).execute(schoolId);
        new SchoolEventsBackTask(getApplicationContext(), this).execute(schoolId);
        new NoticeBoardBackTask(getApplicationContext(), this).execute(schoolId);

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

        // setDataTwo(3, 100);

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

        // setDataThree(3, 100);

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


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        // AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        //  recyclerView.setLayoutManager(layoutManager);

        mLayout.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //   fab.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                //  fab.setVisibility(View.VISIBLE);

            }
        });

        //Event Code

        adminEventRecyclerView = (RecyclerView) findViewById(R.id.admin_events_recycler_view);

        mAdminEventAdapter = new AdminDashboardEventAdapter(adminEventList);
        RecyclerView.LayoutManager mAdminEventLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adminEventRecyclerView.setLayoutManager(mAdminEventLayoutManager);
        adminEventRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adminEventRecyclerView.setAdapter(mAdminEventAdapter);

        //    prepareAdminEventData();

        //NoticeBoard RecyclerView

        adminNoticeRecyclerView = (RecyclerView) findViewById(R.id.notice_board_recycler_view);

        mAdminNoticeAdapter = new AdminDashboardNoticeBoardAdapter(adminNoticeList);
        RecyclerView.LayoutManager mAdminNoticeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adminNoticeRecyclerView.setLayoutManager(mAdminNoticeLayoutManager);
        adminNoticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adminNoticeRecyclerView.setAdapter(mAdminNoticeAdapter);


    }


    //harish

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

        entries.add(new PieEntry(98.0f, "Present"));
        entries.add(new PieEntry(1.0f, "Leave"));
        entries.add(new PieEntry(1.0f, "Absent"));

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


        //  prepareAdminNoticeData();
    }


    //Admin Notice Board Data

    private void prepareAdminNoticeData() {


        AdminDashboardNoticeBoardClass adminNoticeText = new AdminDashboardNoticeBoardClass("10th Nov 2017", "12:12", "Subject", "Come and meet principal immediately");
        adminNoticeList.add(adminNoticeText);

        adminNoticeList.add(new AdminDashboardNoticeBoardClass("10th Nov 2017", "12:12", "Subject", "Come and meet principal immediately"));
        adminNoticeList.add(new AdminDashboardNoticeBoardClass("10th Nov 2017", "12:12", "Subject", "Come and meet principal immediately"));
        adminNoticeList.add(new AdminDashboardNoticeBoardClass("10th Nov 2017", "12:12", "Subject", "Come and meet principal immediately"));
        adminNoticeList.add(new AdminDashboardNoticeBoardClass("10th Nov 2017", "12:12", "Subject", "Come and meet principal immediately"));

        mAdminNoticeAdapter.notifyDataSetChanged();
    }


    /**
     * Simple GridLayoutManager that spans two columns
     **/
    //  GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
    //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
    // linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    //  recyclerView.setLayoutManager(manager);
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
            mLayout.setPanelState(PanelState.COLLAPSED);
            AllEmployeeDetails allEmployeeDetails = new AllEmployeeDetails();
            setFragment(allEmployeeDetails);
            //  fab.setVisibility(View.VISIBLE);


        }

        if (item.text == "Student Attendance") {
            mLayout.setPanelState(PanelState.COLLAPSED);
            //StudentAttendenceFragment studentAttendenceFragment=new StudentAttendenceFragment();
            // setFragment(studentAttendenceFragment);
            //  fab.setVisibility(View.GONE);
            StudentAttendanceTabsFragment studentAttendanceTabsFragment = new StudentAttendanceTabsFragment();
            setFragment(studentAttendanceTabsFragment);

        }

        if (item.text == "Employee Attendance") {
            mLayout.setPanelState(PanelState.COLLAPSED);
            EmployeeAttendanceTabsFragment employeeAttendanceTabsFragment = new EmployeeAttendanceTabsFragment();
            setFragment(employeeAttendanceTabsFragment);
          /*  EmpAttendanceFragment empAttendanceFragment = new EmpAttendanceFragment();
            setFragment(empAttendanceFragment);*/
            // AttendanceReportsByMonth attendanceReportsByMonth=new AttendanceReportsByMonth();
            //  setFragment(attendanceReportsByMonth);
            //  AttendanceReportByDayFragment attendanceReportByDayFragment=new AttendanceReportByDayFragment();
            // setFragment(attendanceReportByDayFragment);
            //  fab.setVisibility(View.GONE);

        }

        if (item.text == "Academics") {
            mLayout.setPanelState(PanelState.COLLAPSED);
            AcademicsTabsFragment academicsTabsFragment = new AcademicsTabsFragment();
            setFragment(academicsTabsFragment);

        }

        if (item.text == "Assignments") {
            mLayout.setPanelState(PanelState.COLLAPSED);
            AllAssignmentFragment allAssignmentFragment = new AllAssignmentFragment();
            setFragment(allAssignmentFragment);

        }
        if (item.text == "Examinations") {
            mLayout.setPanelState(PanelState.COLLAPSED);
         /*   ExamScheduleFragment examScheduleFragment = new ExamScheduleFragment();
            setFragment(examScheduleFragment);*/
            ExaminationTabsFragment examinationTabsFragment = new ExaminationTabsFragment();
            setFragment(examinationTabsFragment);

        }
        if (item.text == "Time Table") {
            mLayout.setPanelState(PanelState.COLLAPSED);
            TimetableTabsFragment classWiseTimetableFragment = new TimetableTabsFragment();
            setFragment(classWiseTimetableFragment);
           /* ExamPaperFragment examPaperFragment = new ExamPaperFragment();
            setFragment(examPaperFragment);*/

        }
        if (item.text == "Fee Collection") {
            mLayout.setPanelState(PanelState.COLLAPSED);
            FeeModuleTabFragment feeModuleTabFragment = new FeeModuleTabFragment();
            setFragment(feeModuleTabFragment);

     /*  ExamEvaluationFragment examEvaluationFragment=new ExamEvaluationFragment();
       setFragment(examEvaluationFragment);*/

        }

        if (item.text == "HR") {
            mLayout.setPanelState(PanelState.COLLAPSED);

        /*    HrFragment hrFragment = new HrFragment();
            setFragment(hrFragment);*/
            startActivity(new Intent(QuickDashboardClass.this, BulkEvaluationsActivitiy.class));


            // startActivity(new Intent(QuickDashboardClass.this, SubmitAttendanceStudentActivity.class));
           // startActivity(new Intent(QuickDashboardClass.this, TimeTableActivity.class));
         //   StudentAttendanceGraphReportByMonth studentAttendanceGraphReportByMonth = new StudentAttendanceGraphReportByMonth();
          //    setFragment(studentAttendanceGraphReportByMonth);

            //startActivity(new Intent(QuickDashboardClass.this, TimeTableActivity.class));

            //StudentBulkAttFragment studentBulkAttFragment=new StudentBulkAttFragment();
            // setFragment(studentBulkAttFragment);
            //startActivity(new Intent(QuickDashboardClass.this, CustomPrograssActivity.class));
            //  HrFragment hrFragment = new HrFragment();
            //  setFragment(hrFragment);

     /*  ExamEvaluationFragment examEvaluationFragment=new ExamEvaluationFragment();
       setFragment(examEvaluationFragment);*/

        }

        if (item.text == "Library") {
            mLayout.setPanelState(PanelState.COLLAPSED);
            BookListFragment bookListFragment = new BookListFragment();
            setFragment(bookListFragment);
            // startActivity(new Intent(QuickDashboardClass.this, com.example.medianet.proschool.suresh.swipetodelete.MainActivity.class));

        }

        if (item.text == "Transport") {
            mLayout.setPanelState(PanelState.COLLAPSED);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

   /* @Override
    public void onRestart()
    {
        super.onRestart();
      //  finish();
      //  startActivity(getIntent());
    }*/

    @Override
    public void onBackPressed() {

        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {

            super.onBackPressed();
            return;


        } /*else {

            *//*Intent intent = new Intent(QuickDashboardClass.this, QuickDashboardClass.class);

            intent.addCategory(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*//*

        }*/
        Intent intent = new Intent(QuickDashboardClass.this, QuickDashboardClass.class);

        intent.addCategory(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Press Once Again to Exit!",
                Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
        finish();




        /*if (getFragmentManager().getBackStackEntryCount() > 0) {

            getFragmentManager().popBackStack();
          //  Intent intent = new Intent(QuickDashboardClass.this, QuickDashboardClass.class);

           Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            //System.out.println("back button");
        } else {
      //  finish();
            super.onBackPressed();*/

        // Intent intent = new Intent(Intent.ACTION_MAIN);
            /*Intent intent = new Intent(QuickDashboardClass.this, QuickDashboardClass.class);

        intent.addCategory(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
       finish();*/

     /*else {

            System.exit(0);
            finish();*/


            /*//this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit SMS", Toast.LENGTH_LONG).show();

            Intent intentTwo = new Intent(Intent.ACTION_MAIN);
            intentTwo.addCategory(Intent.CATEGORY_HOME);
            intentTwo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentTwo);
            finish();
            System.exit(0);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
            return;*/


        //System.out.println("back button2");

    }


     /*   if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else if(getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        }
        else {
            getFragmentManager().popBackStack();
        }*/


    // if(s == null || !selectedFragment.onBackPressed()) {
    // Selected fragment did not consume the back press event.
      /*  if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }*/


    /*@Override
    public void onBackPressed() {


        try {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                //finish();// finish activity if you are at home screen
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();

                    Intent intent = new Intent(QuickDashboardClass.this, QuickDashboardClass.class);

                    //Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                }

                *//**
     * Displaying toast message when pressing back button
     *//*
                else {
                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "Press again to exit SMS", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    System.exit(0);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                    return;
                } *//*else {
                    getSupportFragmentManager().popBackStack();// will pop previous fragment
                    return;
                }*//*
            }
        } catch (Exception e) {
            super.onBackPressed();
        }

    }*/



/*

    @Override
    public void onBackPressed() {

            */
/*super.onBackPressed();
            this.finish();*//*

            try {
                if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                    //finish();// finish activity if you are at home screen
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                    */

    /**
     * Displaying toast message when pressing back button
     *//*

                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "Press again to exit SMS", Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                    return;
                } else {
                    getSupportFragmentManager().popBackStack();// will pop previous fragment
                    return;
                }
            } catch (Exception e) {
                super.onBackPressed();
            }

    }
*/
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


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void staffAttendanceGraph(String result) throws JSONException {
        if (result != null && !result.isEmpty()) {


            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.clear();
            JSONObject jsonObject = new JSONObject(result);


            JSONArray jsonArray = jsonObject.getJSONArray("employeeAttendence");

            System.out.println("all attendance:" + jsonArray);

            int taskCount = entries.size();
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


            entries.add(new PieEntry(totalPresents, "Present" + "-" + countPresent1));
            entries.add(new PieEntry(totalAbsents, "Absent" + "-" + countAbsent1));
            entries.add(new PieEntry(totalLeaves, "Leave" + "-" + countOnLeave1));

            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.PASTEL_COLORS);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            data.setValueTextColor(Color.BLACK);
            mAdminChart.setRotationEnabled(true);
            //data.setValueTypeface(mTfLight);
            mAdminChart.setData(data);
            mAdminChart.invalidate();

        }

           /* else if (teching!=null)
            {
                new StaffAttendanceBackTask(this, this).execute(teching, date);



                ArrayList<PieEntry> entriesTwo = new ArrayList<PieEntry>();


                entriesTwo.add(new PieEntry(totalPresents, "Present" + "-" + countPresent1));
                entriesTwo.add(new PieEntry(totalAbsents, "Leave" + "-" + countOnLeave1));
                entriesTwo.add(new PieEntry(totalLeaves, "Absent" + "-" + countAbsent1));


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
            else if (nonTeching!=null);
            new StaffAttendanceBackTask(this, this).execute(nonTeching, date);


            ArrayList<PieEntry> entriesThree = new ArrayList<PieEntry>();

            entriesThree.add(new PieEntry(totalPresents, "Present" + "-" + countPresent1));
            entriesThree.add(new PieEntry(totalAbsents, "Leave" + "-" + countOnLeave1));
            entriesThree.add(new PieEntry(totalLeaves, "Absent" + "-" + countAbsent1));


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
*/


             /*   //Count....
                String taskCount = "Attendance Records (" + String.valueOf(listMarks.size()) + ")";
                textExamCount.setText(taskCount);
                //
                feeCollectionAdapter = new AllAttendanceReportByDayAdapter(getActivity(), listMarks);
                recycler_view.setAdapter(feeCollectionAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(linearLayoutManager);
                feeCollectionAdapter.notifyDataSetChanged();
                //
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(300);
            } else {
                sliding_layout.setParallaxOffset(0);
                sliding_layout.setPanelHeight(0);
                recycler_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Records Found....!", Toast.LENGTH_LONG).show();


}
    }*/
    }

    @Override
    public void staffNoneachingGraph(String nonTeaching) throws JSONException {

        if (nonTeaching != null && !nonTeaching.isEmpty()) {

            ArrayList<PieEntry> entriesThree = new ArrayList<>();
            entriesThree.clear();
            JSONObject jsonObject = new JSONObject(nonTeaching);



            JSONArray jsonArray = jsonObject.getJSONArray("employeeAttendence");

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
            entriesThree.add(new PieEntry(totalAbsents, "Absent" + "-" + countAbsent1));
            entriesThree.add(new PieEntry(totalLeaves, "Leave" + "-" + countOnLeave1));


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
            mNonTeachChart.setRotationEnabled(true);
            mNonTeachChart.setData(data);
            mNonTeachChart.invalidate();
        }


    }

    @Override
    public void staffTeachingGraph(String teaching) throws JSONException {
        if (teaching != null && !teaching.isEmpty()) {


            ArrayList<PieEntry> entriesTwo = new ArrayList<>();
            entriesTwo.clear();
            JSONObject jsonObject = new JSONObject(teaching);


            JSONArray jsonArray = jsonObject.getJSONArray("employeeAttendence");

            System.out.println("all attendance:" + jsonArray);

            int taskCount = entriesTwo.size();
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

            entriesTwo.add(new PieEntry(totalPresents, "Present" + "-" + countPresent1));
            entriesTwo.add(new PieEntry(totalAbsents, "Absent" + "-" + countAbsent1));
            entriesTwo.add(new PieEntry(totalLeaves, "Leave" + "-" + countOnLeave1));

            PieDataSet dataSetTwo = new PieDataSet(entriesTwo, "");

            dataSetTwo.setSliceSpace(3f);
            dataSetTwo.setSelectionShift(5f);
            dataSetTwo.setColors(ColorTemplate.PASTEL_COLORS);

            PieData data = new PieData(dataSetTwo);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            dataSetTwo.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            data.setValueTextColor(Color.BLACK);
            mTeachChart.setRotationEnabled(true);
            mTeachChart.setData(data);
            mTeachChart.invalidate();


        }

    }


    @Override
    public void schoolevents(String schoolevents) throws JSONException {
        if (schoolevents != null && !schoolevents.isEmpty()) {
            String stdName;
            adminEventList.clear();
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
                    adminEventList.add(adminEventTotal);

                    count++;
                }
                mAdminEventAdapter.notifyDataSetChanged();

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
            adminNoticeList.clear();
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
                    adminNoticeList.add(adminNoticeText);


                    count++;
                }
                mAdminNoticeAdapter.notifyDataSetChanged();

            } else {
                // sliding_layout.setParallaxOffset(0);
                //  sliding_layout.setPanelHeight(0);
                //  recycler_view.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "No Records Found....!", Toast.LENGTH_LONG).show();
            }


        }
    }


    @Override
    public void OnClick(int position) {
       // timeTableLayout.removeAllViews();
        String classId = classMap.keySet().toArray()[position].toString();
        System.out.println("classId" + classId);
        Calendar calendar = Calendar.getInstance();
        dayOfWeek1= calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("day of Week" + dayOfWeek1);
        new TimeTableBackGroundTask(mContext, QuickDashboardClass.this).execute(String.valueOf(dayOfWeek1), classId);
    }

    @Override
    public void onTimeTableResponse(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            Log.e("Resp", response);
            timeTableLayout.removeAllViews();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("timetable");

            for (int i = 0; i < jsonArray.length(); i++) {
                // Linear Layout
                LinearLayout linearLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(15, 0, 15, 0);
                linearLayout.setBackgroundResource(R.color.white);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(layoutParams);
                // JSONObject
                JSONObject sectionObject = jsonArray.getJSONObject(i);
                // Section
                sectionName = "Section " + sectionObject.getString("sectionName");
                TextView sectionTV = new TextView(mContext);
                sectionTV.setText(sectionName);
                sectionTV.setTextSize(18);
                sectionTV.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Handlee-Regular.ttf"));
                sectionTV.setGravity(1);
                sectionTV.setTextColor(ContextCompat.getColor(mContext, R.color.sectionColor));
                sectionTV.setPadding(20, 0, 20, 0);
                //sectionTV.setTypeface(Typeface.DEFAULT_BOLD);
                // Adding section name to linear layout
                linearLayout.addView(sectionTV);
                // TimeTable
                JSONArray timeTableArray = sectionObject.getJSONArray("timetableData");
                for (int j = 0; j < timeTableArray.length(); j++) {
                    JSONObject timeTableObject = timeTableArray.getJSONObject(j);
                    // Subject
                    TextView subjectTV = new TextView(mContext);
                    subjectTV.setText(timeTableObject.getString("subject"));
                    subjectTV.setPadding(10, 20, 10, 20);
                    subjectTV.setTextSize(15);
                    subjectTV.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Handlee-Regular.ttf"));
                    subjectTV.setTextColor(ContextCompat.getColor(mContext, R.color.subjectColor));
                    // Adding subject name to linear layout
                    linearLayout.addView(subjectTV);
                    // View
                    View view = new View(mContext);
                    LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                    view.setLayoutParams(viewParams);
                    view.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
                    // Adding view to linear layout
                    linearLayout.addView(view);
                }
                timeTableLayout.addView(linearLayout);
            }
        }
    }

    @Override
    public void onClassResponse(String classResponse) throws JSONException {

        // Classes
        //  classResponse = getIntent().getExtras().getString("classes");
        if (classResponse != null && !classResponse.isEmpty()) {
            try {
                JSONObject classObject = new JSONObject(classResponse);
                JSONArray classArray = classObject.getJSONArray("school_classes");

                JSONObject cObject1 = classArray.getJSONObject(0);
              String  firstClassData=    cObject1.getString("class_id");
                new TimeTableBackGroundTask(mContext, QuickDashboardClass.this).execute(String.valueOf(dayOfWeek), firstClassData);


                int count = 0;
                while (count < classArray.length()) {
                    JSONObject cObject = classArray.getJSONObject(count);
                    classMap.put(cObject.getString("class_id"), cObject.getString("name"));
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        classAdapter = new ClassAdapter(mContext, classMap, QuickDashboardClass.this);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        classRecyclerView.setLayoutManager(linearLayoutManager);
        classRecyclerView.setAdapter(classAdapter);
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTimeResponse(String response) throws JSONException {

        // Timings
        //   timeResponse = getIntent().getExtras().getString("timings");
        if (response != null && !response.isEmpty()) {
            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("session_timings");
                Log.e("session timings", String.valueOf(jsonArray));
                if (jsonArray.length() > 0) {
                    // TimeTable
                    TextView heading = new TextView(mContext);
                    heading.setText("");
                    heading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Handlee-Regular.ttf"));
                    heading.setTextSize(18);
                    //heading.setTypeface(Typeface.DEFAULT_BOLD);
                    timingsLayout.addView(heading);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject timeObject = jsonArray.getJSONObject(i);
                        // Timings
                        TextView timeView = new TextView(mContext);
                        String text = timeObject.getString("start_time") + " - " + timeObject.getString("end_time");
                        timeView.setText(text);
                        timeView.setPadding(10, 20, 10, 20);
                        timeView.setTextSize(15);
                        timeView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Handlee-Regular.ttf"));
                        timeView.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
                        timingsLayout.addView(timeView);
                        listTime.put(timeObject.getString("start_time"), timeObject.getString("end_time"));
                        // View
                        View view = new View(mContext);
                        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                        view.setLayoutParams(viewParams);
                        view.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
                        timingsLayout.addView(view);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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


    @Override
    public void allSectionsGraph(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {
            String sectionId = "",sectionName = "",totalCount = "",sPresent = "",sAbsent = "",sOnleave = "", employeeId = "";
            String imageName="";
            String imageurlData="";

            listSections.clear();
            JSONObject jsonObject = new JSONObject(response);
            System.out.println("section report"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("students");


            //JSONArray jsonArray = jsonObject.getJSONArray(examScheduleKey + "-" + sectionKey);
            System.out.println("all attendance:"+jsonArray);


            if (jsonArray.length() > 0) {
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject markObject = jsonArray.getJSONObject(count);

                 /*   stdName = markObject.getString("first_name") + markObject.getString("last_name");
                    date=markObject.getString("date");
                    String firstTen=date.substring(0,10);*/

                    sectionId = markObject.getString("sectionId");
                    sectionName = "Section " +markObject.getString("sectionName");


               /*     totalCount=markObject.getString;("totalCount");
                    totalPresent=markObject.getString("totalPresent");
                    totalAbsent=markObject.getString("totaAbsent");
                    totalLeave=markObject.getString("totalOnLeave");*/


                    //Log.e("student Count att",totalCount+" "+totalPresent);


                    if (!markObject.isNull("attendance")) {
                        JSONObject parentArray = markObject.getJSONObject("attendance");
                        //  int p = 0;
                        //  while (p < parentArray.length()) {
                       // JSONObject parentObject = parentArray.getJSONObject(p);
                        sPresent = parentArray.getString("present");
                        sAbsent = parentArray.getString("absent");
                        sOnleave = parentArray.getString("onLeave");

                        // new StudentProfileBackTask(getActivity(), StudentAttendanceGraphReportByMonth.this).execute(studentId);


                        StudentGraphModel studentGraphModel = new StudentGraphModel(sectionId, employeeId,sectionName, totalCount, totalCount, totalCount, totalCount, sPresent, sAbsent, sOnleave, totalCount, totalCount, totalCount, totalCount);
                        listSections.add(studentGraphModel);
                        //   p++;
                        // }


                    }
                    count++;
                }






                 /*   String a;
                    String b;
                    String c;
                    int total;
                    String totalValue;
                    a = markObject.getString("fee_amount");
                    b = markObject.getString("fine");
                    c = markObject.getString("discount");
                    int amount=Integer.parseInt(a);
                    int fine=Integer.parseInt(b);
                    int discount=Integer.parseInt(c);
                    total=(amount+fine)-discount;
                    totalValue= String.valueOf(total);
                    System.out.println("total"+totalValue);*/
                    //paper_result_id
                  /*  FeeCollection evalMarks = new FeeCollection(markObject.getString("_id"), markObject.getString("exam_paper_id"),
                            selectedPaper, selectedStudent, markObject.getString("marks"),
                            markObject.getString("percentage"), markObject.getString("conduct"), markObject.getString("paper_name"),
                            markObject.getString("first_name"), markObject.getString("examschedule_name"), selectedExamSchedule, selectedSubject);*/



                //Count....
               // String taskCount = "Attendance Records (" + String.valueOf(listMarks.size()) + ")";
               // textExamCount.setText(taskCount);
                //
              //  recycler_view_sections.setVisibility(View.VISIBLE);

                allDayStudentMonthAdapter = new AllStudentMonthGraphAdapterForSingleStudent(mContext, listSections);
                recyclerSectionsData.setAdapter(allDayStudentMonthAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                recyclerSectionsData.setLayoutManager(linearLayoutManager);
                allDayStudentMonthAdapter.notifyDataSetChanged();
                recyclerSectionsData.destroyDrawingCache();
                //
               // sliding_layout.setParallaxOffset(0);
              //  sliding_layout.setPanelHeight(120);
            } else {
               // sliding_layout.setParallaxOffset(0);
              //  sliding_layout.setPanelHeight(0);
                recyclerSectionsData.setVisibility(View.GONE);
                Toast.makeText(mContext, "No Records Found....!", Toast.LENGTH_LONG).show();
            }
        }




    }

    @Override
    public void onClassSections(String response) throws JSONException {

        // Classes
        //  classResponse = getIntent().getExtras().getString("classes");
        if (response != null && !response.isEmpty()) {
            try {
                JSONObject classObject = new JSONObject(response);
                JSONArray classArray = classObject.getJSONArray("school_classes");
                Log.e("sect classes", String.valueOf(classArray));

                JSONObject cObject1 = classArray.getJSONObject(0);
                firstClassId=    cObject1.getString("class_id");
                new AllSectionsnDayAttendanceBackTask(mContext, QuickDashboardClass.this).execute(date, firstClassId);

                Log.e("first Class",firstClassId);
                int count = 0;
                while (count < classArray.length()) {
                    JSONObject cObject = classArray.getJSONObject(count);



                    classMapSections.put(cObject.getString("class_id"), cObject.getString("name"));
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        classSectionsAdapter = new ClassSectionsAdapter(mContext, classMapSections, QuickDashboardClass.this);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        classSectionRecycler.setLayoutManager(linearLayoutManager);
        classSectionRecycler.setAdapter(classSectionsAdapter);
        classSectionsAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClickClassForSections(int position) {

     //   timeTableLayout.removeAllViews();
        String classId = classMapSections.keySet().toArray()[position].toString();
        Log.e("class sections",classId);
       /* System.out.println("classId" + classId);
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek1 = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("day of Week" + dayOfWeek1);*/
        new AllSectionsnDayAttendanceBackTask(mContext, QuickDashboardClass.this).execute(date, classId);


    }
}



