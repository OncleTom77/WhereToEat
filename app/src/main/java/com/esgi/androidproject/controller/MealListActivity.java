package com.esgi.androidproject.controller;

import android.content.Intent;
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
import com.esgi.androidproject.model.Meal;
import com.esgi.androidproject.model.Restaurant;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by Vincent on 27/01/2017.
 */

public class MealListActivity extends FragmentActivity {

    private PagerAdapter mPagerAdapter;
    private Restaurant restaurant;
    private int givenMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle b = getIntent().getExtras();
        this.restaurant = (Restaurant) b.getSerializable("restaurant");
        System.out.println(restaurant.getId());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals_list);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(mPagerAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById (R.id.indicator);
        indicator.setViewPager(pager);
    }

    public Restaurant getRestaurant(){
        return this.restaurant;
    }

    public int getGivenMeal(){
        return this.givenMeal;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        Bundle bundle;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int mealsNumber = restaurant.getMeals().size();
            if(position < mealsNumber){
                givenMeal = position;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
