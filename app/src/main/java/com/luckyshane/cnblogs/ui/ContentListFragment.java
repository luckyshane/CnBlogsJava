package com.luckyshane.cnblogs.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.luckyshane.cnblogs.R;
import com.luckyshane.cnblogs.model.Injector;
import com.luckyshane.cnblogs.model.entity.BlogEntry;
import com.luckyshane.cnblogs.ui.adapter.BlogAdapter;
import com.luckyshane.cnblogs.util.JumpHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ContentListFragment extends BaseFragment {
    private static final String KEY_CATEGORY_ID = "category_id";
    private int categoryId;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.list_view)
    RecyclerView recyclerView;
    private BlogAdapter blogAdapter;
    private List<BlogEntry> mBlogEntryList = new ArrayList<>();

    public static ContentListFragment create(int categoryId) {
        ContentListFragment fragment = new ContentListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CATEGORY_ID, categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = getArguments().getInt(KEY_CATEGORY_ID);
    }

    @Override
    protected void initEventAndData() {
        mBlogEntryList.clear();
        blogAdapter = new BlogAdapter(mBlogEntryList);
        blogAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showBlogDetailPage(mBlogEntryList.get(position));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(blogAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContents(categoryId, true);
            }
        });
        loadContents(categoryId, false);
    }

    private void loadContents(int categoryId, boolean forceUpdate) {
        addSubscribe(Injector.getBlogProvider().getHomeBlogs(forceUpdate, 1, 25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BlogEntry>>() {
                    @Override
                    public void accept(List<BlogEntry> blogEntries) throws Exception {
                        showContent(blogEntries);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "loadContents", throwable);
                        onLoadError(throwable);
                    }
                }));
    }

    private void showContent(List<BlogEntry> blogEntries) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        mBlogEntryList.clear();
        mBlogEntryList.addAll(blogEntries);
        blogAdapter.setDataList(mBlogEntryList);
        blogAdapter.notifyDataSetChanged();
    }

    private void onLoadError(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showBlogDetailPage(BlogEntry blogEntry) {
        JumpHelper.openBlogDetailPage(context, blogEntry);
    }





}
