package com.luckyshane.cnblogs.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;
import com.luckyshane.cnblogs.R;
import com.luckyshane.cnblogs.model.Injector;
import com.luckyshane.cnblogs.model.entity.Category;
import com.luckyshane.cnblogs.model.entity.NewsEntry;
import com.luckyshane.cnblogs.ui.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsListFragment extends BaseFragment {
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final int PAGE_SIZE = 25;
    private int categoryId;
    private boolean supportPaging;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.list_view)
    RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsEntry> newsEntryList = new ArrayList<>();
    private boolean isLoadingMore;
    private int currentPage;

    public static NewsListFragment create(int categoryId) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CATEGORY_ID, categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = getArguments().getInt(KEY_CATEGORY_ID);
        if (categoryId == Category.NEWS_RECENT || categoryId == Category.NEWS_RECOMM) {
            supportPaging = true;
        } else if (categoryId == Category.NEWS_HOT) {
            supportPaging = false;
        } else {
            throw new RuntimeException("");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected void initEventAndData() {
        newsEntryList.clear();
        adapter = new NewsAdapter(context, newsEntryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        RecyclerViewDivider.with(context).drawable(ContextCompat.getDrawable(context, R.drawable.comm_divider)).build().addTo(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContents();
            }
        });
        if (supportPaging) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    if (lastVisibleItem >= totalItemCount - 2 && dy > 0) { // 还剩2个Item时加载更多
                        if (!isLoadingMore) {
                            isLoadingMore = true;
                            loadMore();
                        }
                    }
                }
            });
        }
        loadContents();
    }

    private void loadContents() {
        currentPage = 1;
        Observable<List<NewsEntry>> api;
        if (categoryId == Category.NEWS_RECOMM) {
            api = Injector.getNewsProvider().getRecommNews(currentPage, PAGE_SIZE);
        } else if (categoryId == Category.NEWS_RECENT) {
            api = Injector.getNewsProvider().getRecentNews(currentPage, PAGE_SIZE);
        } else {
            api = Injector.getNewsProvider().getHotNews(PAGE_SIZE);
        }
        addSubscribe(api.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewsEntry>>() {
                    @Override
                    public void accept(List<NewsEntry> newsEntries) throws Exception {
                        showContents(newsEntries);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onLoadError(throwable);
                    }
                }));
    }

    private void loadMore() {
        Observable<List<NewsEntry>> api;
        if (categoryId == Category.NEWS_RECOMM) {
            api = Injector.getNewsProvider().getRecommNews(currentPage + 1, PAGE_SIZE);
        } else if (categoryId == Category.NEWS_RECENT) {
            api = Injector.getNewsProvider().getRecentNews(currentPage + 1, PAGE_SIZE);
        } else {
            return;
        }
        addSubscribe(api.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewsEntry>>() {
                    @Override
                    public void accept(List<NewsEntry> newsEntries) throws Exception {
                        showMore(newsEntries);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onLoadError(throwable);
                    }
                }));
    }

    private void showContents(List<NewsEntry> newsEntries) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        newsEntryList.clear();
        newsEntryList.addAll(newsEntries);
        adapter.setDataList(newsEntryList);
        adapter.notifyDataSetChanged();
    }

    private void onLoadError(Throwable throwable) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (isLoadingMore) {
            isLoadingMore = false;
        }
        Log.e(TAG, "onLoadError", throwable);
    }

    private void showMore(List<NewsEntry> newsEntries) {
        if (!newsEntries.isEmpty()) {
            currentPage++;
            newsEntryList.addAll(newsEntries);
            adapter.setDataList(newsEntryList);
            adapter.notifyDataSetChanged();
        }
        isLoadingMore = false;
    }


}
