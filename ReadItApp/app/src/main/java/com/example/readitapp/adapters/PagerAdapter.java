package com.example.readitapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

// Since this is an object collection, use a FragmentStatePagerAdapter, not a
// FragmentPagerAdapter.
public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> fragmentTitlesList;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitlesList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitlesList.get(position);
    }

    public void initAdapter() {
        fragmentList = new ArrayList<>();
        fragmentTitlesList = new ArrayList<>();
    }
}
