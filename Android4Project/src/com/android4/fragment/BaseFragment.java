package com.android4.fragment;

import roboguice.fragment.RoboFragment;
import android.app.Activity;

import com.android4.impl.IBaseFragment;

public abstract class BaseFragment extends RoboFragment implements IBaseFragment {

    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }
}
