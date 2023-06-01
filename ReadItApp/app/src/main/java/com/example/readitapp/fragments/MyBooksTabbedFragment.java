package com.example.readitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.readitapp.R;
import com.example.readitapp.adapters.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

import javax.annotation.Nullable;

public class MyBooksTabbedFragment extends Fragment {
    // When requested, this adapter returns a DemoObjectFragment, representing
    // an object in the collection.
    private PagerAdapter pagerAdapter;

    private TabLayout tableLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_books_tab_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tableLayout = view.findViewById(R.id.tab_layout);

        viewPager = view.findViewById(R.id.view_pager);
        tableLayout.setupWithViewPager(viewPager);

        pagerAdapter = new PagerAdapter(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(new CurrentlyReadingFragment(), "Reading");
        pagerAdapter.addFragment(new ReadingHistoryFragment(), "History");

        viewPager.setAdapter(pagerAdapter);
    }
}
