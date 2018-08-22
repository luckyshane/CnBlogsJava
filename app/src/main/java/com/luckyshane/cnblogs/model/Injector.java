package com.luckyshane.cnblogs.model;

import com.luckyshane.cnblogs.model.repository.BlogProvider;
import com.luckyshane.cnblogs.model.repository.BlogRepository;
import com.luckyshane.cnblogs.model.repository.impl.BlogRemoteRepository;

public class Injector {
    private static BlogProvider blogProvider;
    private static BlogRepository remoteBlogRepository;
    private static BlogRepository localBlogRepository;

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

    public static BlogRepository getRemoteBlogRepository() {
        if (remoteBlogRepository == null) {
            synchronized (Injector.class) {
                if (remoteBlogRepository == null) {
                    remoteBlogRepository = new BlogRemoteRepository();
                }
            }
        }
        return remoteBlogRepository;
    }

    public static BlogRepository getLocalBlogRepository() {
        if (localBlogRepository == null) {
            synchronized (Injector.class) {
                if (localBlogRepository == null) {

                }
            }
        }
        return localBlogRepository;
    }




}
