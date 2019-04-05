package com.example.michailgromtsev.newsreader.database;

import android.content.Context;
import android.util.Log;

import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NewsConverter {

    private Context context;

   public NewsConverter(Context context){
       this.context = context;
   }


    public void toDatabase  (List<NewsItem> newsItems){
       ArrayList<NewsEntity> newsEntitiesList = new ArrayList<>(newsItems.size());

        for(NewsItem item: newsItems) {

            String title = item.getTitle();
            String imageUrl = item.getImageUrl();
            String category = item.getCategory();
            long publishDate =  DateConverter.dateToTimestamp(item.getPublishDate());
            String previewText = item.getPreviewText();
            String url = item.getUrl();
            String id = title.concat(url);

            NewsEntity newsEntity = new NewsEntity(id,title,imageUrl,category,publishDate,previewText,url);
            newsEntitiesList.add(newsEntity);
        }
       NewsEntity[] entities =  newsEntitiesList.toArray(new NewsEntity[newsEntitiesList.size()]);
        AppDatabase db = AppDatabase.getAppDatavese(context);

        db.newsDao().insertAll(entities);
    }



    public static List<NewsItem> fromDatabase(List<NewsEntity> newsEntities){



        List<NewsItem> newsItemList = new ArrayList<>(newsEntities.size());

        for (NewsEntity entity : newsEntities) {
            String title = entity.getTitle();
            String imageUrl = entity.getImageUrl();
            String category = entity.getCategory();

            Date publishDate = DateConverter.fromTimestamp(entity.getPublishDate());
            String previewText = entity.getPreviewText();
            String url = entity.getUrl();

            NewsItem newsItem = NewsItem.create(title,imageUrl,category,publishDate,previewText,url);
            newsItemList.add(newsItem);
        }
        return newsItemList;
    }
}
