package com.eric.mynews.article;

import com.eric.mynews.commands.BackCommand;
import com.eric.mynews.commands.BackCommandImpl;
import com.eric.mynews.commands.OpenWebBrowserCmd;
import com.eric.mynews.commands.OpenWebBrowserCmdImpl;

import javax.annotation.ParametersAreNonnullByDefault;

import dagger.Module;
import dagger.Provides;

@Module
@ParametersAreNonnullByDefault
public class ArticleDetailsActivityModule {
    @Provides
    BackCommand provideBackCmd(ArticleDetailsActivity mainActivity) {
        return new BackCommandImpl(mainActivity);
    }

    @Provides
    OpenWebBrowserCmd provideOpenWebBrowserCmd(ArticleDetailsActivity mainActivity) {
        return new OpenWebBrowserCmdImpl(mainActivity);
    }

    @Provides
    ArticleDetailsViewModel provideViewModel(BackCommand backCommand, OpenWebBrowserCmd openWebBrowserCmd) {
        return new ArticleDetailsViewModel(backCommand, openWebBrowserCmd);
    }
}
