package com.example.michailgromtsev.newsreader.data.database;

import com.example.michailgromtsev.newsreader.data.network.models.dto.MultimediaDTO;
import com.example.michailgromtsev.newsreader.data.network.models.dto.NewsDTO;
import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;

import java.util.Date;
import java.util.List;

public class ConvectorFromNetvorkToDatabase {

    public static NewsItem fromDatabase(NewsEntity newsEntity) {
        String title = newsEntity.getTitle();
         String imageUrl = newsEntity.getImageUrl();
         String category = newsEntity.getSubsection();
         Date publishDate = newsEntity.getPublishDate();
         String previewText = newsEntity.getAbstractDescription();
         String url = newsEntity.getUrl();

       NewsItem item =  NewsItem.create(title,imageUrl,category,publishDate,previewText,url);
        return item;}

    public static NewsEntity  toDatabase(NewsDTO newsDTO) {
        NewsEntity entity = new NewsEntity();

        String id = newsDTO.getTitle().concat(newsDTO.getUrl());
        entity.setId(id);

        entity.setSubsection(newsDTO.getSubsection());
        entity.setTitle(newsDTO.getTitle());
        entity.setAbstractDescription(newsDTO.getAbstractDescription());
        entity.setPublishDate(newsDTO.getPublishDate());

        List<MultimediaDTO> multimedias = newsDTO.getMultimedia();
        if (multimedias == null || multimedias.isEmpty()) {
            entity.setImageUrl(null);
        } else {
            final int imageMaximumQualityIndex = multimedias.size() - 1;
            final MultimediaDTO multimedia = multimedias.get(imageMaximumQualityIndex);
                if (!multimedia.getType().equals("image")) {
                    entity.setImageUrl(null);
                } else {
                    entity.setImageUrl(multimedia.getUrl());
                }
        }


        return entity;}
}
