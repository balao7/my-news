package com.eric.mynews.routers;

import android.app.Activity;

import com.eric.mynews.article.ArticleDetailsActivity;
import com.eric.mynews.models.Article;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class NewsDetailsRouterImpl implements NewsDetailsRouter {
    private final Activity activity;

    public NewsDetailsRouterImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void gotoDetails(Article news) {
        activity.startActivity(ArticleDetailsActivity.newIntent(activity, news));
    }
}
