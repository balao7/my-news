package com.eric.mynews.repositories;

import com.eric.mynews.models.Article;
import com.eric.mynews.models.ArticleDao;

import org.greenrobot.greendao.AbstractDaoSession;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import io.reactivex.Completable;
import io.reactivex.Single;
import timber.log.Timber;

@ParametersAreNonnullByDefault
public class NewsLocalRepoImpl implements NewsRepository {
    private final AbstractDaoSession daoSession;

    public NewsLocalRepoImpl(AbstractDaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public Single<List<Article>> getNews() {
        Timber.i("getNews");
        return Single.fromCallable(() -> daoSession.queryBuilder(Article.class)
                .orderDesc(ArticleDao.Properties.PublishedAt)
                .list());
    }

    public Completable insertOrReplace(Article article) {
        return Completable.fromCallable(() -> daoSession.insertOrReplace(article));
    }
}
