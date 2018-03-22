package com.example.medianet.proschool.suresh.timetable;

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
import android.widget.TextView;

import com.example.medianet.proschool.ExamPaperFragment;
import com.example.medianet.proschool.ExamScheduleFragment;
import com.example.medianet.proschool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 29-06-2017.
 */

public class TimetableTabsFragment extends Fragment {
    View timeTableView;
    ArrayList<String> tabs;
    TabLayout tabLayout;
    ViewPager viewPager;
    Context mContext;
    String schoolId;
    String role;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        timeTableView = inflater.inflate(R.layout.timetable_tabs_layout, container, false);


        tabLayout = (TabLayout) timeTableView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) timeTableView.findViewById(R.id.viewPager);

        tabs = new ArrayList<String>();
        tabs.add("Schedule");
        tabs.add("Papers");
        tabs.add("Evaluations");
        // tabs.add("Reports");


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
TimeTableClassSection timeTableClassSection=new TimeTableClassSection();
       // ViewTimeTableClassFragment viewTimetableClassFragment = new ViewTimeTableClassFragment();
        viewPagerAdapter.addFrag(timeTableClassSection, "Timetable");

   /*     TimetableEventsFragment timetableEventsFragment = new TimetableEventsFragment();
        viewPagerAdapter.addFrag(timetableEventsFragment, "Events");

        TimetableNoticeFragment timetableNoticeFragment = new TimetableNoticeFragment();
        viewPagerAdapter.addFrag(timetableNoticeFragment, "Notice Board");*/


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
        changeTabsFont(getContext(), tabLayout, Color.rgb(0, 131, 143));

        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Timetable and Events");

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
}
