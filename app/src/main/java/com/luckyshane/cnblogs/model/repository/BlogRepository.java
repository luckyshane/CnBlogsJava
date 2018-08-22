package com.luckyshane.cnblogs.model.repository;

import com.luckyshane.cnblogs.model.entity.BlogEntry;

import java.util.List;

import io.reactivex.Observable;

public interface BlogRepository {

    Observable<List<BlogEntry>> getHomeBlogs(int pageIndex, int pageSize);



}
