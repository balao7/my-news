package com.eric.mynews.repositories;

import com.eric.mynews.models.Article;
import com.eric.mynews.models.NewsResponse;
import com.eric.mynews.rest.NewsApi;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import io.reactivex.Single;
import timber.log.Timber;

@ParametersAreNonnullByDefault
public class NewsRemoteRepositoryImpl implements NewsRepository {
    private final NewsApi api;

    public NewsRemoteRepositoryImpl(NewsApi api) {
        this.api = api;
    }

    @Override
    public Single<List<Article>> getNews() {
        Timber.i("getNews");
        return api.getNews()
                .map(NewsResponse::getArticles);
    }
}
