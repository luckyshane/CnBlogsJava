package com.luckyshane.cnblogs.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.luckyshane.cnblogs.R;
import com.luckyshane.cnblogs.model.entity.NewsEntry;
import com.luckyshane.cnblogs.util.Formatter;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsEntry> newsEntryList;
    private Context context;

    public NewsAdapter(Context context, List<NewsEntry> newsEntries) {
        this.context = context;
        this.newsEntryList = newsEntries;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_news_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        LogUtils.d("position: " + position);
        NewsEntry entry = newsEntryList.get(position);
        holder.titleTv.setText(entry.title);
        holder.summaryTv.setText(entry.summary);
        holder.sourceTv.setText(entry.sourceName);
        holder.readCountTv.setText(String.format(Locale.CHINA, "阅读 · %d", entry.viewCount));
        holder.raiseCountTv.setText(String.format(Locale.CHINA, "点赞 · %d", entry.raiseCount));
        holder.commentCountTv.setText(String.format(Locale.CHINA, "评论 · %d", entry.commentCount));
        holder.timeTv.setText(Formatter.formatDateCompareNow(entry.publishDate));
    }

    @Override
    public int getItemCount() {
        return newsEntryList != null ? newsEntryList.size() : 0;
    }

    public void setDataList(List<NewsEntry> newsEntries) {
        this.newsEntryList = newsEntries;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.summary_tv)
        TextView summaryTv;
        @BindView(R.id.time_tv)
        TextView timeTv;
        @BindView(R.id.source_name_tv)
        TextView sourceTv;
        @BindView(R.id.read_count_tv)
        TextView readCountTv;
        @BindView(R.id.raise_count_tv)
        TextView raiseCountTv;
        @BindView(R.id.comment_tv)
        TextView commentCountTv;
        @BindView(R.id.news_icon_iv)
        ImageView newsIv;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
