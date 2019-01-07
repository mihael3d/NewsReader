package com.example.michailgromtsev.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.michailgromtsev.newsreader.data.DataUtils;
import com.example.michailgromtsev.newsreader.data.NewsItem;

import java.text.Format;
import java.text.SimpleDateFormat;

public class NewsDetailsActivity extends AppCompatActivity {
    private ImageView image;
    private TextView title;
    private TextView date;
    private TextView fullText;
    private  RequestManager imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        image = findViewById(R.id.iv_image);
        title = findViewById(R.id.tv_title);
        date = findViewById(R.id.tv_date);
        fullText = findViewById(R.id.tv_full_text);

        RequestOptions imgeOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .fallback(R.drawable.ic_launcher_background);
              //  .centerCrop();
        imageLoader = Glide.with(this).applyDefaultRequestOptions(imgeOptions);



        NewsItem news = DataUtils.generateNews().get(0);

        imageLoader.load(news.getImageUrl()).into(image);

        title.setText(news.getTitle());
        Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        date.setText(formatter.format(news.getPublishDate()));
        fullText.setText(news.getFullText());

        setTitle(news.getCategory().getName());

    }
}
