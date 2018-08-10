package com.luckyshane.cnblogs.model.entity;

public class Category {
    public static final int BLOG_HOME = 0;
    public static final int BLOG_TOP_VIEW_48HOURS = 1;
    public static final int BLOG_TOP_RECOMM_10DAYS = 2;

    public String name;
    public int id;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
