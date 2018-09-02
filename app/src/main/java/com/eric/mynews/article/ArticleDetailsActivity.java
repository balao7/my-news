package com.eric.mynews.article;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eric.mynews.R;
import com.eric.mynews.BR;
import com.eric.mynews.models.Article;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

@ParametersAreNonnullByDefault
public class ArticleDetailsActivity extends DaggerAppCompatActivity {
    static final String ARG_NEWS = "ARG_NEWS";

    @Inject ArticleDetailsViewModel viewModel;

    public static Intent newIntent(Activity activity, Article news) {
        return new Intent(activity, ArticleDetailsActivity.class).putExtra(ARG_NEWS, news);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        ViewDataBinding detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_details);
        detailBinding.setVariable(BR.vm, viewModel);

        viewModel.onActivityCreated(extras);
    }
}
