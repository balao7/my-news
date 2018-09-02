package com.eric.mynews.main;

import android.location.Geocoder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eric.mynews.MyLocationManager;
import com.eric.mynews.ResourceProvider;
import com.eric.mynews.commands.BackCommand;
import com.eric.mynews.commands.BackCommandImpl;
import com.eric.mynews.repositories.NewsRepository;
import com.eric.mynews.commands.NewsDetailsCommand;
import com.eric.mynews.commands.NewsDetailsCmdImpl;

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
    MainViewModel provideViewModel(NewsRepository weatherRepository,
                                   RecyclerView.LayoutManager layoutManager,
                                   MainRVAdapter rvAdapter,
                                   MyLocationManager locationManager,
                                   ResourceProvider resourceProvider,
                                   Geocoder geocoder) {
        return new MainViewModel(weatherRepository, layoutManager, rvAdapter, locationManager, resourceProvider, geocoder, Schedulers.io());
    }
}
