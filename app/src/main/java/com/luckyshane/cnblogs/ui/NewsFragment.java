package com.luckyshane.cnblogs.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.luckyshane.cnblogs.R;
import com.luckyshane.cnblogs.model.entity.Category;
import com.luckyshane.cnblogs.ui.adapter.TabFragmentAdapter;
import com.luckyshane.cnblogs.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<Category> categories = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_content;
    }

    @Override
    protected void initEventAndData() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.removeAllTabs();

        categories.clear();
        fragments.clear();
        categories.add(new Category(Category.NEWS_RECENT, AppUtil.getString(R.string.tab_news_recent)));
        categories.add(new Category(Category.NEWS_RECOMM, AppUtil.getString(R.string.tab_news_recomm)));
        categories.add(new Category(Category.NEWS_HOT, AppUtil.getString(R.string.tab_news_hot)));

        for (Category category : categories) {
            fragments.add(NewsListFragment.create(category.id));
            tabLayout.addTab(tabLayout.newTab().setText(category.name));
        }
        TabFragmentAdapter adapter = new TabFragmentAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        int index = 0;
        for (Category categoryBean : categories) {
            tabLayout.getTabAt(index++).setText(categoryBean.name);
        }
    }


}
