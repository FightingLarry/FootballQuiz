package com.android4.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.android4.fragment.handler.HandlerThreadFragment;
import com.android4.fragment.touchimageview.TouchImageViewFragment;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private final String[] TITLE = new String[] { "∂”ŒÈ", "" };

    private final int mCount = TITLE.length;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("Android4Project", "new fragment");
        switch (position) {
            case 0:
                return HandlerThreadFragment.newInstance();
            case 1:
                return TouchImageViewFragment.newInstance();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position % mCount];
    }

}
