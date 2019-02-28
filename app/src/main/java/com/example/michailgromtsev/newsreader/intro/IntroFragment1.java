package com.example.michailgromtsev.newsreader.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.news.NewsListActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroFragment1 extends Fragment {

    private  static final int LAYOUT = R.layout.fragment_intro1;

    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        button = view.findViewById(R.id.bt_close_intro);
        button.setOnClickListener(view1 ->   startActivity(new Intent(getActivity(), NewsListActivity.class)));
        return view;
    }
}
