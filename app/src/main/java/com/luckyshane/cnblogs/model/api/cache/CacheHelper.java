package com.luckyshane.cnblogs.model.api.cache;

import com.luckyshane.cnblogs.util.AppUtil;

import java.io.File;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

public class CacheHelper {
    private static BlogCacheProvider blogCacheProvider;
    private static RxCache rxCache;

    public static BlogCacheProvider getInstance() {
        if (blogCacheProvider == null) {
            synchronized (CacheHelper.class) {
                if (blogCacheProvider == null) {
                    blogCacheProvider = getRxCache().using(BlogCacheProvider.class);
                }
            }
        }
        return blogCacheProvider;
    }


    public static RxCache getRxCache() {
        if (rxCache == null) {
            File cacheDir = getCacheDir();
            if (!cacheDir.exists() && !cacheDir.mkdirs()) {
                return null;
            }
            RxCache.Builder builder = new RxCache.Builder().setMaxMBPersistenceCache(10)
                    .useExpiredDataIfLoaderNotAvailable(true);
            rxCache = builder.persistence(cacheDir, new GsonSpeaker());
        }
        return rxCache;
    }

    private static File getCacheDir() {
        return AppUtil.getDiskCacheDir(AppUtil.getAppContext(), "ApiCache");
    }


}
