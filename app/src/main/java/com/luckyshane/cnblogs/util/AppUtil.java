package com.luckyshane.cnblogs.util;

import android.content.Context;
import android.os.Environment;

import com.luckyshane.cnblogs.App;

import java.io.File;

public class AppUtil {

    public static String getString(int id) {
        return getAppContext().getString(id);
    }

    public static Context getAppContext() {
        return App.getInstance();
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir == null) {
                cacheDir = context.getCacheDir();
            }
            cachePath = cacheDir.getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


}
