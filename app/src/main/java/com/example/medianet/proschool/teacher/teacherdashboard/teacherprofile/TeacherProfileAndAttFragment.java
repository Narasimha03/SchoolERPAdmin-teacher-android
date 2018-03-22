package com.example.medianet.proschool.teacher.teacherdashboard.teacherprofile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.medianet.proschool.AddStationFragment;
import com.example.medianet.proschool.AddVehicleFragment;
import com.example.medianet.proschool.BusRouteFragment;
import com.example.medianet.proschool.EmpAttendanceFragment;
import com.example.medianet.proschool.EmployeeDetailsFragment;
import com.example.medianet.proschool.ExamEvaluationFragment;
import com.example.medianet.proschool.ExamPaperFragment;
import com.example.medianet.proschool.ExamScheduleFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.RouteGeoLocationFragment;
import com.example.medianet.proschool.StudentAttendenceFragment;
import com.example.medianet.proschool.suresh.attendancemodule.AttendanceReportsByMonth;
import com.example.medianet.proschool.suresh.attendancemodule.daywisereport
        .AttendanceReportByDayFragment;
import com.example.medianet.proschool.suresh.checkboxs.DemoCheckBoxFragment;
import com.example.medianet.proschool.suresh.employeeattendancetabs.dailyadapterandbacktask
        .EmpAttendanceByDayFragment;
import com.example.medianet.proschool.suresh.employeeattendancetabs.monthlyadapterandbacktask
        .EmpAttendanceByMonthFragment;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileAttendanceOnlyGraphFragment;
import com.example.medianet.proschool.suresh.studentprofile.employeeprofile.EmployeeProfileAttendanceFragment;
import com.example.medianet.proschool.suresh.studentprofile.employeeprofile.EmployeeProfileAttendanceOnlyGraphFragment;
import com.example.medianet.proschool.suresh.studentprofile.employeeprofile.EmployeeProfileShowFragment;
import com.example.medianet.proschool.suresh.studentprofile.employeeprofile.EmployeeProfileTabsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 29-06-2017.
 */

public class TeacherProfileAndAttFragment extends Fragment{
    View empAttView;
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
        empAttView = inflater.inflate(R.layout.teacher_profile_module_tabs_layout, container,
                false);

        tabLayout = (TabLayout) empAttView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) empAttView.findViewById(R.id.viewPager);


        tabs = new ArrayList<String>();
        tabs.add("Employee Attendance");
        tabs.add("Monthly Reports");
        tabs.add("Day Reports");
        // tabs.add("Reports");




        TeacherProfileAndAttFragment.ViewPagerAdapter viewPagerAdapter = new TeacherProfileAndAttFragment.ViewPagerAdapter(getFragmentManager());
        EmployeeProfileShowFragment employeeProfileShowFragment = new EmployeeProfileShowFragment();
        viewPagerAdapter.addFrag(employeeProfileShowFragment, "Profile");
        EmployeeProfileAttendanceFragment employeeProfileAttendanceFragment = new EmployeeProfileAttendanceFragment();
        viewPagerAdapter.addFrag(employeeProfileAttendanceFragment, "Attendance");
        EmployeeProfileAttendanceOnlyGraphFragment employeeProfileAttendanceGraphFragment = new EmployeeProfileAttendanceOnlyGraphFragment();
        viewPagerAdapter.addFrag(employeeProfileAttendanceGraphFragment, "Monthly Attendance Graph");
        //AllE routeGeoLocationFragment=new RouteGeoLocationFragment();
        //  viewPagerAdapter.addFrag(routeGeoLocationFragment, "Reports");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        //viewPager.setOffscreenPageLimit(0);
        setRetainInstance(true);
        changeTabsFont(getContext(), tabLayout, Color.rgb(0, 131, 143));

        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

        return empAttView;
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
}
