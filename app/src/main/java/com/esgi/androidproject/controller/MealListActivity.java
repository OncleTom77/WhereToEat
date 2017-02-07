package com.esgi.androidproject.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.esgi.androidproject.controller.fragment.MealCardFragment;
import com.esgi.androidproject.controller.fragment.MealFormFragment;
import com.esgi.androidproject.controller.fragment.OptionPageFragment;
import com.esgi.androidproject.controller.fragment.SharePageFragment;
import com.esgi.androidproject.model.Restaurant;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by Vincent on 27/01/2017.
 */

public class MealListActivity extends FragmentActivity {

    private PagerAdapter mPagerAdapter;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        this.restaurant = (Restaurant) b.getSerializable("restaurant");

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
        public Fragment getItem(int position) {
            int mealsNumber = restaurant.getMeals().size();
            if(position < mealsNumber){
                return new MealCardFragment();
            } else {
                return new MealFormFragment();
            }
        }

        @Override
        public int getCount() {
            return restaurant.getMeals().size() + 1;
        }
    }
}