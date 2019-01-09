package com.example.michailgromtsev.newsreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.michailgromtsev.newsreader.data.NewsItem;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    @NonNull
    private final List<NewsItem> items = new ArrayList<>();
    @NonNull
    private final LayoutInflater inflater;
    @NonNull
    private final RequestManager imageLoader;
    @Nullable
    private final OnItemClickListener clickListener;

    private final int TYPE_SIMPLE_NEWS_ITEM = 0;
    private final int TYPE_CRIMINAL_NEWS_ITEM = 1;

    public NewsRecyclerAdapter(@NonNull Context context,
                               @Nullable OnItemClickListener clickListener) {

        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;

        RequestOptions imgeOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background)
                .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imgeOptions);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_CRIMINAL_NEWS_ITEM) {
            view = inflater.inflate(R.layout.item_news_criminal,parent,false);
        } else {
            view = inflater.inflate(R.layout.item_news,parent,false);
        }
        return new ViewHolder(view,clickListener);
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getCategory().getName().equals("Criminal")) {
            return TYPE_CRIMINAL_NEWS_ITEM;
        } else {
            return TYPE_SIMPLE_NEWS_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull NewsItem newsItem);
    }

    public void replaceItems (@NonNull List<NewsItem> newsItems) {
        items.clear();
        items.addAll(newsItems);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryView;
        private final ImageView imageView;
        private final TextView titleView;
        private final TextView previewTextView;
        private final TextView dateView;

         ViewHolder(@NonNull View itemView, @Nullable final OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(items.get(position));
                    }
                }
            });
            categoryView = itemView.findViewById(R.id.category);
            imageView = itemView.findViewById(R.id.image);
            titleView = itemView.findViewById(R.id.title);
            previewTextView = itemView.findViewById(R.id.preview_text);
            dateView = itemView.findViewById(R.id.date);
        }

        void bind(NewsItem news) {
            Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            categoryView.setText(news.getCategory().getName());
            imageLoader.load(news.getImageUrl()).into(imageView);
            titleView.setText(news.getTitle());
            previewTextView.setText(news.getPreviewText());
            dateView.setText(formatter.format(news.getPublishDate()));
        }
    }


}
