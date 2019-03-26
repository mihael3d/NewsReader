package com.example.michailgromtsev.newsreader.intro;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;


import android.content.Intent;
;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.news.NewsListFragment;
import com.example.michailgromtsev.newsreader.storage.Storage;


public class IntroActivity extends AppCompatActivity {

    private FragmentPagerAdapter adapterViewPajer;
    private ViewPager vpIntroPajer;
    private TextView tvWelcome;
    private CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        if (!Storage.needToShowIntro(this)) {
            startSecondActivity();
        }
        setupUi();
        setupUx();

    }

    private void setupUi() {
        vpIntroPajer = findViewById(R.id.view_pager);
        indicator = findViewById(R.id.indicator);
        tvWelcome = findViewById(R.id.tv_welcome);
    }

    private void setupUx() {
        adapterViewPajer = new IntroPagerAdapter(getSupportFragmentManager());
        vpIntroPajer.setAdapter(adapterViewPajer);
        vpIntroPajer.setPageTransformer(false, new ViewPagerTransformer());

        indicator.setViewPager(vpIntroPajer);

        tvWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSecondActivity();
            }
        });
    }

    private void startSecondActivity() {
        Storage.setToShowIntro(this,false);
        startActivity(new Intent(this, NewsListFragment.class));
        finish();
    }

}
