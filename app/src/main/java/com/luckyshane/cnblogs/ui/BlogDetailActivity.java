package com.luckyshane.cnblogs.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.luckyshane.cnblogs.R;
import com.luckyshane.cnblogs.model.Injector;
import com.luckyshane.cnblogs.model.entity.BlogEntry;
import com.luckyshane.cnblogs.util.JumpHelper;
import com.luckyshane.cnblogs.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BlogDetailActivity extends BaseActivity {
    private BlogEntry blogEntry;
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_blog_detail;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            blogEntry = (BlogEntry) savedInstanceState.getSerializable(JumpHelper.KEY_BLOG_ENTRY);
        } else {
            blogEntry = (BlogEntry) getIntent().getExtras().getSerializable(JumpHelper.KEY_BLOG_ENTRY);
        }
        initWebView();
        loadContent(blogEntry.id, blogEntry.updateTimeStamp);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(JumpHelper.KEY_BLOG_ENTRY, blogEntry);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
    }

    private void loadContent(String id, String timestamp) {
        addSubscribe(Injector.getBlogProvider().getBlogContent(id, timestamp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        loadHtml(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    private void loadHtml(String content) {
        String htmlContent = "";
        try {
            InputStream template = getAssets().open("html/blog.html");
            htmlContent = StringUtil.readFromInputStream(template);
            htmlContent = htmlContent.replace("#body#", content);
            webView.loadDataWithBaseURL("file:///android_asset/", htmlContent, "text/html", "UTF-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
