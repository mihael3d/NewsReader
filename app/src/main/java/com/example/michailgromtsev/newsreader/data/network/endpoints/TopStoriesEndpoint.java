package com.example.michailgromtsev.newsreader.data.network.endpoints;

import com.example.michailgromtsev.newsreader.data.network.models.dto.TopStoriesResponse;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TopStoriesEndpoint {
    @NonNull
    @GET("topstories/v2/{section}.json")
    Single<TopStoriesResponse> get (@Path("section")@NonNull String section);

    @NonNull
    @GET("topstories/v2/{section}.json?api-key=an9fUe4qX1YdwGElFkY9hMXzK1wYWAdO")
    Single<TopStoriesResponse> get2 (@Path("section")@NonNull String section);
}
