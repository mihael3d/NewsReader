package com.example.michailgromtsev.newsreader.data.network;

import com.example.michailgromtsev.newsreader.data.network.endpoints.TopStoriesEndpoint;
import com.example.michailgromtsev.newsreader.data.network.interceptors.ApiKeyInteractor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestApi {

    private static RestApi sRestApi;
    private static final String TOP_STORIES_BASE_URL = "https://api.nytimes.com/svc/";
    private TopStoriesEndpoint topStoriesEndpoint;

    public static synchronized RestApi getInstance () {
        if (sRestApi == null) {
            sRestApi = new RestApi();
        }
        return sRestApi;
    }

    private  RestApi () {
        final OkHttpClient client = bildClient();
        final Retrofit retrofit = bildRetrofit(client);

        topStoriesEndpoint = retrofit.create(TopStoriesEndpoint.class);
    }
    private Retrofit bildRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(TOP_STORIES_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient bildClient() {

        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
               .addInterceptor(ApiKeyInteractor.create())

                .build();
    }

    public TopStoriesEndpoint topStories() {
        return topStoriesEndpoint;
    }
}
