package com.gzfgeh.happytime.presenter.fragment_weather;

import android.content.Context;

/**
 * Description:
 * Created by guzhenfu on 16/1/17.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public interface IWeatherModle {
    void loadWeatherData(String name, WeatherModle.LoadWeatherListener listener);
    void loadWeatherCity(Context context, WeatherModle.LoadCityListener listener);
}
