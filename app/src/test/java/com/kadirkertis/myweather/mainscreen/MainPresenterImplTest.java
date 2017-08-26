package com.kadirkertis.myweather.mainscreen;

import com.kadirkertis.myweather.forecastrestservice.model.CurrentConditions;
import com.kadirkertis.myweather.repository.WeatherRepository;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Kadir Kertis on 30.7.2017.
 */
public class MainPresenterImplTest {

    WeatherRepository mockWeatherRepo;
    MainView mockView;
   //Presenter to test
    MainPresenter presenter;
    CurrentConditions conditions;

    @Before
    public void setUp() throws Exception {
        mockWeatherRepo = mock(WeatherRepository.class);

        conditions = new CurrentConditions();
        conditions.setTemperature("35");
        when(mockWeatherRepo.getWeatherInfo()).thenReturn(conditions);

        mockView = mock(MainView.class);
        presenter = new MainPresenterImpl(mockWeatherRepo);
        presenter.setView(mockView);
    }

    @Test
    public void shouldInvokeWeatherLoadingErrorWhenConditionsNull(){
        when(mockWeatherRepo.getWeatherInfo()).thenReturn(null);
        presenter.loadWeatherInfo();
        verify(mockView).displayWeatherLoadingError();

    }

    @Test
    public void  shouldDisplayWeatherWhenConditionsNotNull(){
        presenter.loadWeatherInfo();

        //verify repo interactions
        verify(mockWeatherRepo,times(1)).getWeatherInfo();

        //verify view interaction
        verify(mockView,times(1)).displayTemp("35");

        verify(mockView,never()).displayWeatherLoadingError();
    }

    //This test is flaky because
    //NullPointerException can come from another
    //method int the stack
    @Test(expected = ViewNotFoundException.class)
    public void shouldThrowViewNotFoundExceptionWhenViewIsNull(){
        presenter.setView(null);
        presenter.loadWeatherInfo();
    }

}