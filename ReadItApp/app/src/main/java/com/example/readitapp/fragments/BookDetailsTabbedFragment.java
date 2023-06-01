package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.readitapp.R;
import com.example.readitapp.adapters.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

import javax.annotation.Nullable;

public class BookDetailsTabbedFragment extends Fragment {

    private PagerAdapter pagerAdapter;

    private TabLayout tableLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_details_tabbed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tableLayout = view.findViewById(R.id.tab_layout);

        viewPager = view.findViewById(R.id.view_pager);
        tableLayout.setupWithViewPager(viewPager);

        pagerAdapter = new PagerAdapter(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
        bookDetailsFragment.setArguments(getArguments());

        ReviewsFragment reviewsFragment = new ReviewsFragment();
        reviewsFragment.setArguments(getArguments());

        pagerAdapter.addFragment(bookDetailsFragment, "Details");
        pagerAdapter.addFragment(reviewsFragment, "Reviews");

        viewPager.setAdapter(pagerAdapter);
    }
}