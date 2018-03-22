package com.example.medianet.proschool.suresh.feemodule;

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

import com.example.medianet.proschool.FeesMasterFragment;
import com.example.medianet.proschool.R;
import com.example.medianet.proschool.suresh.feemodule.FeeTypeFragmentMain;
import com.example.medianet.proschool.suresh.feemodule.feecollection.AllFeeCollectionFragment;
import com.example.medianet.proschool.suresh.feemodule.feecollection.AllFeeCollectionReportsByDayFragment;
import com.example.medianet.proschool.suresh.feemodule.feecollection.AllFeeCollectionReportsFragment;
import com.example.medianet.proschool.suresh.feemodule.feemaster.AllFeeMasterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 29-06-2017.
 */

public class FeeModuleTabFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        timeTableView = inflater.inflate(R.layout.fee_module_tabs_layout, container, false);
        tabLayout = (TabLayout) timeTableView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) timeTableView.findViewById(R.id.viewPager);

        tabs = new ArrayList<String>();
        tabs.add("Fee Type");
        tabs.add("Fee Master");
        tabs.add("Fee Collection");

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        /*FeeTypeFragmentMain feeTypeFragmentMain = new FeeTypeFragmentMain();
        viewPagerAdapter.addFrag(feeTypeFragmentMain, "Fee Type");
        AllFeeMasterFragment allFeeMasterFragment = new AllFeeMasterFragment();
        viewPagerAdapter.addFrag(allFeeMasterFragment, "Fee Master");*/
        AllFeeCollectionFragment allFeeCollectionFragment=new AllFeeCollectionFragment();
        viewPagerAdapter.addFrag(allFeeCollectionFragment,"Fee Collection");
        AllFeeCollectionReportsFragment collectFeeReports=new AllFeeCollectionReportsFragment();
        viewPagerAdapter.addFrag(collectFeeReports,"Fee Reports By Student");
        AllFeeCollectionReportsByDayFragment feeReportsByDay=new AllFeeCollectionReportsByDayFragment();
        viewPagerAdapter.addFrag(feeReportsByDay,"Fee Reports By Date");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        changeTabsFont(getContext(), tabLayout, Color.rgb(0, 131, 143));

        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Fee Module");

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
