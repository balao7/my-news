package com.eric.mynews.article;

import com.eric.mynews.commands.BackCommand;
import com.eric.mynews.commands.BackCommandImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ArticleDetailsActivityModule {
    @Provides
    BackCommand provideBackCmd(ArticleDetailsActivity mainActivity) {
        return new BackCommandImpl(mainActivity);
    }

    @Provides
    ArticleDetailsViewModel provideViewModel(BackCommand backCommand) {
        return new ArticleDetailsViewModel(backCommand);
    }
}
