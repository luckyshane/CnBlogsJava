package com.luckyshane.cnblogs;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.luckyshane.cnblogs.model.db.entity.MyObjectBox;

import io.objectbox.BoxStore;

public class App extends Application {
    private static App instance;
    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        boxStore = MyObjectBox.builder().androidContext(this).build();
        if (BuildConfig.DEBUG) {
            // boolean started = new AndroidObjectBrowser(boxStore).start(this);
            // Log.i("ObjectBrowser", "Started: " + started);
        }
    }

    public static App getInstance() {
        return instance;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

}
