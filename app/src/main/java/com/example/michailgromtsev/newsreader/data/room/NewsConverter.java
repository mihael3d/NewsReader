package com.example.michailgromtsev.newsreader.data.room;

import android.content.Context;

import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsConverter {

    private Context context;

   public NewsConverter(Context context){
       this.context = context;
   }


    public void toDatabase  (List<NewsItem> newsItems){
       ArrayList<NewsEntity> newsEntitiesList = new ArrayList<>(newsItems.size());

        for(NewsItem item: newsItems) {

            String section = item.getSection();
            String title = item.getTitle();
            String imageUrl = item.getImageUrl();
            String category = item.getCategory();
            long publishDate =  DateConverter.dateToTimestamp(item.getPublishDate());
            String previewText = item.getPreviewText();
            String url = item.getUrl();
            String id = title.concat(url);

            NewsEntity newsEntity = new NewsEntity(id,title,imageUrl,category,publishDate,previewText,url,section);
            newsEntitiesList.add(newsEntity);
        }
       NewsEntity[] entities =  newsEntitiesList.toArray(new NewsEntity[newsEntitiesList.size()]);
        AppDatabase db = AppDatabase.getAppDatavese(context);

        db.newsDao().insertAll(entities);
    }



    public static List<NewsItem> fromDatabase(List<NewsEntity> newsEntities){



        List<NewsItem> newsItemList = new ArrayList<>(newsEntities.size());

        for (NewsEntity entity : newsEntities) {
            String section = entity.getSection();
            String title = entity.getTitle();
            String imageUrl = entity.getImageUrl();
            String category = entity.getCategory();

            Date publishDate = DateConverter.fromTimestamp(entity.getPublishDate());
            String previewText = entity.getPreviewText();
            String url = entity.getUrl();

            NewsItem newsItem = NewsItem.create(section,title,imageUrl,category,publishDate,previewText,url);
            newsItemList.add(newsItem);
        }
        return newsItemList;
    }
}
