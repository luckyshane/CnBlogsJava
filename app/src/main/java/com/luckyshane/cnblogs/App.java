package com.luckyshane.cnblogs;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
    }

    public static App getInstance() {
        return instance;
    }


}
