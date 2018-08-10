package com.luckyshane.cnblogs.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.luckyshane.cnblogs.R;
import com.luckyshane.cnblogs.ui.adapter.TabFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_view_pager)
    ViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigationView;

    private List<Fragment> fragmentList;
    private int[] navIds = new int[] {
            R.id.navigation_blog,
            R.id.navigation_news,
            R.id.navigation_account
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_blog:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_news:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_account:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentList = new ArrayList<>();
        fragmentList.add(new BlogFragment());
        fragmentList.add(new NewsFragment());
        fragmentList.add(new AccountFragment());

        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigationView.setSelectedItemId(navIds[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }





}
