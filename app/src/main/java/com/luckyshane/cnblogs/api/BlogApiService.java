package com.luckyshane.cnblogs.api;

import com.luckyshane.cnblogs.model.BlogResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlogApiService {


    @GET("blog/sitehome/recent/{count}")
    Call<BlogResponse> getRecentBlogs(@Path("count") int count);





}
