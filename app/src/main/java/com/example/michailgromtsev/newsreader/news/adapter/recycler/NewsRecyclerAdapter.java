package com.example.michailgromtsev.newsreader.news.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.michailgromtsev.newsreader.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsViewHolder> {


    private final List<NewsItem> items = new ArrayList<>();

    private final LayoutInflater inflater;
    private final RequestManager imageLoader;
    @Nullable
    private OnItemClickListener newsListener;


    public NewsRecyclerAdapter(@NonNull Context context) {

        this.inflater = LayoutInflater.from(context);
        final RequestOptions imgeOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background)
                .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imgeOptions);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return NewsViewHolder.create(inflater,parent,imageLoader);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
     final NewsItem newsItem = items.get(position);
     holder.bind(newsItem,newsListener);
    }

    public void setOnClickListner (@NonNull OnItemClickListener newsListener) {
        this.newsListener = newsListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceItems (List<NewsItem> newsItems) {
        items.clear();
        items.addAll(newsItems);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull NewsItem newsItem);
    }
}
