package com.eric.mynews.commands;

import android.app.Activity;

import com.eric.mynews.article.ArticleDetailsActivity;
import com.eric.mynews.models.Article;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class NewsDetailsCmdImpl implements NewsDetailsCommand {
    private final Activity activity;

    public NewsDetailsCmdImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void gotoDetails(Article news) {
        activity.startActivity(ArticleDetailsActivity.newIntent(activity, news));
    }
}
