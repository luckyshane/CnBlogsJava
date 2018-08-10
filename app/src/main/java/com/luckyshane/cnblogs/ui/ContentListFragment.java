package com.luckyshane.cnblogs.ui;

import com.luckyshane.cnblogs.R;

public class ContentListFragment extends BaseFragment {

    public static ContentListFragment create(int id) {
        // TODO
       return new ContentListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected void initEventAndData() {

    }



}
