package com.example.michailgromtsev.newsreader.details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;

public class NewsDetailsFragment extends Fragment {

    private  static final int LAYOUT = R.layout.fragment_news_details;
    private  static final String EXTRA_NEWS_ITEM = "exta:newsItem";
    private static final String DESCRIBABLE_KEY_NEWS_ITEM = "newsItem";
    private NewsItem newsItem;
    private WebView fultext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT,container,false);
        fultext = view.findViewById(R.id.wv_fultext);
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(DESCRIBABLE_KEY_NEWS_ITEM)) {
            newsItem = (NewsItem) arguments.getSerializable(DESCRIBABLE_KEY_NEWS_ITEM);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(newsItem.getTitle());
        }
        fultext.loadUrl(newsItem.getUrl());
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static NewsDetailsFragment newIstance(@NonNull NewsItem newsItem) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY_NEWS_ITEM,newsItem);
        fragment.setArguments(bundle);

        return fragment;
    }

}
