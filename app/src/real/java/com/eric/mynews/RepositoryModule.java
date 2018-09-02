package com.eric.mynews;

import com.eric.mynews.repositories.NewsRepository;
import com.eric.mynews.repositories.NewsRepositoryImpl;
import com.eric.mynews.rest.NewsApi;

import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RepositoryModule {
//    @Singleton
//    @Provides AbstractDaoSession provideDaoSession(MyApp myApp) {
//        DevOpenHelper helper = new DevOpenHelper(myApp, "notes-db");
//        Database db = helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
//    }

    @Singleton
    @Provides
    NewsRepository provideRepository(Retrofit.Builder builder) {
        return new NewsRepositoryImpl(builder.baseUrl(BuildConfig.API_BASE)
                .build()
                .create(NewsApi.class));
    }
}
