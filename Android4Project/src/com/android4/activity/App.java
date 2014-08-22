package com.android4.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;

public class App extends Application {

    public Map<Integer, Integer> mUnReadPushCount = new HashMap<Integer, Integer>();

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

}
