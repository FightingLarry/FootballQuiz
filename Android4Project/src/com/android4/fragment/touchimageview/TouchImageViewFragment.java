package com.android4.fragment.touchimageview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android4.fragment.BaseFragment;

public class TouchImageViewFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_bluemountain, null);
        super.onCreateView(inflater, container, savedInstanceState);
        return new CropperView(mActivity);
    }

    public static TouchImageViewFragment newInstance() {

        return new TouchImageViewFragment();
    }

}
