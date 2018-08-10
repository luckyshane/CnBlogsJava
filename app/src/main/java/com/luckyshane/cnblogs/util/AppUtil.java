package com.luckyshane.cnblogs.util;

import android.content.Context;

import com.luckyshane.cnblogs.App;

public class AppUtil {

    public static String getString(int id) {
        return getAppContext().getString(id);
    }

    public static Context getAppContext() {
        return App.getInstance();
    }


}
