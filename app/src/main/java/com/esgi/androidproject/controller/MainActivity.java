package com.esgi.androidproject.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.esgi.androidproject.R;
import com.esgi.androidproject.controller.fragment.ListPageFragment;
import com.esgi.androidproject.controller.fragment.MapPageFragment;
import com.esgi.androidproject.controller.fragment.OptionPageFragment;
import com.esgi.androidproject.controller.fragment.SharePageFragment;
import com.viewpagerindicator.CirclePageIndicator;

public class MainActivity extends FragmentActivity {

    private static final int NUM_PAGES = 4;

    private static final int MY_REQUEST_CODE = 1;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
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

    public void updateMapFragment(Fragment currentFragment) {

        FragmentManager manager = getSupportFragmentManager();

        for(Fragment fragment : manager.getFragments()) {
            if(fragment != null
                    && fragment.getId() != currentFragment.getId()
                    && fragment.isVisible()
                    && fragment instanceof MapPageFragment) {
                System.out.println("LOAD IN UPDATE");
                ((MapPageFragment) fragment).loadRestaurants();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("MAIN ACTIVITY", "NEED PERMISSIONS !");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(MY_REQUEST_CODE == requestCode) {
            Log.d("MAIN ACTIVITY", "PERMISSION GRANTED BY THE USER !");
        } else {
            Log.d("MAIN ACTIVITY", "PERMISSION NOT GRANTED BY THE USER !");
        }
    }
}