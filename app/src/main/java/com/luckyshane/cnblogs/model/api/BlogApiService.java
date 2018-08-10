package com.luckyshane.cnblogs.model.api;

import com.luckyshane.cnblogs.model.entity.BlogResponse;
import com.luckyshane.cnblogs.model.entity.CommentResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlogApiService {


    /**
     * 获取首页文章列表
     */
    @GET("blog/sitehome/recent/{count}")
    Observable<BlogResponse> getRecentBlogs(@Path("count") int count);

    /**
     * 分页获取首页文章列表
     * @param index 页索引。从1开始
     * @param pageSize 页大小
     */
    @GET("blog/sitehome/recent/paged/{index}/{pageSize}")
    Observable<BlogResponse> getRecentBlogsPage(@Path("index") int index, @Path("pageSize") int pageSize);

    /**
     * 获取48小时阅读排行列表
     */
    @GET("blog/48HoursTopViewPosts/{count}")
    Observable<BlogResponse> getTopViewPosts(@Path("count") int count);

    /**
     * 获取十天推荐排行
     */
    @GET("blog/TenDaysTopDiggPosts/{itemCount}")
    Observable<BlogResponse> getTopRecommendPosts(@Path("itemCount") int count);


    /**
     * 根据博客id获取博客内容
     */
    @GET("blog/post/body/{id}")
    Observable<String> getBlogContent(@Path("id") String blogId);

    /**
     * 分页获取博客评论
     * @param pageIndex 页号，从1开始
     * @param pageSize 页大小
     */
    @GET("blog/post/9446538/comments/{pageIndex}/{pageSize}")
    Observable<CommentResponse> getBlogComments(@Path("pageIndex") int pageIndex, @Path("pageSize") int pageSize);





}
