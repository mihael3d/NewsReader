package com.example.michailgromtsev.newsreader.data.network.models.dto;


import com.google.gson.annotations.SerializedName;

public class MultimediaDTO {

    @SerializedName("type")
    private String type;

    @SerializedName("url")
    private String url;


    public String getType() {
        return type;
    }



    public String getUrl() {
        return url;
    }


}
