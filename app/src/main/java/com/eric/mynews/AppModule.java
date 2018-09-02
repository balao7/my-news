package com.eric.mynews;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module(includes = {RepositoryModule.class})
public class AppModule {
    @Provides
    @Singleton
    Retrofit.Builder provideRetrofit() {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        final Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter().nullSafe())
                .build();
        return new Retrofit.Builder().client(okHttpBuilder.build())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }
}
