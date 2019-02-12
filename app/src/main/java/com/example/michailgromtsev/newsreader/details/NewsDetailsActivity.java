package com.example.michailgromtsev.newsreader.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;
import com.example.michailgromtsev.newsreader.utils.Utils;

public class NewsDetailsActivity extends AppCompatActivity {

    private  static final String EXTRA_NEWS_ITEM = "exta:newsItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        final NewsItem newsItem = (NewsItem) getIntent().getSerializableExtra(EXTRA_NEWS_ITEM);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(newsItem.getTitle());
        }

//       final ImageView imageView = findViewById(R.id.iv_image);;
//       final TextView titleView = findViewById(R.id.tv_title);
//       final TextView dateView = findViewById(R.id.tv_date);
       final WebView fultext = findViewById(R.id.wv_fultext);
        fultext.loadUrl(newsItem.getUrl());

//       Glide.with(this).load(newsItem.getImageUrl()).into(imageView);
//       titleView.setText(newsItem.getTitle());
      // fullTextView.setText(newsItem.getFullText());
//       dateView.setText(Utils.formateDateTime(this,newsItem.getPublishDate()));




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(@NonNull Context context, @NonNull NewsItem newsItem) {
        context.startActivity(new Intent(context, NewsDetailsActivity.class)
                .putExtra(EXTRA_NEWS_ITEM, newsItem)
        );
    }

}
