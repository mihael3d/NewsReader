package com.example.michailgromtsev.newsreader;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.example.michailgromtsev.newsreader.data.DataUtils;
import com.example.michailgromtsev.newsreader.data.NewsItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class NewsListActivity extends AppCompatActivity {

    private List<NewsItem> newsItems;
    private NewsRecyclerAdapter adapter;
    private RecyclerView recycler;
    private ProgressBar progress;

    @NonNull private Disposable disposable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        progress = findViewById(R.id.progres);
        recycler = findViewById(R.id.recycler);


        adapter = new NewsRecyclerAdapter(this, newsItem -> NewsDetailsActivity.start(this,newsItem));


        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new NewsItemDecoration(getResources().getDimensionPixelOffset(R.dimen.spacing_micro)));

        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            final int columeCounts = getResources().getInteger(R.integer.landscape_news_colums_count);
            recycler.setLayoutManager(new GridLayoutManager(this,columeCounts));
        } else {
            recycler.setLayoutManager(new LinearLayoutManager(this));
        }



        loadItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void loadItems() {
        shoProgress();
        disposable = DataUtils.observeNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateItems,this::handleError);
    }

    private void updateItems(@Nullable List<NewsItem> news) {
        if (adapter != null) adapter.replaceItems(news);

        progress.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }

    private void handleError(Throwable th) {
        if (Utils.isDebug()) {
            Log.e("TAG", th.getMessage(), th);
        }
        progress.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
    }

    private void shoProgress() {
        progress.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

}
