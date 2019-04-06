package com.example.michailgromtsev.newsreader.news;

import com.example.michailgromtsev.newsreader.news.adapter.recycler.NewsItem;
import com.example.michailgromtsev.newsreader.data.network.models.dto.MultimediaDTO;
import com.example.michailgromtsev.newsreader.data.network.models.dto.NewsDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TopStoriesMapper {
    private static final String MULTIMEDIA_TYPE_IMAGE = "image";

    public static List<NewsItem> map(@NonNull List<NewsDTO> dtos) {
        final List<NewsItem> items = new ArrayList<>();

        for (NewsDTO dto : dtos) {
            final NewsItem newsItem = mapItem(dto);
            items.add(newsItem);
        }
        return Collections.unmodifiableList(items);
    }

    private static NewsItem mapItem(@NonNull NewsDTO dto) {
        final String section = dto.getSection();
        final String title = dto.getTitle();
        final List<MultimediaDTO> multimedia = dto.getMultimedia();

        final String imageUrl = mapImage( multimedia);

        final String catrgory = dto.getSubsection();
        final Date date = dto.getPublishDate();
        final String previewText = dto.getAbstractDescription();
        final String url = dto.getUrl();

        return NewsItem.create(section,title,imageUrl,catrgory,date,previewText,url);

    }

    @Nullable
    private static String mapImage(@Nullable List<MultimediaDTO> multimedias) {
        if (multimedias == null || multimedias.isEmpty()) {
            return null;
        }

        final int imageMaximumQualityIndex = multimedias.size() - 1;
        final MultimediaDTO multimedia = multimedias.get(imageMaximumQualityIndex);

        if (!multimedia.getType().equals(MULTIMEDIA_TYPE_IMAGE)) {
            return null;
        }

        return multimedia.getUrl();
    }
}
