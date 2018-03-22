package com.example.medianet.proschool.suresh.studentprofile;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.academics.SubjectsShowFragment;
import com.example.medianet.proschool.suresh.activity.AllChapterFragment;
import com.example.medianet.proschool.suresh.activity.AllStudentDetails;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class StudentProfileTabsFragment extends AppCompatActivity implements AllStudentDetails.SendMessage {
    View timeTableView;
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


        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        toolbar = (Toolbar) findViewById(R.id.test_toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Student Details");

        tabs = new ArrayList<String>();
        tabs.add("Profile");
        tabs.add("Attendance");
        tabs.add("Academic");

        //   tabs.add("Add Chapter");



        StudentProfileTabsFragment.ViewPagerAdapter viewPagerAdapter = new StudentProfileTabsFragment.ViewPagerAdapter(getSupportFragmentManager());

        StudentProfileShowFragment studentProfileShowFragment = new StudentProfileShowFragment();
        viewPagerAdapter.addFrag(studentProfileShowFragment, "Profile");

        StudentProfileAttendanceFragment studentProfileAttendanceFragment = new StudentProfileAttendanceFragment();
        viewPagerAdapter.addFrag(studentProfileAttendanceFragment, "Attendance");
        StudentProfileGraphsFragment studentProfileGraphsFragment = new StudentProfileGraphsFragment();
        viewPagerAdapter.addFrag(studentProfileGraphsFragment, "Attendance Graphs");
        StudentProfileAttendanceOnlyGraphFragment studentProfileOnlyGraphsFragment = new StudentProfileAttendanceOnlyGraphFragment();
        viewPagerAdapter.addFrag(studentProfileOnlyGraphsFragment, "Only Graphs");
        //StudentProfileAcadamicFragment studentProfileAcadamicFragment = new StudentProfileAcadamicFragment();
        //viewPagerAdapter.addFrag(studentProfileAcadamicFragment, "Academic Report");
        //   AddChapterFragment addChapterFragment=new AddChapterFragment();
        // viewPagerAdapter.addFrag(addChapterFragment, "Add Chapter");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        changeTabsFont(this, tabLayout, Color.rgb(0, 131, 143));

    }
/*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        timeTableView = inflater.inflate(R.layout.acadamecis_module_tabs_layout, container, false);
        tabLayout = (TabLayout) timeTableView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) timeTableView.findViewById(R.id.viewPager);

        tabs = new ArrayList<String>();
        tabs.add("Student Profile");
        tabs.add("Attendance Report");
        tabs.add("Acadamic Report");

        //   tabs.add("Add Chapter");



    StudentProfileTabsFragment.ViewPagerAdapter viewPagerAdapter = new StudentProfileTabsFragment.ViewPagerAdapter(getFragmentManager());

        StudentProfileShowFragment studentProfileShowFragment = new StudentProfileShowFragment();
      //  viewPagerAdapter.addFrag(studentProfileShowFragment, "Student Profile");
        StudentProfileAttendanceFragment studentProfileAttendanceFragment = new StudentProfileAttendanceFragment();
        viewPagerAdapter.addFrag(studentProfileAttendanceFragment, "Attendance Report");

        StudentProfileAcadamicFragment studentProfileAcadamicFragment = new StudentProfileAcadamicFragment();
       // viewPagerAdapter.addFrag(studentProfileAcadamicFragment, "Acadamic Report");
        //   AddChapterFragment addChapterFragment=new AddChapterFragment();
        // viewPagerAdapter.addFrag(addChapterFragment, "Add Chapter");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        changeTabsFont(getContext(), tabLayout, Color.rgb(0, 131, 143));

        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Academics");

        return timeTableView;
    }*/

 /*   @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }*/

    @Override
    public void sendData(String message) {
/*
        String tag = "android:switcher:" + R.id.viewPager + ":" + 1;
        StudentProfileAttendanceFragment f = (StudentProfileAttendanceFragment) getFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(message);*/

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
