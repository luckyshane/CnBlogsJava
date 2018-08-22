package com.luckyshane.cnblogs.model.api.cache;

import com.luckyshane.cnblogs.model.entity.BlogEntry;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

public interface BlogCacheProvider {

    @LifeCache(duration = 30, timeUnit = TimeUnit.MINUTES)
    Observable<List<BlogEntry>> getHomeBlogs(Observable<List<BlogEntry>> remote, DynamicKey page, EvictProvider evictProvider);

    @LifeCache(duration = 30, timeUnit = TimeUnit.MINUTES)
    Observable<String> getBlogContent(Observable<String> remote, DynamicKey id);


}
