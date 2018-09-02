package com.eric.mynews;

import com.eric.mynews.models.DaoMaster;
import com.eric.mynews.models.DaoSession;
import com.eric.mynews.repositories.NewsLocalRepoImpl;
import com.eric.mynews.repositories.NewsRepository;
import com.eric.mynews.repositories.NewsRepositoryImpl;
import com.eric.mynews.rest.NewsApi;

import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    AbstractDaoSession provideDaoSession(MyApp myApp) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(myApp, "notes-db");
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    @Singleton
    @Named("remote")
    @Provides
    NewsRepository provideRepository(Retrofit.Builder builder) {
        return new NewsRepositoryImpl(builder.baseUrl(BuildConfig.API_BASE)
                .build()
                .create(NewsApi.class));
    }

    @Singleton
    @Named("local")
    @Provides
    NewsRepository provideLocalRepository(AbstractDaoSession daoSession) {
        return new NewsLocalRepoImpl(daoSession);
    }
}
