package com.luckyshane.cnblogs.model.api;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class BlogApiClient {
    private static final String BASE_URL = "http://wcf.open.cnblogs.com/";
    private static Retrofit retrofit;
    private static BlogApiService blogApiService;

    public static BlogApiService getApiService() {
        if (blogApiService == null) {
            synchronized (BlogApiClient.class) {
                if (blogApiService == null) {
                    blogApiService = getRetrofit().create(BlogApiService.class);
                }
            }
        }
        return blogApiService;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return retrofit;
    }







}
