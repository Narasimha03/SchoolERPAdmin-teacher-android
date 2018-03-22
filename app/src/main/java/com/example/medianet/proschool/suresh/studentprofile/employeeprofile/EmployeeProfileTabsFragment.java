package com.example.medianet.proschool.suresh.studentprofile.employeeprofile;
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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.medianet.proschool.R;
import java.util.ArrayList;
import java.util.List;

import com.example.medianet.proschool.suresh.employeeattendancetabs.EmployeeProfileGraphsFragment;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileAttendanceFragment;
import com.example.medianet.proschool.suresh.studentprofile.StudentProfileShowFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class EmployeeProfileTabsFragment extends AppCompatActivity
{
    ArrayList<String> tabs;
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.student_profile_tabs_layout);


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewPager);
        toolbar = (Toolbar) findViewById(R.id.test_toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Student Details");

        tabs = new ArrayList<>();
        tabs.add("Employee Profile");
        tabs.add("Attendance Report");

        EmployeeProfileTabsFragment.ViewPagerAdapter viewPagerAdapter = new EmployeeProfileTabsFragment.ViewPagerAdapter(getSupportFragmentManager());
        EmployeeProfileShowFragment employeeProfileShowFragment = new EmployeeProfileShowFragment();
        viewPagerAdapter.addFrag(employeeProfileShowFragment, "Profile");

        EmployeeProfileAttendanceFragment employeeProfileAttendanceFragment = new EmployeeProfileAttendanceFragment();
        viewPagerAdapter.addFrag(employeeProfileAttendanceFragment, "Day Attendance Report");

        EmployeeProfileGraphsFragment employeeProfileGraphsFragment = new EmployeeProfileGraphsFragment();
        viewPagerAdapter.addFrag(employeeProfileGraphsFragment, "Attendance Graph");

        EmployeeProfileAttendanceOnlyGraphFragment employeeProfileOnlyGraphsFragment = new EmployeeProfileAttendanceOnlyGraphFragment();
        viewPagerAdapter.addFrag(employeeProfileOnlyGraphsFragment, "Monthly Attendance Report");

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
        changeTabsFont(this, tabLayout, Color.rgb(0, 131, 143));

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

