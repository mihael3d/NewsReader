package com.example.michailgromtsev.newsreader.data.network.models.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopStoriesResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private List <NewsDTO> news;

    public String getStatus() {
        return status;
    }

    public List<NewsDTO> getNews() {
        return news;
    }
}
