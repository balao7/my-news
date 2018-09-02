package com.eric.mynews.repositories;

import com.eric.mynews.models.DaoSession;
import com.eric.mynews.models.NewsResponse;

import org.greenrobot.greendao.AbstractDaoSession;

import io.reactivex.Single;

public class NewsLocalRepoImpl implements NewsRepository {
    private final AbstractDaoSession daoSession;

    public NewsLocalRepoImpl(AbstractDaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public Single<NewsResponse> getNews() {
        return Single.error(new Exception("not implemented"));
    }
}
