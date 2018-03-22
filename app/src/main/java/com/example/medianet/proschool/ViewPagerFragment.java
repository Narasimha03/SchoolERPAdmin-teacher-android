package com.example.medianet.proschool;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 30-06-2017.
 */

public class ViewPagerFragment extends Fragment{
    Context mContext;
    View tabView;
    ArrayList<String> tabs;
    TabLayout tabLayout;
    ViewPager viewPager;

    public ViewPagerFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        tabView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        tabLayout = (TabLayout) tabView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) tabView.findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        tabs = new ArrayList<>();
        tabs.add("Durga");
        tabs.add("Hema");
        tabs.add("Sravanthi");
        tabs.add("Divya");
        tabs.add("Manju");
        tabs.add("Sindhu");
        tabs.add("Ranju");
        tabs.add("Ganju");

        for (int i = 0; i < tabs.size(); i++) {
            ViewPagerPlaceFragment  viewPagerPlaceFragment  = new ViewPagerPlaceFragment();
            viewPagerAdapter.addFrag(viewPagerPlaceFragment,tabs.get(i));
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return tabView;
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
}
