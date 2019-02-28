package com.example.michailgromtsev.newsreader.news;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.example.michailgromtsev.newsreader.data.database.AppDatabase;
import com.example.michailgromtsev.newsreader.data.database.ConvectorFromNetvorkToDatabase;
import com.example.michailgromtsev.newsreader.data.database.NewsEntity;
import com.example.michailgromtsev.newsreader.data.network.models.NewsCategory;
import com.example.michailgromtsev.newsreader.data.network.models.dto.NewsDTO;
import com.example.michailgromtsev.newsreader.details.NewsDetailsActivity;
import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;
import com.example.michailgromtsev.newsreader.data.network.RestApi;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItemDecoration;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsRecyclerAdapter;
import com.example.michailgromtsev.newsreader.news.adapter.spinner.CategorySpinerAdapter;
import com.example.michailgromtsev.newsreader.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class NewsListActivity extends AppCompatActivity {

    private  static final int LAYOUT = R.layout.activity_news_list;

    @Nullable
    private ProgressBar progress;
    @Nullable
    private RecyclerView recycler;
    @Nullable
    private Toolbar toolbar;
    @Nullable
    private Spinner spinnerCategories;
    @Nullable
    private FloatingActionButton floatingActionButton;

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private CategorySpinerAdapter categoriesAdapter;
    private  AppDatabase database;


    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        setupUi();
        setupUx();

    }

    private void setupUi() {
        findViews();
       setupToolbar();

        setupSpiner();
        setupRecyclerViewAdapter();

    }

    private void setupToolbar() {
     setSupportActionBar(toolbar);
     getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void setupSpiner() {
        final NewsCategory[] categories = NewsCategory.values();
        categoriesAdapter = CategorySpinerAdapter.createDefault(this,categories);
        spinnerCategories.setAdapter(categoriesAdapter);
    }

    private void setupRecyclerViewAdapter(){
        newsRecyclerAdapter = new NewsRecyclerAdapter(this);
        recycler.setAdapter(newsRecyclerAdapter);
        recycler.addItemDecoration(new NewsItemDecoration(getResources().getDimensionPixelOffset(R.dimen.spacing_micro)));
        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            final int columeCounts = getResources().getInteger(R.integer.landscape_news_colums_count);
            recycler.setLayoutManager(new GridLayoutManager(this,columeCounts));
        } else {
            recycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void setupUx() {
        newsRecyclerAdapter.setOnClickListner(newsItem -> NewsDetailsActivity.start(this,newsItem));
        //categoriesAdapter.setOnCategorySelectListner(category -> loadItems(category.serverValue()),spinnerCategories);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = categoriesAdapter.getSelectedCategory().serverValue();
                loadItems(category);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    private void loadItems(@NonNull String category) {
        shoProgress();
        final Disposable disposable = RestApi.getInstance()
                .topStories()

                .get2(category)
                .map(response->response.getNews())
               // .map(response->TopStoriesMapper.map(response.getNews()))
                .subscribeOn(Schedulers.io())
                //.observeOn(Shedul)
                .subscribe(this::saveLoadedData,this::handleError);
               // .subscribe(this::updateItems,this::handleError);
       compositeDisposable.add(disposable);
    }

    private void saveLoadedData (List<NewsDTO> newsDTOS) {
        database = AppDatabase.getInstance(this);
//        ArrayList<NewsEntity> entities = new ArrayList<>();

        for (NewsDTO newsDTO : newsDTOS) {
            NewsEntity entity = ConvectorFromNetvorkToDatabase.toDatabase(newsDTO);
//            entities.add(entity);
            database.newsDao().insert(entity);
        }

    }

    private void updateItems(List<NewsItem> newsItems) {
        if (newsRecyclerAdapter != null) newsRecyclerAdapter.replaceItems(newsItems);
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

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    private void shoProgress() {
        progress.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsRecyclerAdapter = null;
        recycler = null;
        progress = null;
    }

    private void findViews() {
        progress = findViewById(R.id.progres);
        recycler = findViewById(R.id.recycler);
        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        spinnerCategories = findViewById(R.id.spinner_categories);
    }
}
