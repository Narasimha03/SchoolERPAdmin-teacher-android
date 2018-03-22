package com.example.medianet.proschool;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JANI on 09-05-2017.
 */

public class FeeTypeFragment extends Fragment{
    // Tab Layout, View Pager....
    TabLayout tabLayout;
    ViewPager tabViewPager;
    public FeeTypeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View feeTypeView = inflater.inflate(R.layout.fee_type_fragment, container, false);
        tabLayout = (TabLayout) feeTypeView.findViewById(R.id.tabLayout);
        tabViewPager = (ViewPager) feeTypeView.findViewById(R.id.tabViewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), getChildFragmentManager());
        StudentCategoryFragment studentCategoryFragment = new StudentCategoryFragment();
        FeesMasterFragment feesMasterFragment = new FeesMasterFragment();
        viewPagerAdapter.addFragment(studentCategoryFragment, "Student category");
        viewPagerAdapter.addFragment(feesMasterFragment, "Fee Master");
        tabViewPager.setAdapter(viewPagerAdapter);
        //setupViewPager(tabViewPager);
        tabLayout.setupWithViewPager(tabViewPager);
        // to set action bar title....
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Fees Type");

        return feeTypeView;
    }
    // ViewPagerAdapter....
    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
            //return null;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
            //return "Page " + position;
        }
    }
}
