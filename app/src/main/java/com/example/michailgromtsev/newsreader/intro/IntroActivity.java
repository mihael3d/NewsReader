package com.example.michailgromtsev.newsreader.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.details.NewsDetailsActivity;
import com.example.michailgromtsev.newsreader.news.NewsListActivity;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IntroActivity extends AppCompatActivity {

    private  static final int LAYOUT = R.layout.activity_intro;

    private boolean hasShowed;
    private SharedPreferences activityPreferences;
    private static final String MY_SETTINGS = "my_settings";
    private static final String PREFERENCES_SHOWING_STATE = "show_state";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ViewPager viewPager;
    private IntroFragmentPagerAdepter vpAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hasShowed = getshowedState();

        if (!hasShowed) {
            setContentView(LAYOUT);
            Disposable disposable = Completable.complete()
                    .delay(15, TimeUnit.SECONDS)
                    .subscribe(this::stratSecondActivity);
            compositeDisposable.add(disposable);
            saveShowingState(hasShowed);
        }
        else {
            saveShowingState(hasShowed);
            stratSecondActivity();
        }
        viewPager = findViewById(R.id.vp_intro);
        vpAdapter = new IntroFragmentPagerAdepter(getSupportFragmentManager());
        viewPager.setAdapter(vpAdapter);
    }

    private void stratSecondActivity(){
            startActivity(new Intent(this, NewsListActivity.class));
    }



    private void saveShowingState(boolean shoewstate) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREFERENCES_SHOWING_STATE,!shoewstate);
        editor.commit();
    }

    private boolean getshowedState() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
       return preferences.getBoolean(PREFERENCES_SHOWING_STATE, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }
}
