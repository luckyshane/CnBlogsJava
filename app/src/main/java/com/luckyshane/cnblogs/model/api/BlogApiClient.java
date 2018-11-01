package com.luckyshane.cnblogs.model.api;

import android.content.Context;

import com.blankj.utilcode.util.NetworkUtils;
import com.luckyshane.cnblogs.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
            final ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
            Cache cache = new Cache(App.getInstance().getDir("api_cache", Context.MODE_PRIVATE), 10 * 1024 * 1024);
            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(cache)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    // cnblogs api在请求头里面增加了cookie之后才会有cache效果
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .addInterceptor(new CacheInterceptor())
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    private static class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            if (!NetworkUtils.isConnected()) {
                builder.cacheControl(CacheControl.FORCE_CACHE);
            }
            return chain.proceed(builder.build());
        }
    }





}
