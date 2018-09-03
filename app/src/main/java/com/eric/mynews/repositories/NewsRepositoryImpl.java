package com.eric.mynews.repositories;

import android.support.annotation.NonNull;

import com.eric.mynews.models.Article;
import com.eric.mynews.models.NewsResponse;
import com.eric.mynews.rest.NewsApi;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class NewsRepositoryImpl implements NewsRepository {
    private final NewsApi api;

    public NewsRepositoryImpl(@NonNull NewsApi api) {
        this.api = api;
    }

    @Override
    public Single<List<Article>> getNews() {
        return api.getNews()
                .map(NewsResponse::getArticles);
    }
}
