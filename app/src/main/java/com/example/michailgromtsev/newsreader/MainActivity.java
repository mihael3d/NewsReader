package com.example.michailgromtsev.newsreader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

import com.example.michailgromtsev.newsreader.details.NewsDetailsFragment;
import com.example.michailgromtsev.newsreader.news.NewsListFragment;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;

public class MainActivity extends AppCompatActivity implements NewsListFragment.NewsListFragmentListener {
    @Nullable
    private Toolbar toolbar;
    private final String TAG_NEWS_LIST_FRAGMENT = "newsListFragment";
    private final String TAG_DITALE_NEWS_FRAGMENT_ = "newsDetailFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setupToolbar();
        setupUx();

    }

    private void setupUx() {
        NewsListFragment newsListFragment = new NewsListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_list,newsListFragment,TAG_NEWS_LIST_FRAGMENT)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

     private void setupToolbar() {
     setSupportActionBar(toolbar);
     getSupportActionBar().setDisplayShowTitleEnabled(true);
         getSupportActionBar().setTitle("222222");
     toolbar.setTitle("bla bla bla");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onNextMessageClicked(NewsItem newsItem) {
        NewsDetailsFragment newsDetailsFragment = NewsDetailsFragment.newIstance(newsItem);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_list,newsDetailsFragment,TAG_DITALE_NEWS_FRAGMENT_)
                .commit();
    }
}
