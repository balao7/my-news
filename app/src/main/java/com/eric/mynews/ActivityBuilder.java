package com.eric.mynews;

import com.eric.mynews.article.ArticleDetailsActivity;
import com.eric.mynews.article.ArticleDetailsActivityModule;
import com.eric.mynews.main.MainActivity;
import com.eric.mynews.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = ArticleDetailsActivityModule.class)
    abstract ArticleDetailsActivity bindArticleDetailsActivity();
}