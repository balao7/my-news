package com.eric.mynews.rest;

import com.eric.mynews.BuildConfig;
import com.eric.mynews.models.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NewsApi {
    @GET("top-headlines?country=us&apiKey=" + BuildConfig.API_KEY)
    Single<NewsResponse> getNews();
}
