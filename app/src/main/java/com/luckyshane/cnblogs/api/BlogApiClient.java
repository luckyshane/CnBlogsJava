package com.luckyshane.cnblogs.api;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class BlogApiClient {
    private static final String BASE_URL = "http://wcf.open.cnblogs.com/";
    private static Retrofit retrofit;


    public static BlogApiService getApiService() {
        return getRetrofit().create(BlogApiService.class);
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
