package com.esgi.androidproject.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esgi.androidproject.R;
import com.esgi.androidproject.controller.MainActivity;
import com.esgi.androidproject.controller.fragment.ListPageFragment;
import com.esgi.androidproject.controller.fragment.MapPageFragment;
import com.esgi.androidproject.controller.fragment.OptionPageFragment;
import com.esgi.androidproject.controller.fragment.SharePageFragment;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by Vincent on 27/01/2017.
 */

public class MealListActivity extends FragmentActivity {

    private static final int NUM_PAGES = 5;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals_list);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mPagerAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById (R.id.indicator);
        indicator.setViewPager(pager);

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new MapPageFragment();
                case 1:
                    return new ListPageFragment();
                case 2:
                    return new SharePageFragment();
                case 3:
                    return new OptionPageFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
