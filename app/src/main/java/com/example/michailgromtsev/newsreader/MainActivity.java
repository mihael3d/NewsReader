package com.example.michailgromtsev.newsreader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.michailgromtsev.newsreader.About.AboutActivity;
import com.example.michailgromtsev.newsreader.details.NewsDetailsFragment;
import com.example.michailgromtsev.newsreader.news.NewsListFragment;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;

public class MainActivity extends AppCompatActivity implements NewsListFragment.NewsListFragmentListener {
    @Nullable
    private Toolbar toolbar;
    private final String TAG_NEWS_LIST_FRAGMENT = "newsListFragment";
    private final String TAG_DITALE_NEWS_FRAGMENT_ = "newsDetailFragment";
    private final String TAD_TRANSACTION_DITALE_NEWS = "newsDetailTransaction";

    private boolean isTwoPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setupToolbar();
        isTwoPanel = findViewById(R.id.frame_ditail) != null;
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_list,new NewsListFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onNextMessageClicked(NewsItem newsItem) {
        NewsDetailsFragment newsDetailsFragment = NewsDetailsFragment.newIstance(newsItem);
        int frameId = isTwoPanel?R.id.frame_ditail:R.id.frame_list;

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(TAD_TRANSACTION_DITALE_NEWS)
                //  .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                .replace(frameId,newsDetailsFragment,TAG_DITALE_NEWS_FRAGMENT_)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_activity:
                startActivity( new Intent(this, AboutActivity.class) );
            default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void setupToolbar() {
     setSupportActionBar(toolbar);
     getSupportActionBar().setDisplayShowTitleEnabled(true);

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        }
        super.onBackPressed();
    }
}
