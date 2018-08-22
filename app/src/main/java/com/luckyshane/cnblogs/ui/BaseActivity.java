package com.luckyshane.cnblogs.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    private Unbinder unbinder;
    protected Activity context;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        int layout = getLayoutId();
        if (layout != 0) {
            setContentView(getLayoutId());
            unbinder = ButterKnife.bind(this);
        }
        initEventAndData(savedInstanceState);
    }

    protected abstract int getLayoutId();
    protected abstract void initEventAndData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        unSubscribe();
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }


    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }



}
