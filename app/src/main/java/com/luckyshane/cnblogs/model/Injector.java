package com.luckyshane.cnblogs.model;

import com.luckyshane.cnblogs.model.repository.BlogProvider;
import com.luckyshane.cnblogs.model.repository.INewsProvider;
import com.luckyshane.cnblogs.model.repository.NewsProvider;

public class Injector {
    private static BlogProvider blogProvider;
    private static NewsProvider newsProvider;

    public static BlogProvider getBlogProvider() {
        if (blogProvider == null) {
            synchronized (Injector.class) {
                if (blogProvider == null) {
                    blogProvider = new BlogProvider();
                }
            }
        }
        return blogProvider;
    }

    public static INewsProvider getNewsProvider() {
        if (newsProvider == null) {
            synchronized (Injector.class) {
                if (newsProvider == null) {
                    newsProvider = new NewsProvider();
                }
            }
        }
        return newsProvider;
    }





}
