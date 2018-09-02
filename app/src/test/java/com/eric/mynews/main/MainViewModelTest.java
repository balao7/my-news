package com.eric.mynews.main;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.eric.mynews.MyLocationManager;
import com.eric.mynews.R;
import com.eric.mynews.ResourceProvider;
import com.eric.mynews.models.CurrentDayWeather;
import com.eric.mynews.models.Day;
import com.eric.mynews.models.Forecast;
import com.eric.mynews.models.ForecastDay;
import com.eric.mynews.models.ForecastResponse;
import com.eric.mynews.repositories.NewsRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.UnknownServiceException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static com.eric.mynews.main.MainViewModel.NUM_DAYS;

public class MainViewModelTest {
    @Mock private NewsRepository weatherRepository;
    @Mock private MyLocationManager myLocationManager;
    @Mock private ResourceProvider resourceProvider;
    @Mock private Geocoder geocoder;
    private TestScheduler testScheduler = new TestScheduler();
    private MainViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        viewModel = Mockito.spy(new MainViewModel(weatherRepository, myLocationManager, resourceProvider, geocoder, Schedulers.trampoline
                ()));
    }

    @Test
    public void refreshData() {
        Assert.assertNull(viewModel.disposable);
        Location location1 = Mockito.mock(Location.class);
        Mockito.doReturn(Observable.just(location1))
                .when(myLocationManager)
                .observeLocation();
        ForecastResponse forecastResponse = Mockito.mock(ForecastResponse.class);
        Mockito.doReturn(Single.just(forecastResponse))
                .when(viewModel)
                .getForecast(Mockito.any());
        Mockito.doNothing()
                .when(viewModel)
                .handleSuccess(Mockito.any());
        Mockito.doNothing()
                .when(viewModel)
                .updateCityName(Mockito.any());
        Mockito.doNothing()
                .when(viewModel)
                .startAnimation();

        viewModel.refreshData();
        testScheduler.triggerActions();

        Mockito.verify(viewModel)
                .startAnimation();
        Assert.assertNotNull(viewModel.disposable);
        viewModel.disposable.dispose();
        Mockito.verify(viewModel)
                .observeLocation();
        Mockito.verify(viewModel)
                .handleSuccess(forecastResponse);
        Mockito.verify(viewModel)
                .updateCityName(Mockito.any(Location.class));
    }

    @Test
    public void refreshData_Error_Location() {
        Assert.assertNull(viewModel.disposable);
        Mockito.doReturn(Observable.error(new UnknownError("location problem")))
                .when(myLocationManager)
                .observeLocation();
        Mockito.doNothing()
                .when(viewModel)
                .handleError();
        Mockito.doNothing()
                .when(viewModel)
                .startAnimation();

        viewModel.refreshData();
        testScheduler.triggerActions();

        Mockito.verify(viewModel)
                .startAnimation();
        Assert.assertNotNull(viewModel.disposable);
        viewModel.disposable.dispose();
        Mockito.verify(viewModel)
                .handleError();
    }

    @Test
    public void refreshData_Error_Api() {
        Assert.assertNull(viewModel.disposable);
        Mockito.doReturn(Observable.error(new UnknownError("location problem")))
                .when(myLocationManager)
                .observeLocation();
        Mockito.doNothing()
                .when(viewModel)
                .handleError();
        Location location1 = Mockito.mock(Location.class);
        Mockito.doReturn(Observable.just(location1))
                .when(myLocationManager)
                .observeLocation();
        Mockito.doReturn(Single.error(new UnknownServiceException("problem with api")))
                .when(weatherRepository)
                .getForecast(Mockito.anyString(), Mockito.anyInt());
        Mockito.doNothing()
                .when(viewModel)
                .startAnimation();

        viewModel.refreshData();
        testScheduler.triggerActions();

        Mockito.verify(viewModel)
                .startAnimation();
        Assert.assertNotNull(viewModel.disposable);
        viewModel.disposable.dispose();
        Mockito.verify(viewModel)
                .handleError();
    }

    @Test
    public void handleSuccess() {
        final CurrentDayWeather currentDayWeather = new CurrentDayWeather(18);
        final List<ForecastDay> forecastDayList = Collections.singletonList(new ForecastDay(new Day(25.5f), "2018-08-20"));
        final Forecast forecast = new Forecast(forecastDayList);
        final ForecastResponse forecastResponse = new ForecastResponse(currentDayWeather, forecast);
        Mockito.doReturn("%d Celsius")
                .when(resourceProvider)
                .getString(R.string.temp_degree);
        Mockito.doNothing()
                .when(viewModel)
                .stopAnimation();

        viewModel.handleSuccess(forecastResponse);

        Mockito.verify(viewModel)
                .stopAnimation();
        Assert.assertFalse(viewModel.hasError.get());
        Assert.assertEquals("18 Celsius", viewModel.currentTemp.get());
        Assert.assertEquals(R.color.blue_city_name, viewModel.mainTextColour.get());
        Assert.assertEquals(R.dimen.text_size_36, viewModel.mainTextSize.get());
        Assert.assertEquals(forecastDayList, viewModel.forecastDays.get());
    }

    @Test
    public void handleError() {
        Mockito.doReturn("something wrong")
                .when(resourceProvider)
                .getString(R.string.error_msg);
        Mockito.doNothing()
                .when(viewModel)
                .stopAnimation();

        viewModel.handleError();

        Mockito.verify(viewModel)
                .stopAnimation();
        Assert.assertTrue(viewModel.hasError.get());
        Assert.assertEquals("something wrong", viewModel.currentCity.get());
        Assert.assertEquals(R.color.white, viewModel.mainTextColour.get());
        Assert.assertEquals(R.dimen.text_size_54, viewModel.mainTextSize.get());
    }

    @Test
    public void updateCityName() throws IOException {
        Address address = Mockito.mock(Address.class);
        Mockito.doReturn("afeawfe")
                .when(address)
                .getLocality();
        Location location = Mockito.mock(Location.class);
        Mockito.doReturn(Collections.singletonList(address))
                .when(geocoder)
                .getFromLocation(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyInt());

        viewModel.updateCityName(location);

        Assert.assertEquals("afeawfe", viewModel.currentCity.get());
    }

    @Test
    public void observeLocation() {
        Location location1 = Mockito.mock(Location.class);
        Location location2 = Mockito.mock(Location.class);
        Location location3 = Mockito.mock(Location.class);
        Mockito.doReturn(Observable.just(location1, location2, location3))
                .when(myLocationManager)
                .observeLocation();

        TestObserver<Location> testObserver = viewModel.observeLocation()
                .test();
        testScheduler.triggerActions();

        testObserver.assertNoErrors()
                .assertValueCount(2)
                .assertTerminated()
                .dispose();
    }

    @Test
    public void observeLocation_Error() {
        Mockito.doReturn(Observable.error(new UnknownServiceException("location problem")))
                .when(myLocationManager)
                .observeLocation();

        TestObserver<Location> testObserver = viewModel.observeLocation()
                .test();
        testScheduler.triggerActions();

        testObserver.assertErrorMessage("location problem")
                .assertError(UnknownServiceException.class)
                .assertTerminated()
                .dispose();
    }

    @Test
    public void getForecast() {
        Location location = Mockito.mock(Location.class);
        final String locationStr = String.format(Locale.US, "%f,%f", location.getLatitude(), location.getLongitude());
        ForecastResponse forecastResponse = Mockito.mock(ForecastResponse.class);
        Mockito.doReturn(Single.just(forecastResponse))
                .when(weatherRepository)
                .getForecast(locationStr, NUM_DAYS);

        viewModel.getForecast(location)
                .test()
                .assertValueCount(1)
                .assertTerminated()
                .dispose();
    }
}
