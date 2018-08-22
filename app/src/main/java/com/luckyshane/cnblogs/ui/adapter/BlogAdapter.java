package com.luckyshane.cnblogs.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.luckyshane.cnblogs.R;
import com.luckyshane.cnblogs.model.entity.BlogEntry;
import com.luckyshane.cnblogs.util.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private List<BlogEntry> blogEntryList;
    private AdapterView.OnItemClickListener onItemClickListener;


    public BlogAdapter(List<BlogEntry> blogEntries) {
        this.blogEntryList = blogEntries;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDataList(List<BlogEntry> blogEntries) {
        this.blogEntryList = blogEntries;
    }

    @NonNull
    @Override
    public BlogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_blog_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogAdapter.ViewHolder holder, final int position) {
        BlogEntry entry = blogEntryList.get(position);
        holder.titleTv.setText(StringUtil.getEmptyIfNull(entry.title));
        holder.summaryTv.setText(StringUtil.getEmptyIfNull(entry.summary));
        holder.authorTv.setText(StringUtil.getEmptyIfNull(entry.author.name));
        holder.commentTv.setText(String.valueOf(entry.commentCount));
        holder.loveTv.setText(String.valueOf(entry.raiseCount));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(null, null, position, 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogEntryList != null ? blogEntryList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.summary_tv)
        TextView summaryTv;
        @BindView(R.id.author_tv)
        TextView authorTv;
        @BindView(R.id.comment_tv)
        TextView commentTv;
        @BindView(R.id.love_tv)
        TextView loveTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
