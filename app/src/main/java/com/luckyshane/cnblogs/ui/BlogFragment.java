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

public class BlogFragment extends BaseFragment {
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
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.removeAllTabs();

        categories.clear();
        fragments.clear();
        categories.add(new Category(Category.BLOG_HOME, AppUtil.getString(R.string.tab_blog_home)));
        categories.add(new Category(Category.BLOG_TOP_VIEW_48HOURS, AppUtil.getString(R.string.tab_blog_top_view_48hours)));
        categories.add(new Category(Category.BLOG_TOP_RECOMM_10DAYS, AppUtil.getString(R.string.tab_blog_top_recomm_10days)));

        for (Category category : categories) {
            fragments.add(BlogListFragment.create(category.id));
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
