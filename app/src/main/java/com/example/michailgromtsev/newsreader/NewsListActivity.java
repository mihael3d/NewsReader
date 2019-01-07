package com.example.michailgromtsev.newsreader;


import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.michailgromtsev.newsreader.data.DataUtils;
import com.example.michailgromtsev.newsreader.data.NewsItem;
import com.example.michailgromtsev.newsreader.data.NewsRecyclerAdapter;
import com.example.michailgromtsev.newsreader.data.VerticalItemDecoration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListActivity extends AppCompatActivity {
    private RecyclerView newsList;
    private final NewsRecyclerAdapter.OnItemClickListener clickListener = new NewsRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(NewsItem news) {
            String clickMassage = news.getTitle();
            Toast.makeText(getApplicationContext(), clickMassage, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), NewsDetailsActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);


        newsList = findViewById(R.id.recycler);


        newsList.setAdapter(new NewsRecyclerAdapter(this,DataUtils.generateNews(),clickListener));

        newsList.addItemDecoration(new VerticalItemDecoration(20,false));
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();

        if (orientation == Surface.ROTATION_90
                || orientation == Surface.ROTATION_270) {
            newsList.setLayoutManager(new GridLayoutManager(this,2));
        } else { newsList.setLayoutManager(new LinearLayoutManager(this));}

    }
}
