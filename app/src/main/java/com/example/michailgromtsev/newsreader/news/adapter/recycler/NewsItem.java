package com.example.michailgromtsev.newsreader.news.adapter.recycler;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsItem implements Serializable {
    @NonNull
    private final String section;
    @NonNull
    private final String title;
    @NonNull
    private final String imageUrl;
    @NonNull
    private final String category;
    @NonNull
    private final Date publishDate;
    @NonNull
    private final String previewText;
    @NonNull
    private final String url;


    public static NewsItem create( @NonNull String section,
                            @NonNull String title,
                            @Nullable String imageUrl,
                            @NonNull String category,
                            @NonNull Date publishDate,
                            @NonNull String previewText,
                            @NonNull String url) {
        return new NewsItem(section, title, imageUrl, category, publishDate, previewText, url);
    }

    private NewsItem(String section,String title, String imageUrl, String category, Date publishDate, String previewText, String url) {
        this.section = section;
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.publishDate = publishDate;
        this.previewText = previewText;
        this.url = url;
    }

    public  String getSection() {
        return  section;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getPreviewText() {
        return previewText;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem newsItem = (NewsItem) o;
        return  Objects.equals(section, newsItem.section) &&
                Objects.equals(title, newsItem.title) &&
                Objects.equals(imageUrl, newsItem.imageUrl) &&
                Objects.equals(category, newsItem.category) &&
                Objects.equals(publishDate, newsItem.publishDate) &&
                Objects.equals(previewText, newsItem.previewText) &&
                Objects.equals(url, newsItem.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(section, title, imageUrl, category, publishDate, previewText, url);
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "section='" + section + '\'' +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category=" + category +
                ", publishDate=" + publishDate +
                ", previewText='" + previewText + '\'' +
                ", fullText='" + url + '\'' +
                '}';
    }

}

