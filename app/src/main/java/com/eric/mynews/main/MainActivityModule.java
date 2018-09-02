package com.eric.mynews.main;

import android.location.Geocoder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eric.mynews.MyLocationManager;
import com.eric.mynews.ResourceProvider;
import com.eric.mynews.repositories.NewsRepository;
import com.eric.mynews.routers.NewsDetailsRouter;
import com.eric.mynews.routers.NewsDetailsRouterImpl;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;

@Module
public class MainActivityModule {
    @Provides
    NewsDetailsRouter provideRouter(MainActivity mainActivity) {
        return new NewsDetailsRouterImpl(mainActivity);
    }

    @Provides
    MainRVAdapter provideRvAdapter(NewsDetailsRouter router) {
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
