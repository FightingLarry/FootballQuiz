package com.android4.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android4.fragment.fonts.FontsFragment;
import com.android4.fragment.handler.HandlerThreadFragment;
import com.android4.fragment.installapk.InstallApkFragment;
import com.android4.fragment.push.PushFragment;
import com.android4.fragment.touchimageview.TouchImageViewFragment;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    private final String[] TITLE = new String[] { "通知", "线程间通信", "图片缩放", "字体", "安装应用" };

    private final int mCount = TITLE.length;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PushFragment.newInstance();
            case 1:
                return HandlerThreadFragment.newInstance();
            case 2:
                return TouchImageViewFragment.newInstance();
            case 3:
                return FontsFragment.newInstance();
            case 4:
                return new InstallApkFragment();
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
