package com.luckyshane.cnblogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.luckyshane.cnblogs.api.BlogApiClient;
import com.luckyshane.cnblogs.model.BlogEntry;
import com.luckyshane.cnblogs.model.BlogResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BlogApiClient.getApiService().getRecentBlogs(10).enqueue(new Callback<BlogResponse>() {
            @Override
            public void onResponse(Call<BlogResponse> call, Response<BlogResponse> response) {
                BlogResponse blogResponse = response.body();
                Log.d(TAG, "onResponse " + blogResponse.updateTimeStamp + ", title: " + blogResponse.title);
                if (blogResponse.blogEntryList != null) {
                    for (BlogEntry entry : blogResponse.blogEntryList) {
                        Log.d(TAG, entry.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<BlogResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });

    }

}
