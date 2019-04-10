package com.example.michailgromtsev.newsreader.Background;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.michailgromtsev.newsreader.R;
import com.example.michailgromtsev.newsreader.data.network.RestApi;
import com.example.michailgromtsev.newsreader.data.room.NewsConverter;
import com.example.michailgromtsev.newsreader.news.TopStoriesMapper;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ServiceNewsDownloading extends Service {

    private Disposable downloadDisposable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Intent deleteIntent = new Intent(this, ServiceNewsDownloading.class);
        deleteIntent.putExtra("close",true);
        PendingIntent deletePendingIntent = PendingIntent.getService(this, 0, deleteIntent, 0);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("NY Times")
                .setContentText("News updating...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .addAction(R.mipmap.ic_launcher, "Delete", deletePendingIntent)
                .setAutoCancel(true)
               // .setCategory(Notification.CATEGORY_STATUS)
               // .setVisibility(Notification.VISIBILITY_PUBLIC)
                .build();
        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        notificationManager.notify(92,notification);
        startForeground(92,notification);



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        boolean s = intent.getBooleanExtra("close",false);
        if (s ){
            stopSelf();
        }
        downloadDisposable = RestApi.getInstance()
                .topStories()
                .get2("home")
                .map(response-> TopStoriesMapper.map(response.getNews()))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(this::loadItems,this::handleError);

        String ns =  Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(92);
        nMgr.cancelAll();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if(downloadDisposable != null && !downloadDisposable.isDisposed()){
            downloadDisposable.dispose();
        }
    }

    private void loadItems(List<NewsItem> newsItems) {
        NewsConverter newsConverter = new NewsConverter(getApplicationContext());
        newsConverter.toDatabase(newsItems);

        Intent deleteIntent = new Intent(this, ServiceNewsDownloading.class);
        deleteIntent.putExtra("close",true);
        PendingIntent deletePendingIntent = PendingIntent.getService(this, 0, deleteIntent, 0);

        Notification notificationNewsDowloadComplite = new Notification.Builder(getApplicationContext())
                .setContentTitle("NY Times")
                .setContentText("News updated")
                .setSmallIcon(R.mipmap.ic_launcher)
                 .setAutoCancel(true)
                .setContentIntent(deletePendingIntent)
                // .setCategory(Notification.CATEGORY_STATUS)
                // .setVisibility(Notification.VISIBILITY_PUBLIC)
                .build();
        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(92,notificationNewsDowloadComplite);

        notificationManager.cancel(92);
//        notificationManager.cancelAll();
    }

    private void handleError (Throwable th) {
        Log.e("ServiceNewsDownloading", th.getMessage(), th);
    }
}
