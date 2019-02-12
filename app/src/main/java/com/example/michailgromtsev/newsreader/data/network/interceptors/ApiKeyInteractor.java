package com.example.michailgromtsev.newsreader.data.network.interceptors;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class ApiKeyInteractor implements Interceptor {

    private static final String TOP_STORYES_API_KEY = "an9fUe4qX1YdwGElFkY9hMXzK1wYWAdO";
    private static final String API_KEY_HEADER_NAME = "api-key";

    public static ApiKeyInteractor create() {
        return new ApiKeyInteractor();
    }


    private ApiKeyInteractor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        request = request.newBuilder()
                .addHeader(API_KEY_HEADER_NAME, TOP_STORYES_API_KEY)
                .build();
        Response response = chain.proceed(request);
        return response;

//        final Request requestWithoutApiKey = chain.request();
//
//        final HttpUrl url = requestWithoutApiKey.url()
//                .newBuilder()
//                .build();
//
//        final Request requestWithAttachedApiKey = requestWithoutApiKey.newBuilder()
//                .url(url)
//                .addHeader(API_KEY_HEADER_NAME, TOP_STORYES_API_KEY)
//                .build();
//
//        return chain.proceed(requestWithAttachedApiKey);
    }
}
