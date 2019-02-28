package com.example.michailgromtsev.newsreader.intro;

import android.os.Bundle;

import com.example.michailgromtsev.newsreader.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    private  static final int LAYOUT = R.layout.activity_intro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
    }
}
