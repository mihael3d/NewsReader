package com.example.michailgromtsev.newsreader;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.michailgromtsev.newsreader.data.DataUtils;
import com.example.michailgromtsev.newsreader.data.NewsItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class NewsListActivity extends AppCompatActivity {

//    private RecyclerView newsList;
//    private final NewsRecyclerAdapter.OnItemClickListener clickListener = new NewsRecyclerAdapter.OnItemClickListener() {
//        @Override
//        public void onItemClick(NewsItem news) {
//            String clickMassage = news.getTitle();
//            Toast.makeText(getApplicationContext(), clickMassage, Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), NewsDetailsActivity.class);
//            startActivity(intent);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        NewsRecyclerAdapter.OnItemClickListener clickListener = new NewsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull NewsItem newsItem) {
                NewsDetailsActivity.start(getBaseContext(),newsItem);
            }
        };

        final NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(this, new NewsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull NewsItem newsItem) {
                NewsDetailsActivity.start(getBaseContext(),newsItem);
            }
        });

        final RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new NewsItemDecoration(getResources().getDimensionPixelOffset(R.dimen.default_size_padding)));

        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            final int columeCounts = getResources().getInteger(R.integer.landscape_news_colums_count);
            recycler.setLayoutManager(new GridLayoutManager(this,columeCounts));
        } else {
            recycler.setLayoutManager(new LinearLayoutManager(this));
        }
        adapter.replaceItems(DataUtils.generateNews());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
