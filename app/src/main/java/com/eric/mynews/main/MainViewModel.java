package com.eric.mynews.main;

import android.databinding.ObservableBoolean;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;

import com.eric.mynews.models.NewsResponse;
import com.eric.mynews.repositories.NewsRepository;

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
    private final NewsRepository repository;
    public ObservableBoolean hasError = new ObservableBoolean();
    public ObservableBoolean isLoading = new ObservableBoolean(true);

    MainViewModel(NewsRepository repository,
                  RecyclerView.LayoutManager layoutManager,
                  MainRVAdapter rvAdapter,
                  Scheduler retrofitScheduler) {
        this.repository = repository;
        this.layoutManager = layoutManager;
        this.rvAdapter = rvAdapter;
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
