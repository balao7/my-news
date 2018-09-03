package com.eric.mynews.main;

import android.databinding.ObservableBoolean;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;

import com.eric.mynews.MyConnectivityManager;
import com.eric.mynews.models.Article;
import com.eric.mynews.repositories.NewsLocalRepoImpl;
import com.eric.mynews.repositories.NewsRepository;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import timber.log.Timber;

@ParametersAreNonnullByDefault
public class MainViewModel {
    private final Scheduler bgScheduler;
    private final Scheduler uiScheduler;
    public final MainRVAdapter rvAdapter;
    public final RecyclerView.LayoutManager layoutManager;
    private final NewsLocalRepoImpl localRepo;
    private final MyConnectivityManager connectivityManager;
    @VisibleForTesting Disposable disposable;
    private final NewsRepository repository;
    public ObservableBoolean hasError = new ObservableBoolean();
    public ObservableBoolean isLoading = new ObservableBoolean(true);

    MainViewModel(NewsRepository repository,
                  NewsLocalRepoImpl localRepo,
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
        hasError.set(true);
    }

    public void onActivityDestroyed() {
        if (disposable != null) { disposable.dispose(); }
    }

    @VisibleForTesting
    void stopAnimation() {
        isLoading.set(false);
    }

    @VisibleForTesting
    void startAnimation() {
        isLoading.set(true);
    }

    void onActivityCreated() {
        disposable = fetchNews().doOnSubscribe(disposable -> startAnimation())
                .doFinally(this::stopAnimation)
                .subscribeOn(bgScheduler)
                .observeOn(uiScheduler)
                .doOnSuccess(this::handleSuccess)
                .flatMapCompletable(articles -> Observable.fromIterable(articles)
                        .subscribeOn(bgScheduler)
                        .flatMapCompletable(localRepo::insertOrReplace))
                .subscribe(Functions.EMPTY_ACTION, throwable -> {
                    Timber.e(throwable);
                    handleError();
                });
    }

    @VisibleForTesting
    Single<List<Article>> fetchNews() {
        if (connectivityManager.isOnline()) {
            return repository.getNews();
        } else {
            return localRepo.getNews();
        }
    }

    @VisibleForTesting
    void handleSuccess(List<Article> articles) {
        hasError.set(false);
        rvAdapter.update(articles);
    }
}
