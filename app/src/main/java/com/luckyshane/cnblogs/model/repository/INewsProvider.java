package com.luckyshane.cnblogs.model.repository;

import com.luckyshane.cnblogs.model.entity.NewsContentResponse;
import com.luckyshane.cnblogs.model.entity.NewsEntry;

import java.util.List;

import io.reactivex.Observable;

public interface INewsProvider {

    Observable<List<NewsEntry>> getHotNews(int count);

    Observable<List<NewsEntry>> getRecommNews(int pageIndex, int pageSize);

    Observable<List<NewsEntry>> getRecentNews(int pageIndex, int pageSize);

    Observable<NewsContentResponse> getNewsContent(String newsId);

}
