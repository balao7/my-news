package com.eric.mynews.repositories;

import android.support.annotation.NonNull;

import com.eric.mynews.models.ForecastResponse;
import com.eric.mynews.models.NewsResponse;
import com.eric.mynews.rest.NewsApi;

import io.reactivex.Single;

public class NewsRepositoryImpl implements NewsRepository {
    private final NewsApi api;

    public NewsRepositoryImpl(@NonNull NewsApi api) {
        this.api = api;
    }

    @Override
    public Single<NewsResponse> getNews() {
        return api.getNews();
    }
}
