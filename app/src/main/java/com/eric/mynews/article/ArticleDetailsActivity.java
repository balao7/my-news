package com.eric.mynews.article;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eric.mynews.R;
import com.eric.mynews.models.Article;

public class ArticleDetailsActivity extends AppCompatActivity {
    private static final String ARG_NEWS = "ARG_NEWS";

    public static Intent newIntent(Activity activity, Article news) {
        return new Intent(activity, ArticleDetailsActivity.class).putExtra(ARG_NEWS, news);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
    }
}
