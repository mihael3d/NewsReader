package com.example.michailgromtsev.newsreader.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;

public class NewsItem implements Serializable {
    @NonNull
    private final String title;
    @NonNull
    private final String imageUrl;
    @NonNull
    private final Category category;
    @NonNull
    private final Date publishDate;
    @NonNull
    private final String previewText;
    @NonNull
    private final String fullText;


    public NewsItem(String title, String imageUrl, Category category, Date publishDate, String previewText, String fullText) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.publishDate = publishDate;
        this.previewText = previewText;
        this.fullText = fullText;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getPreviewText() {
        return previewText;
    }

    public String getFullText() {
        return fullText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem newsItem = (NewsItem) o;
        return Objects.equals(title, newsItem.title) &&
                Objects.equals(imageUrl, newsItem.imageUrl) &&
                Objects.equals(category, newsItem.category) &&
                Objects.equals(publishDate, newsItem.publishDate) &&
                Objects.equals(previewText, newsItem.previewText) &&
                Objects.equals(fullText, newsItem.fullText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, imageUrl, category, publishDate, previewText, fullText);
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category=" + category +
                ", publishDate=" + publishDate +
                ", previewText='" + previewText + '\'' +
                ", fullText='" + fullText + '\'' +
                '}';
    }

}

