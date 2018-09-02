package com.eric.mynews.article;

import android.os.Bundle;

import com.eric.mynews.commands.BackCommand;
import com.eric.mynews.models.Article;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static com.eric.mynews.article.ArticleDetailsActivity.ARG_NEWS;

@ParametersAreNonnullByDefault
public class ArticleDetailsViewModel {
    private final BackCommand backCommand;
    public Article article;

    ArticleDetailsViewModel(BackCommand backCommand) {
        this.backCommand = backCommand;
    }

    public void onActivityCreated(@Nullable Bundle extras) {
        if (extras == null) { return; }
        article = extras.getParcelable(ARG_NEWS);
    }

    public void onBackClick() {
        backCommand.goBack();
    }
}
