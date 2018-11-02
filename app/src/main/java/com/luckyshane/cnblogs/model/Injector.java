package com.luckyshane.cnblogs.model;

import com.luckyshane.cnblogs.model.repository.BlogProvider;

public class Injector {
    private static BlogProvider blogProvider;

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





}
