package com.eric.mynews;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {RepositoryModule.class})
public class AppModule {
    @Provides
    @Singleton
    Retrofit.Builder provideRetrofit() {
        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel
                (HttpLoggingInterceptor.Level.BODY))
                .build();
        return new Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }
}
