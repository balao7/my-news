package com.eric.mynews.models;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class NewsResponse {

    @Json(name = "status") private String status;
    @Json(name = "totalResults") private Integer totalResults;
    @Json(name = "articles") private List<Article> articles = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
