package com.example.michailgromtsev.newsreader.news;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.michailgromtsev.newsreader.data.network.models.NewsCategory;
import com.example.michailgromtsev.newsreader.details.NewsDetailsFragment;
import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;
import com.example.michailgromtsev.newsreader.data.network.RestApi;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItemDecoration;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsRecyclerAdapter;
import com.example.michailgromtsev.newsreader.news.adapter.spinner.CategorySpinerAdapter;
import com.example.michailgromtsev.newsreader.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class NewsListFragments extends Fragment {

    private  static final int LAYOUT = R.layout.fragment_news_list;
    private static final String TAG = NewsListFragments.class.getSimpleName();
    private int checkNewsCategoryIndex = -1;

    @Nullable
    private ProgressBar progress;
    @Nullable
    private RecyclerView recycler;
    @Nullable
    private Spinner spinnerCategories;

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private CategorySpinerAdapter categoriesAdapter;

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT, container, false);
        setupUi(view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUx();
    }

    private void setupUi(View view) {
        findViews(view);
        setupSpiner();
        setupRecyclerViewAdapter();

    }

    private void setupSpiner() {
        final NewsCategory[] categories = NewsCategory.values();
        categoriesAdapter = CategorySpinerAdapter.createDefault(getActivity(),categories);
        spinnerCategories.setAdapter(categoriesAdapter);
    }

    private void setupRecyclerViewAdapter(){
        newsRecyclerAdapter = new NewsRecyclerAdapter(getActivity());
        recycler.setAdapter(newsRecyclerAdapter);
        recycler.addItemDecoration(new NewsItemDecoration(getResources().getDimensionPixelOffset(R.dimen.spacing_micro)));
        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            final int columeCounts = getResources().getInteger(R.integer.landscape_news_colums_count);
            recycler.setLayoutManager(new GridLayoutManager(getActivity(),columeCounts));
        } else {
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private void setupUx() {
        newsRecyclerAdapter.setOnClickListner(newsItem -> NewsDetailsFragment.start(getActivity(),newsItem));
        categoriesAdapter.setOnCategorySelectListner(category -> loadItems(category.serverValue()),spinnerCategories);
    }

    private void loadItems(@NonNull String category) {
        shoProgress();
       final Disposable disposable = RestApi.getInstance()
                .topStories()
                .get2(category)
                .map(response->TopStoriesMapper.map(response.getNews()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateItems,this::handleError);
       compositeDisposable.add(disposable);
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
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    private void shoProgress() {
        progress.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsRecyclerAdapter = null;
        recycler = null;
        progress = null;
    }

    private void findViews(View view) {
        progress = view.findViewById(R.id.progres);
        recycler = view.findViewById(R.id.recycler);
        spinnerCategories = view.findViewById(R.id.spinner_categories);
    }
}
