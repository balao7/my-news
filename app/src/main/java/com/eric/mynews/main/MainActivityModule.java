package com.eric.mynews.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eric.mynews.commands.NewsDetailsCmdImpl;
import com.eric.mynews.commands.NewsDetailsCommand;
import com.eric.mynews.repositories.NewsRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;

@Module
public class MainActivityModule {
    @Provides
    NewsDetailsCommand provideRouter(MainActivity mainActivity) {
        return new NewsDetailsCmdImpl(mainActivity);
    }

    @Provides
    MainRVAdapter provideRvAdapter(NewsDetailsCommand router) {
        return new MainRVAdapter(router);
    }

    @Provides
    RecyclerView.LayoutManager provideLayoutManager(MainActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    MainViewModel provideViewModel(NewsRepository weatherRepository, RecyclerView.LayoutManager layoutManager, MainRVAdapter rvAdapter) {
        return new MainViewModel(weatherRepository, layoutManager, rvAdapter, Schedulers.io());
    }
}
