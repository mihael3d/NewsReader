package com.example.michailgromtsev.newsreader.news.adapter.recycler;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder  extends RecyclerView.ViewHolder {

    private static final int LAYOUT = R.layout.item_news;

    private final RequestManager imageLoader;
    private final View itemView;

    private ImageView imageView;
    private TextView categoryView;
    private TextView titleView;
    private TextView previewView;
    private TextView dateView;

    public static NewsViewHolder create(@NonNull LayoutInflater inflater,
                                        @NonNull ViewGroup paren,
                                        @NonNull RequestManager imageLoader) {
        final View view = inflater.inflate(LAYOUT,paren,false);
        return new NewsViewHolder(view, imageLoader);
    }

     NewsViewHolder(@NonNull View itemView,
                    @NonNull RequestManager imageLoader) {
        super(itemView);
        this.itemView = itemView;
        this.imageLoader = imageLoader;
         findeViews(itemView);
    }

    void bind (@NonNull NewsItem newsItem,
               @NonNull NewsRecyclerAdapter.OnItemClickListener newsClickListener) {
        setupUi(newsItem);
        setupUx(newsItem, newsClickListener);
    }


    private void setupUi(NewsItem newsItem) {
        imageLoader.load(newsItem.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

        categoryView.setText(newsItem.getCategory());
        titleView.setText(newsItem.getTitle());
        previewView.setText(newsItem.getPreviewText());
        dateView.setText(Utils.formateDateTime(itemView.getContext(),newsItem.getPublishDate()));
    }

    private void setupUx(@NonNull NewsItem newsItem,
                         @Nullable NewsRecyclerAdapter.OnItemClickListener newsClickListner) {
        itemView.setOnClickListener(view -> {
            int position = getAdapterPosition();
            if (newsClickListner != null && position != RecyclerView.NO_POSITION) {
                newsClickListner.onItemClick(newsItem);
            }
        });

    }

    private void findeViews (@NonNull View itemView) {
        categoryView = itemView.findViewById(R.id.category);
        imageView = itemView.findViewById(R.id.image);
        titleView = itemView.findViewById(R.id.title);
        previewView = itemView.findViewById(R.id.preview_text);
        dateView = itemView.findViewById(R.id.date);
    }
}
