package com.fb.recyclerviewdemo.app;

import android.app.Application;

/**
 * Description:
 * CreateTime: 2018/7/25 14:42
 * Author: ShengDecheng
 */

public class MyApplication extends Application {

    public static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
    }
}
