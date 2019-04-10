package com.example.michailgromtsev.newsreader.news;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.michailgromtsev.newsreader.Background.ServiceNewsDownloading;
import com.example.michailgromtsev.newsreader.data.network.models.NewsCategory;
import com.example.michailgromtsev.newsreader.data.room.AppDatabase;
import com.example.michailgromtsev.newsreader.data.room.NewsConverter;
import com.example.michailgromtsev.newsreader.data.room.NewsEntity;
import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;
import com.example.michailgromtsev.newsreader.data.network.RestApi;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItemDecoration;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsRecyclerAdapter;
import com.example.michailgromtsev.newsreader.news.adapter.spinner.CategorySpinerAdapter;
import com.example.michailgromtsev.newsreader.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListFragment extends Fragment {

    private  static final int LAYOUT = R.layout.fragment_news_list;
    private static final String TAG = NewsListFragment.class.getSimpleName();

    private int checkNewsCategoryIndex = -1;

    @Nullable
    private ProgressBar progress;
    @Nullable
    private RecyclerView recycler;
    @Nullable
    private Spinner spinnerCategories;
    @Nullable
    private FloatingActionButton floatingActionButton;

    private NewsRecyclerAdapter newsRecyclerAdapter;
    private CategorySpinerAdapter categoriesAdapter;

    private NewsListFragmentListener listner;
    private Disposable disposable;
    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT, container, false);
        setupUi(view);
        setupUx();

        return view;
    }

     @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof NewsListFragmentListener ) {
            listner = (NewsListFragmentListener) getActivity();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(categoriesAdapter.getSelectedCategory().displayValue());
    }

    @Override
    public void onDetach() {
        listner = null;
        super.onDetach();
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


//        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
//            final int columeCounts = getResources().getInteger(R.integer.landscape_news_colums_count);
//            recycler.setLayoutManager(new GridLayoutManager(getActivity(),columeCounts));
//        } else {
//            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
//        }
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupUx() {
        newsRecyclerAdapter.setOnClickListner(newsItem -> listner.onNextMessageClicked(newsItem));
        categoriesAdapter.setOnCategorySelectListner(category -> {loadItems(category.serverValue());
          ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(category.displayValue());
            },spinnerCategories);
        floatingActionButton.setOnClickListener(view -> loadNewsItemsFromRoom( getString(
               ((CategorySpinerAdapter)spinnerCategories.getAdapter()).getSelectedCategory().displayValue()
                ))
        );
        startNewsDownloadingServise();
    }

    private void startNewsDownloadingServise() {
        Intent intent = new Intent(getActivity(), ServiceNewsDownloading.class);

        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O) {
            getActivity().startForegroundService(intent);
        } else {
            getActivity().startService(intent);
        }
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
      saveNewsItemsToRoom(newsItems);
        if (newsRecyclerAdapter != null) newsRecyclerAdapter.replaceItems(newsItems);

        progress.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
        recycler.setEnabled(true);

    }

    private void handleError(Throwable th) {
        if (Utils.isDebug()) {
            Log.e("TAG", th.getMessage(), th);
        }
        progress.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
    }

    private void saveNewsItemsToRoom(List<NewsItem> newsItems) {
        NewsConverter newsConverter = new NewsConverter(getContext());
        newsConverter.toDatabase(newsItems);
    }

    private void loadNewsItemsFromRoom(String selection) {
        AppDatabase db = AppDatabase.getAppDatavese(getContext());
        Observable<List<NewsEntity>> getAllNewsEntities = db.newsDao().getAllBySection(selection);
         disposable =  getAllNewsEntities
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadItems,this::handleError);
        //compositeDisposable.add(disposable);
    }

    private void loadItems(List<NewsEntity> newsEntetis) {
        List<NewsItem> newsItems = NewsConverter.fromDatabase(newsEntetis);
        if (newsRecyclerAdapter != null) newsRecyclerAdapter.replaceItems(newsItems);
        disposable.dispose();
        recycler.setVisibility(View.VISIBLE);

    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    private void shoProgress() {
        progress.setVisibility(View.VISIBLE);
      //  recycler.setVisibility(View.GONE);
        recycler.setEnabled(false);

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
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
    }

    public interface NewsListFragmentListener {
        void onNextMessageClicked(NewsItem newsItem);
    }

}
