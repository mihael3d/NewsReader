package com.example.michailgromtsev.newsreader.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michailgromtsev.newsreader.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroFragment extends Fragment {
    // Store instance variable
    private String title;
    private final static String BUNDLE_ARG_TITLE = "someTitle";
    private TextView tvTitle;


    private static int VIEW = R.layout.fragment_inro;

    public static IntroFragment newInstance(String title) {
        IntroFragment introFragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_ARG_TITLE, title);
        introFragment.setArguments(args);
        return  introFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(BUNDLE_ARG_TITLE, "nothing");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(VIEW,container,false);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);

        return view;
    }
}
