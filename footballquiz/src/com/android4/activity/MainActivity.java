package com.android4.activity;

import roboguice.activity.RoboFragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.android4.R;
import com.android4.adapter.MainFragmentAdapter;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends RoboFragmentActivity {

    private MainFragmentAdapter mAdapter;

    private ViewPager mPager;

    private PageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);

    }
}
