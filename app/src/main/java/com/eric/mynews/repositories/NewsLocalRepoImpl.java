package com.eric.mynews.repositories;

import com.eric.mynews.models.Article;
import com.eric.mynews.models.ArticleDao;
import com.eric.mynews.models.DaoSession;
import com.eric.mynews.models.NewsResponse;

import org.greenrobot.greendao.AbstractDaoSession;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.ToLongFunction;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

public class NewsLocalRepoImpl {
    private final AbstractDaoSession daoSession;

    public NewsLocalRepoImpl(AbstractDaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public Single<List<Article>> getNews() {
        Timber.i("loading news from local db");
        return Single.fromCallable(new Callable<List<Article>>() {
            @Override
            public List<Article> call() throws Exception {
                return daoSession.queryBuilder(Article.class)
                        .orderDesc(ArticleDao.Properties.PublishedAt)
                        .list();
            }
        });
    }

    public Completable insertNews(Article article) {
        return Completable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return daoSession.insert(article);
            }
        });
    }

    public Completable insertOrReplace(Article article) {
        return Completable.fromCallable((Callable<Long>) () -> daoSession.insertOrReplace(article));
    }
}
