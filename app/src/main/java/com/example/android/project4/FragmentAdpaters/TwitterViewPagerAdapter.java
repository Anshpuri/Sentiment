package com.example.android.project4.FragmentAdpaters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by amandhapola on 08/07/17.
 */

public class TwitterViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragementList;
    private ArrayList<String> fragmentTitleList;

    public TwitterViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragementList,ArrayList<String> fragmentTitleList) {
        super(fm);
        this.fragementList = fragementList;
        this.fragmentTitleList=fragmentTitleList;
    }

    public TwitterViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragementList.get(position);
    }

    @Override
    public int getCount() {
        return fragementList.size();
    }
}
