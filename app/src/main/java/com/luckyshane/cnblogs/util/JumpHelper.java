package com.luckyshane.cnblogs.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.luckyshane.cnblogs.model.entity.BlogEntry;
import com.luckyshane.cnblogs.ui.BlogDetailActivity;

public class JumpHelper {
    public static final String KEY_BLOG_ENTRY = "blog_entry";


    public static void openPage(Context fromContext, Class<?> cls, Bundle extras) {
        Intent intent = new Intent(fromContext, cls);
        intent.putExtras(extras);
        fromContext.startActivity(intent);
    }
    public static void openBlogDetailPage(Context fromContext, BlogEntry blogEntry) {
        Bundle extra = new Bundle();
        extra.putSerializable(KEY_BLOG_ENTRY, blogEntry);
        openPage(fromContext, BlogDetailActivity.class, extra);
    }




}
