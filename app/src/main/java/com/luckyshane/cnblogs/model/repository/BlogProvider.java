package com.luckyshane.cnblogs.model.repository;

import com.luckyshane.cnblogs.model.api.BlogApiClient;
import com.luckyshane.cnblogs.model.api.cache.CacheHelper;
import com.luckyshane.cnblogs.model.entity.BlogEntry;
import com.luckyshane.cnblogs.model.entity.BlogResponse;
import com.luckyshane.cnblogs.util.DateUtil;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

public class BlogProvider {

    public Observable<List<BlogEntry>> getHomeBlogs(boolean forceUpdate, int pageIndex, int pageSize) {
        Observable<List<BlogEntry>> remote = BlogApiClient.getApiService().getRecentBlogsPage(pageIndex, pageSize)
                .map(new BlogEntryMapFunc());
        return CacheHelper.getInstance().getHomeBlogs(remote,
                new DynamicKey(String.format(Locale.US, "%d-%d", pageIndex, pageSize)), new EvictDynamicKey(forceUpdate));
    }

    public Observable<List<BlogEntry>> getHomeBlogs(int pageIndex, int pageSize) {
        return BlogApiClient.getApiService().getRecentBlogsPage(pageIndex, pageSize)
                .map(new BlogEntryMapFunc());
    }

    public Observable<List<BlogEntry>> getTopRecommPosts() {
        return BlogApiClient.getApiService().getTopRecommendPosts(25)
                .map(new BlogEntryMapFunc());
    }

    public Observable<List<BlogEntry>> getTopViewPosts() {
        return BlogApiClient.getApiService().getTopViewPosts(25)
                .map(new BlogEntryMapFunc());
    }

    public Observable<String> getBlogContent(String blogId, String timeStamp) {
        Observable<String> remote = BlogApiClient.getApiService().getBlogContent(blogId);
        return CacheHelper.getInstance().getBlogContent(remote, new DynamicKey(blogId + timeStamp));
    }

    private static class BlogEntryMapFunc implements Function<BlogResponse, List<BlogEntry>> {

        @Override
        public List<BlogEntry> apply(BlogResponse newsResponse) throws Exception {
            List<BlogEntry> entries = newsResponse.blogEntryList;
            for (BlogEntry entry : entries) {
                entry.publishDate = DateUtil.parseUTCDateStr(entry.publishedTimeStamp);
                entry.updateDate = DateUtil.parseUTCDateStr(entry.updateTimeStamp);
            }
            return entries;
        }
    }



}
