package com.gzfgeh.happytime.presenter.fragment_weather;

import com.gzfgeh.happytime.beans.WeatherBean;
import com.gzfgeh.happytime.presenter.IProgress;

import java.util.List;

/**
 * Description:
 * Created by guzhenfu on 16/1/17.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public interface IWeatherView extends IProgress{
    void showFail(String msg);
    void showWeatherLayout();

    void setCity(String city);
    void setToday(String data);
    void setTemperature(String temperature);
    void setWind(String wind);
    void setWeather(String weather);
    void setWeatherImage(int res);
    void setWeatherData(List<WeatherBean> lists);
}
