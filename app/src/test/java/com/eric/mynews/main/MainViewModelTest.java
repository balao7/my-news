package com.eric.mynews.main;

import android.support.v7.widget.RecyclerView;

import com.eric.mynews.MyConnectivityManager;
import com.eric.mynews.models.Article;
import com.eric.mynews.models.NewsResponse;
import com.eric.mynews.repositories.NewsLocalRepoImpl;
import com.eric.mynews.repositories.NewsRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.UnknownServiceException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MainViewModelTest {
    @Mock private NewsRepository repository;
    @Mock private NewsLocalRepoImpl localRepo;
    @Mock private MyConnectivityManager connectivityManager;
    @Mock private RecyclerView.LayoutManager layoutManager;
    @Mock private MainRVAdapter rvAdapter;
    private MainViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        viewModel = Mockito.spy(new MainViewModel(repository, localRepo, connectivityManager, layoutManager, rvAdapter, Schedulers
                .trampoline(), Schedulers.trampoline()));
    }

    @Test
    public void handleSuccess() {
        final Article article = new Article(32432L, "author", "title", "desc", "url", "img url", new Date());
        Mockito.doNothing()
                .when(viewModel)
                .stopAnimation();

        viewModel.handleSuccess(Collections.singletonList(article));

        Assert.assertFalse(viewModel.hasError.get());
        Mockito.verify(rvAdapter)
                .update(Collections.singletonList(article));
    }

    @Test
    public void handleError() {
        Mockito.doNothing()
                .when(viewModel)
                .stopAnimation();

        viewModel.handleError();

        Assert.assertTrue(viewModel.hasError.get());
    }

    @Test
    public void onActivityCreated() {
        Assert.assertNull(viewModel.disposable);
        final Article article1 = new Article(32432L, "author", "title1", "desc", "url", "img url", new Date());
        final Article article2 = new Article(43535L, "author", "title2", "desc", "url", "img url", new Date());
        final List<Article> articles = Arrays.asList(article1, article2);
        Mockito.doReturn(Single.just(articles))
                .when(viewModel)
                .fetchNews();
        Mockito.doNothing()
                .when(viewModel)
                .startAnimation();
        Mockito.doNothing()
                .when(viewModel)
                .handleError();
        Mockito.doNothing()
                .when(viewModel)
                .handleSuccess(Mockito.any());
        Mockito.doReturn(Completable.complete())
                .when(localRepo)
                .insertOrReplace(Mockito.any(Article.class));

        viewModel.onActivityCreated();

        Assert.assertNotNull(viewModel.disposable);
        Mockito.verify(viewModel)
                .startAnimation();
        Mockito.verify(viewModel)
                .stopAnimation();
        Mockito.verify(viewModel)
                .handleSuccess(articles);
        Mockito.verify(localRepo, Mockito.times(articles.size()))
                .insertOrReplace(Mockito.any(Article.class));
    }

    @Test
    public void onActivityCreated_Error() {
        Assert.assertNull(viewModel.disposable);
        Mockito.doReturn(Single.error(new UnknownServiceException("something wrong")))
                .when(viewModel)
                .fetchNews();
        Mockito.doNothing()
                .when(viewModel)
                .startAnimation();
        Mockito.doNothing()
                .when(viewModel)
                .handleError();

        viewModel.onActivityCreated();

        Assert.assertNotNull(viewModel.disposable);
        Mockito.verify(viewModel)
                .startAnimation();
        Mockito.verify(viewModel)
                .stopAnimation();
        Mockito.verify(viewModel)
                .handleError();
    }

    @Test
    public void stopAnimation() {
        viewModel.stopAnimation();

        Assert.assertFalse(viewModel.isLoading.get());
    }

    @Test
    public void startAnimation() {
        viewModel.startAnimation();

        Assert.assertTrue(viewModel.isLoading.get());
    }

    @Test
    public void fetchNews_Online() {
        Mockito.doReturn(true)
                .when(connectivityManager)
                .isOnline();
        final Article article = new Article(32432L, "author", "title", "desc", "url", "img url", new Date());
        final List<Article> articles = Collections.singletonList(article);
        Mockito.doReturn(Single.just(articles))
                .when(repository)
                .getNews();

        viewModel.fetchNews()
                .test()
                .assertValueCount(1)
                .assertValueAt(0, articles)
                .assertTerminated()
                .dispose();
    }

    @Test
    public void fetchNews_Offline() {
        Mockito.doReturn(false)
                .when(connectivityManager)
                .isOnline();
        final Article article = new Article(32432L, "author", "title", "desc", "url", "img url", new Date());
        final List<Article> articles = Collections.singletonList(article);
        Mockito.doReturn(Single.just(articles))
                .when(localRepo)
                .getNews();

        viewModel.fetchNews()
                .test()
                .assertValueCount(1)
                .assertValueAt(0, articles)
                .assertTerminated()
                .dispose();
    }
}
