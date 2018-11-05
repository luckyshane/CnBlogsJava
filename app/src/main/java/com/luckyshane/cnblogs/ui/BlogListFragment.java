package com.luckyshane.cnblogs.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;
import com.luckyshane.cnblogs.R;
import com.luckyshane.cnblogs.model.Injector;
import com.luckyshane.cnblogs.model.db.DBHelper;
import com.luckyshane.cnblogs.model.entity.BlogEntry;
import com.luckyshane.cnblogs.model.entity.Category;
import com.luckyshane.cnblogs.ui.adapter.BlogAdapter;
import com.luckyshane.cnblogs.util.JumpHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BlogListFragment extends BaseFragment {
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final int PAGE_SIZE = 20;
    private int categoryId;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.list_view)
    RecyclerView recyclerView;
    private BlogAdapter blogAdapter;
    private List<BlogEntry> mBlogEntryList = new ArrayList<>();
    private boolean supportPaging;
    private boolean isLoadingMore;
    private int currentPage;

    public static BlogListFragment create(int categoryId) {
        BlogListFragment fragment = new BlogListFragment();
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
        if (categoryId == Category.BLOG_HOME) {
            supportPaging = true;
        } else {
            supportPaging = false;
        }
    }

    @Override
    protected void initEventAndData() {
        mBlogEntryList.clear();
        blogAdapter = new BlogAdapter(context, mBlogEntryList);
        blogAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BlogEntry entry = mBlogEntryList.get(position);
                showBlogDetailPage(entry);
                if (!entry.isRead) {
                    entry.isRead = true;
                    blogAdapter.notifyItemChanged(position);
                    DBHelper.getInstance().insertReadBlogId(entry.id);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerViewDivider.with(context).drawable(getResources().getDrawable(R.drawable.comm_divider)).build().addTo(recyclerView);
        recyclerView.setAdapter(blogAdapter);
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadContents();
            }
        });
        loadContents();
    }

    private void loadContents() {
        currentPage = 1;
        Observable<List<BlogEntry>> api = null;
        if (categoryId == Category.BLOG_HOME) {
            api = Injector.getBlogProvider().getHomeBlogs(currentPage, PAGE_SIZE);
        } else if (categoryId == Category.BLOG_TOP_RECOMM_10DAYS) {
            api = Injector.getBlogProvider().getTopRecommPosts();
        } else {
            api = Injector.getBlogProvider().getTopViewPosts();
        }
        addSubscribe(api
                .map(new ReadStateMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<List<BlogEntry>>() {
                    @Override
                    public void accept(List<BlogEntry> blogEntries) throws Exception {
                        showContent(blogEntries);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onLoadError(throwable);
                    }
                }));
    }

    private void loadMore() {
        addSubscribe(Injector.getBlogProvider().getHomeBlogs(currentPage + 1, PAGE_SIZE)
                .map(new ReadStateMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<List<BlogEntry>>() {
                    @Override
                    public void accept(List<BlogEntry> blogEntries) throws Exception {
                        if (!blogEntries.isEmpty()) {
                            currentPage++;
                            mBlogEntryList.addAll(blogEntries);
                            blogAdapter.setDataList(mBlogEntryList);
                            blogAdapter.notifyDataSetChanged();
                        }
                        isLoadingMore = false;

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isLoadingMore = false;
                    }
                }));
    }

    private class ReadStateMap implements Function<List<BlogEntry>, List<BlogEntry>> {

        @Override
        public List<BlogEntry> apply(List<BlogEntry> blogEntries) throws Exception {
            if (blogEntries != null) {
                List<String> readIs = DBHelper.getInstance().getAllReadIds();
                for (BlogEntry blogEntry : blogEntries) {
                    blogEntry.isRead = readIs.contains(blogEntry.id);
                }
            }
            return blogEntries;
        }
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
        Log.e(TAG, "onLoadError", throwable);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showBlogDetailPage(BlogEntry blogEntry) {
        JumpHelper.openBlogDetailPage(context, blogEntry);
    }


}
