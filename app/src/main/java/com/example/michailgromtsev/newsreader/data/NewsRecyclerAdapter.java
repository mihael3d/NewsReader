package com.example.michailgromtsev.newsreader.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.michailgromtsev.newsreader.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    @NonNull
    private final List<NewsItem> news;
    @NonNull
    private final LayoutInflater inflater;
    @NonNull
    private final RequestManager imageLoader;
    @Nullable
    private final OnItemClickListener clickListener;

    public NewsRecyclerAdapter(@NonNull Context context, @NonNull List<NewsItem> news,
                               @Nullable OnItemClickListener clickListener) {
        this.news = news;
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
        return new ViewHolder(inflater.inflate(R.layout.item_news,parent,false),clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(news.get(position));
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem news);
    }

    @Override
    public int getItemCount() {
        return news.size();
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
                        listener.onItemClick(news.get(position));
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
