package com.eric.mynews.main;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;

import com.eric.mynews.MyLocationManager;
import com.eric.mynews.R;
import com.eric.mynews.ResourceProvider;
import com.eric.mynews.models.ForecastDay;
import com.eric.mynews.models.ForecastResponse;
import com.eric.mynews.models.NewsResponse;
import com.eric.mynews.repositories.NewsRepository;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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

    MainViewModel(NewsRepository weatherRepository,
                  RecyclerView.LayoutManager layoutManager,
                  MainRVAdapter rvAdapter,
                  MyLocationManager locationManager,
                  ResourceProvider resourceProvider,
                  Geocoder geocoder,
                  Scheduler retrofitScheduler) {
        this.repository = weatherRepository;
        this.layoutManager = layoutManager;
        this.rvAdapter = rvAdapter;
        this.locationManager = locationManager;
        this.resourceProvider = resourceProvider;
        this.geocoder = geocoder;
        this.retrofitScheduler = retrofitScheduler;
    }

    @VisibleForTesting
    void updateCityName(Location location) {
        Timber.i("updateCityName - %s", location);
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            currentCity.set(addressList.get(0)
                    .getLocality());
        } catch (IOException e) {
            Timber.e(e, "could not get city name");
            e.printStackTrace();
        }
    }

    @VisibleForTesting
    void handleSuccess(ForecastResponse forecastResponse) {
        stopAnimation();
        hasError.set(false);
        isForecastReady.set(true);
        currentTemp.set(String.format(Locale.US, resourceProvider.getString(R.string.temp_degree), forecastResponse.getCurrent()
                .getTempC()));
        mainTextColour.set(R.color.blue_city_name);
        mainTextSize.set(R.dimen.text_size_36);
        forecastDays.set(forecastResponse.getForecast()
                .getForecastday());
    }

    @VisibleForTesting
    void handleError() {
        stopAnimation();
        hasError.set(true);
        currentCity.set(resourceProvider.getString(R.string.error_msg));
        mainTextColour.set(R.color.white);
        mainTextSize.set(R.dimen.text_size_54);
    }

    public void onActivityDestroyed() {
        if (disposable != null) { disposable.dispose(); }
    }

    //    @VisibleForTesting
    //    Single<ForecastResponse> getForecast(Location location) {
    //        Timber.i("getForecast");
    //        final String locationStr = String.format(Locale.US, "%f,%f", location.getLatitude(), location.getLongitude());
    //        return repository.getForecast(locationStr, NUM_DAYS)
    //                .subscribeOn(retrofitScheduler);
    //    }

    public void onRetryClicked() {
        if (disposable != null) { disposable.dispose(); }
        refreshData();
    }

    public void refreshData() {
        //        disposable = observeLocation().doOnSubscribe(disposable -> startAnimation())
        //                .doOnNext(this::updateCityName)
        //                .flatMapSingle(this::getForecast)
        //                .subscribe(this::handleSuccess, throwable -> {
        //                    Timber.e(throwable);
        //                    handleError();
        //                });
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
                .subscribe(new Consumer<NewsResponse>() {
                    @Override
                    public void accept(NewsResponse newsResponse) throws Exception {
                        handleSuccess(newsResponse);
                    }
                }, Timber::d);
    }

    private void handleSuccess(NewsResponse newsResponse) {
        stopAnimation();
        hasError.set(false);
        rvAdapter.update(newsResponse.getArticles());
    }
}
