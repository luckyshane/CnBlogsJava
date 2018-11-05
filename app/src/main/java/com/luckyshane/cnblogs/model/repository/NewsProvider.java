package com.luckyshane.cnblogs.model.repository;

import com.luckyshane.cnblogs.model.api.BlogApiClient;
import com.luckyshane.cnblogs.model.entity.NewsContentResponse;
import com.luckyshane.cnblogs.model.entity.NewsEntry;
import com.luckyshane.cnblogs.model.entity.NewsResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class NewsProvider implements INewsProvider {

    @Override
    public Observable<List<NewsEntry>> getHotNews(int count) {
        return BlogApiClient.getApiService().getHotNews(count).map(new NewsEntryMapFunc());
    }

    @Override
    public Observable<List<NewsEntry>> getRecommNews(int pageIndex, int pageSize) {
        return BlogApiClient.getApiService().getRecommNews(pageIndex, pageSize).map(new NewsEntryMapFunc());
    }

    @Override
    public Observable<List<NewsEntry>> getRecentNews(int pageIndex, int pageSize) {
        return BlogApiClient.getApiService().getRecentNews(pageIndex, pageSize).map(new NewsEntryMapFunc());
    }

    @Override
    public Observable<NewsContentResponse> getNewsContent(String newsId) {
        return BlogApiClient.getApiService().getNewsContent(newsId);
    }

    private static class NewsEntryMapFunc implements Function<NewsResponse, List<NewsEntry>> {

        @Override
        public List<NewsEntry> apply(NewsResponse newsResponse) throws Exception {
            return newsResponse.newsEntryList;
        }
    }


}
