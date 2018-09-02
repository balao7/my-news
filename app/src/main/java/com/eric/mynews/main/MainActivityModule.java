package com.eric.mynews.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eric.mynews.MyConnectivityManager;
import com.eric.mynews.MyConnectivityManagerImpl;
import com.eric.mynews.commands.NewsDetailsCmdImpl;
import com.eric.mynews.commands.NewsDetailsCommand;
import com.eric.mynews.repositories.NewsRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    MyConnectivityManager provideConnManager(MainActivity activity) {
        return new MyConnectivityManagerImpl(activity);
    }

    @Provides
    MainViewModel provideViewModel(@Named("remote") NewsRepository remoteRepository,
                                   @Named("local") NewsRepository localRepo,
                                   MyConnectivityManager provideConnManager,
                                   RecyclerView.LayoutManager layoutManager,
                                   MainRVAdapter rvAdapter) {
        return new MainViewModel(remoteRepository, localRepo, provideConnManager, layoutManager, rvAdapter, Schedulers.io(),
                AndroidSchedulers.mainThread());
    }
}
