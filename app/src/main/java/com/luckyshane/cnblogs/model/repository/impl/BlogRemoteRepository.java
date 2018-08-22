package com.luckyshane.cnblogs.model.repository.impl;

import com.luckyshane.cnblogs.model.api.BlogApiClient;
import com.luckyshane.cnblogs.model.entity.BlogEntry;
import com.luckyshane.cnblogs.model.entity.BlogResponse;
import com.luckyshane.cnblogs.model.repository.BlogRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class BlogRemoteRepository implements BlogRepository {


    @Override
    public Observable<List<BlogEntry>> getHomeBlogs(int pageIndex, int pageSize) {
        return BlogApiClient.getApiService().getRecentBlogsPage(pageIndex, pageSize).map(new Function<BlogResponse, List<BlogEntry>>() {
            @Override
            public List<BlogEntry> apply(BlogResponse blogResponse) throws Exception {
                return blogResponse.blogEntryList;
            }
        });
    }




}
