package com.kadirkertis.myweather.mainscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kadirkertis.myweather.MyWeatherApp;
import com.kadirkertis.myweather.R;
import com.kadirkertis.myweather.mainscreen.di.DaggerMainActivityComponent;
import com.kadirkertis.myweather.mainscreen.di.MainActivityComponent;
import com.kadirkertis.myweather.mainscreen.di.MainActivityModule;

import javax.inject.Inject;


public class MainFragment extends Fragment implements MainView {

    @Inject
    MainPresenter mainPresenter;


    protected TextView tempText;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityComponent component = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule((MainActivity) getActivity()))
                .appComponent(MyWeatherApp.getApplication(getActivity()).getAppComponent())
                .build();
        component.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        tempText = (TextView) v.findViewById(R.id.mainFragmentTmpText);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.setView(this);
        mainPresenter.loadWeatherInfo();
        mainPresenter.loadCurrentWeather();
    }

    @Override
    public void displayTemp(String tmp) {
        tempText.setText(tmp);
    }

    @Override
    public void displayWeatherLoadingError() {
        Toast.makeText(getActivity(), getString(R.string.error_loading_temperature), Toast.LENGTH_SHORT).show();
    }


}
