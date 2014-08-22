package com.android4.fragment;

import roboguice.fragment.RoboFragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android4.impl.IBaseFragment;

public abstract class BaseFragment extends RoboFragment implements IBaseFragment {

    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int resId = getLayoutResource();
        if (resId != 0) {
            View mainView = inflater.inflate(getLayoutResource(), null);
            return mainView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    protected abstract int getLayoutResource();
}
