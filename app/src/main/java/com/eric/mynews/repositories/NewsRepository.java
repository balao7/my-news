package com.eric.mynews.repositories;

import com.eric.mynews.models.Article;
import com.eric.mynews.models.NewsResponse;

import java.util.List;

import io.reactivex.Single;

public interface NewsRepository {
    Single<List<Article>> getNews();
}