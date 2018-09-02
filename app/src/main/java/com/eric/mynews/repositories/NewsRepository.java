package com.eric.mynews.repositories;

import com.eric.mynews.models.NewsResponse;

import io.reactivex.Single;

public interface NewsRepository {
    Single<NewsResponse> getNews();
}