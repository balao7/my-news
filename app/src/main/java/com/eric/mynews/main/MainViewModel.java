package com.eric.mynews.main;

import android.databinding.ObservableBoolean;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;

import com.eric.mynews.MyConnectivityManager;
import com.eric.mynews.models.Article;
import com.eric.mynews.models.NewsResponse;
import com.eric.mynews.repositories.NewsRepository;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainViewModel {
    private final Scheduler bgScheduler;
    private final Scheduler uiScheduler;
    public final MainRVAdapter rvAdapter;
    public final RecyclerView.LayoutManager layoutManager;
    private final NewsRepository localRepo;
    private final MyConnectivityManager connectivityManager;
    @VisibleForTesting Disposable disposable;
    private final NewsRepository repository;
    public ObservableBoolean hasError = new ObservableBoolean();
    public ObservableBoolean isLoading = new ObservableBoolean(true);

    MainViewModel(NewsRepository repository,
                  NewsRepository localRepo,
                  MyConnectivityManager connectivityManager,
                  RecyclerView.LayoutManager layoutManager,
                  MainRVAdapter rvAdapter,
                  Scheduler bgScheduler,
                  Scheduler uiScheduler) {
        this.repository = repository;
        this.localRepo = localRepo;
        this.connectivityManager = connectivityManager;
        this.layoutManager = layoutManager;
        this.rvAdapter = rvAdapter;
        this.bgScheduler = bgScheduler;
        this.uiScheduler = uiScheduler;
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

    void onActivityCreated() {
        disposable = fetchNews().doOnSubscribe(disposable -> startAnimation())
                .subscribeOn(bgScheduler)
                .observeOn(uiScheduler)
                .subscribe(this::handleSuccess, throwable -> {
                    Timber.e(throwable);
                    handleError();
                });
    }

    private Single<NewsResponse> fetchNews() {
        if (connectivityManager.isOnline()) {
            return repository.getNews();
        } else {
            return localRepo.getNews();
        }
    }

    @VisibleForTesting
    void handleSuccess(NewsResponse newsResponse) {
        stopAnimation();
        hasError.set(false);
        rvAdapter.update(newsResponse.getArticles());
    }
}
