package com.gzfgeh.happytime.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.beans.WeatherBean;
import com.gzfgeh.happytime.presenter.fragment_weather.IWeatherPresenter;
import com.gzfgeh.happytime.presenter.fragment_weather.IWeatherView;
import com.gzfgeh.happytime.presenter.fragment_weather.WeatherPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Description:
 * Created by guzhenfu on 16/1/17.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class WeatherFragment extends Fragment implements IWeatherView {

    @Bind(R.id.city)
    TextView city;
    @Bind(R.id.today)
    TextView today;
    @Bind(R.id.weatherImage)
    ImageView weatherImage;
    @Bind(R.id.weatherTemp)
    TextView weatherTemp;
    @Bind(R.id.wind)
    TextView wind;
    @Bind(R.id.weather)
    TextView weather;
    @Bind(R.id.weather_content)
    LinearLayout weatherContent;
    @Bind(R.id.weather_layout)
    LinearLayout weatherLayout;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.root_layout)
    FrameLayout rootLayout;

    private IWeatherPresenter presenter;

    public static WeatherFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        WeatherFragment fragment = new WeatherFragment();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WeatherPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);

        presenter.loadWeather();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showFail(String msg) {

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWeatherLayout() {
        weatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCity(String c) {
        city.setText(c);
    }

    @Override
    public void setToday(String data) {
        today.setText(data);
    }

    @Override
    public void setTemperature(String temperature) {
        weatherTemp.setText(temperature);
    }

    @Override
    public void setWind(String w) {
        wind.setText(w);
    }

    @Override
    public void setWeather(String w) {
        weather.setText(w);
    }

    @Override
    public void setWeatherImage(int res) {
        weatherImage.setImageResource(res);
    }

    @Override
    public void setWeatherData(List<WeatherBean> lists) {
        List<View> list = new ArrayList<>();
        for (WeatherBean weatherBean : lists){
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_weather, null, false);
            TextView dateTV = (TextView) view.findViewById(R.id.date);
            ImageView todayWeatherImage = (ImageView) view.findViewById(R.id.weatherImage);
            TextView todayTemperatureTV = (TextView) view.findViewById(R.id.weatherTemp);
            TextView todayWindTV = (TextView) view.findViewById(R.id.wind);
            TextView todayWeatherTV = (TextView) view.findViewById(R.id.weather);

            dateTV.setText(weatherBean.getWeek());
            todayTemperatureTV.setText(weatherBean.getTemperature());
            todayWindTV.setText(weatherBean.getWind());
            todayWeatherTV.setText(weatherBean.getWeather());
            todayWeatherImage.setImageResource(weatherBean.getImageRes());
            weatherContent.addView(view);
            list.add(view);
        }
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }
}
