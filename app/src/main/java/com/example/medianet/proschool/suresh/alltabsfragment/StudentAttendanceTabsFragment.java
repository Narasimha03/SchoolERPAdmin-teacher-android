package com.example.medianet.proschool.suresh.alltabsfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.medianet.proschool.AddStationFragment;
import com.example.medianet.proschool.AddVehicleFragment;
import com.example.medianet.proschool.BusRouteFragment;
import com.example.medianet.proschool.ExamEvaluationFragment;
import com.example.medianet.proschool.ExamPaperFragment;
import com.example.medianet.proschool.ExamScheduleFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.RouteGeoLocationFragment;
import com.example.medianet.proschool.StudentAttendenceFragment;
import com.example.medianet.proschool.checkattendance.StudentBulkAttFragment;
import com.example.medianet.proschool.suresh.MainActivity;
import com.example.medianet.proschool.suresh.QuickDashboardClass;
import com.example.medianet.proschool.suresh.attendancemodule.AttendanceReportsByMonth;
import com.example.medianet.proschool.suresh.attendancemodule.daywisereport
        .AttendanceReportByDayFragment;
import com.example.medianet.proschool.suresh.attendancemodule.studentgraphs.StudentAttendanceGraphReportByMonth;
import com.example.medianet.proschool.suresh.checkboxs.DemoCheckBoxFragment;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

/**
 * Created by JANI on 29-06-2017.
 */

public class StudentAttendanceTabsFragment extends Fragment {
    View timeTableView;
    ArrayList<String> tabs;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        timeTableView = inflater.inflate(R.layout.student_attendance_module_tabs_layout,
                container, false);


        tabLayout = (TabLayout) timeTableView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) timeTableView.findViewById(R.id.viewPager);

        tabs = new ArrayList<String>();
        tabs.add("Student Attendance");
        tabs.add("Monthly Reports");
        tabs.add("Day Reports");
        // tabs.add("Reports");

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
       /* DemoCheckBoxFragment demoCheckBoxFragment = new DemoCheckBoxFragment();
        viewPagerAdapter.addFrag(demoCheckBoxFragment, "Student Attendance");*/
        StudentBulkAttFragment studentBulkAttFragment=new StudentBulkAttFragment();
        viewPagerAdapter.addFrag(studentBulkAttFragment, "Student Attendance");

      /*  StudentAttendenceFragment studentAttendenceFragment = new StudentAttendenceFragment();
        viewPagerAdapter.addFrag(studentAttendenceFragment, "Student Attendance");*/
        AttendanceReportsByMonth attendanceReportsByMonth = new AttendanceReportsByMonth();
        viewPagerAdapter.addFrag(attendanceReportsByMonth, "Monthly Reports");
        AttendanceReportByDayFragment attendanceReportByDayFragment = new
                AttendanceReportByDayFragment();
        viewPagerAdapter.addFrag(attendanceReportByDayFragment, "Day Reports");

        StudentAttendanceGraphReportByMonth studentAttendanceGraphReportByMonth = new StudentAttendanceGraphReportByMonth();
        viewPagerAdapter.addFrag(studentAttendanceGraphReportByMonth, "Monthly Reports Graphs");
        //AllE routeGeoLocationFragment=new RouteGeoLocationFragment();
        //  viewPagerAdapter.addFrag(routeGeoLocationFragment, "Reports");



        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        changeTabsFont(getContext(), tabLayout, Color.rgb(0, 131, 143));


        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Student Attendance");



        return timeTableView;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public static void changeTabsFont(Context context, TabLayout tabLayout, int color) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        tabLayout.setBackgroundColor(color);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(context
                            .getAssets(), "fonts/Handlee-Regular.ttf"));
                }
            }
        }
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }*/

}
