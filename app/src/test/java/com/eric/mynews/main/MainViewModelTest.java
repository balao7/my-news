package com.eric.mynews.main;

import android.support.v7.widget.RecyclerView;

import com.eric.mynews.models.NewsResponse;
import com.eric.mynews.repositories.NewsRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.UnknownServiceException;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MainViewModelTest {
    @Mock private NewsRepository repository;
    @Mock private RecyclerView.LayoutManager layoutManager;
    @Mock private MainRVAdapter rvAdapter;
    private MainViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        viewModel = Mockito.spy(new MainViewModel(repository, layoutManager, rvAdapter, Schedulers.trampoline(), Schedulers.trampoline()));
    }

    @Test
    public void handleSuccess() {
        final NewsResponse newsResponse = new NewsResponse();
        Mockito.doNothing()
                .when(viewModel)
                .stopAnimation();

        viewModel.handleSuccess(newsResponse);

        Mockito.verify(viewModel)
                .stopAnimation();
        Assert.assertFalse(viewModel.hasError.get());
        Mockito.verify(rvAdapter)
                .update(newsResponse.getArticles());
    }

    @Test
    public void handleError() {
        Mockito.doNothing()
                .when(viewModel)
                .stopAnimation();

        viewModel.handleError();

        Mockito.verify(viewModel)
                .stopAnimation();
        Assert.assertTrue(viewModel.hasError.get());
    }

    @Test
    public void onActivityCreated() {
        Assert.assertNull(viewModel.disposable);
        final NewsResponse newsResponse = new NewsResponse();
        Mockito.doReturn(Single.just(newsResponse))
                .when(repository)
                .getNews();
        Mockito.doNothing()
                .when(viewModel)
                .startAnimation();
        Mockito.doNothing()
                .when(viewModel)
                .handleError();
        Mockito.doNothing()
                .when(viewModel)
                .handleSuccess(Mockito.any(NewsResponse.class));

        viewModel.onActivityCreated();

        Assert.assertNotNull(viewModel.disposable);
        Mockito.verify(viewModel)
                .handleSuccess(newsResponse);
    }

    @Test
    public void onActivityCreated_Error() {
        Assert.assertNull(viewModel.disposable);
        Mockito.doReturn(Single.error(new UnknownServiceException("something wrong")))
                .when(repository)
                .getNews();
        Mockito.doNothing()
                .when(viewModel)
                .startAnimation();
        Mockito.doNothing()
                .when(viewModel)
                .handleError();
        Mockito.doNothing()
                .when(viewModel)
                .handleSuccess(Mockito.any(NewsResponse.class));

        viewModel.onActivityCreated();

        Assert.assertNotNull(viewModel.disposable);
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
}
