package com.example.michailgromtsev.newsreader.data.database;

import com.example.michailgromtsev.newsreader.data.network.models.dto.MultimediaDTO;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class NewsEntity {
    @PrimaryKey
    @NonNull
    public String  id;

    @ColumnInfo(name = "subsection")
    public String subsection;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "abstract")
    private String abstractDescription;

    @ColumnInfo(name = "published_date")
    @TypeConverters(DateConverter.class)
    private Date publishDate;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractDescription() {
        return abstractDescription;
    }

    public void setAbstractDescription(String abstractDescription) {
        this.abstractDescription = abstractDescription;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
