package com.eric.mynews.main;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;

import com.eric.mynews.MyLocationManager;
import com.eric.mynews.R;
import com.eric.mynews.ResourceProvider;
import com.eric.mynews.commands.BackCommand;
import com.eric.mynews.models.ForecastDay;
import com.eric.mynews.models.NewsResponse;
import com.eric.mynews.repositories.NewsRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainViewModel {
    @VisibleForTesting static final int NUM_DAYS = 4;
    private final Scheduler retrofitScheduler;
    public final MainRVAdapter rvAdapter;
    public final RecyclerView.LayoutManager layoutManager;
    @VisibleForTesting Disposable disposable;
    private final ResourceProvider resourceProvider;
    private final NewsRepository repository;
    private final MyLocationManager locationManager;
    private final Geocoder geocoder;
    public ObservableField<String> currentTemp = new ObservableField<>();
    public ObservableField<String> currentCity = new ObservableField<>();
    public ObservableInt mainTextColour = new ObservableInt(R.color.blue_city_name);
    public ObservableInt mainTextSize = new ObservableInt(R.dimen.text_size_36);
    public ObservableBoolean hasError = new ObservableBoolean();
    public ObservableField<List<ForecastDay>> forecastDays = new ObservableField<>();
    public ObservableBoolean isLoading = new ObservableBoolean(true);
    public ObservableBoolean isForecastReady = new ObservableBoolean(false);

    MainViewModel(NewsRepository repository,
                  RecyclerView.LayoutManager layoutManager,
                  MainRVAdapter rvAdapter,
                  MyLocationManager locationManager,
                  ResourceProvider resourceProvider,
                  Geocoder geocoder,
                  Scheduler retrofitScheduler) {
        this.repository = repository;
        this.layoutManager = layoutManager;
        this.rvAdapter = rvAdapter;
        this.locationManager = locationManager;
        this.resourceProvider = resourceProvider;
        this.geocoder = geocoder;
        this.retrofitScheduler = retrofitScheduler;
    }

    @VisibleForTesting
    void handleError() {
        stopAnimation();
        hasError.set(true);
    }

    public void onActivityDestroyed() {
        if (disposable != null) { disposable.dispose(); }
    }

    @VisibleForTesting
    void stopAnimation() {
        Timber.i("stopAnimation");
        isLoading.set(false);
    }

    @VisibleForTesting
    void startAnimation() {
        Timber.i("startAnimation");
        isLoading.set(true);
    }

    @VisibleForTesting
    Observable<Location> observeLocation() {
        Timber.i("observeLocation");
        return locationManager.observeLocation()
                .take(2);
    }

    public void onActivityCreated() {
        disposable = repository.getNews()
                .subscribeOn(retrofitScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, throwable -> {
                    Timber.e(throwable);
                    handleError();
                });
    }

    private void handleSuccess(NewsResponse newsResponse) {
        stopAnimation();
        hasError.set(false);
        rvAdapter.update(newsResponse.getArticles());
    }
}
