package com.gzfgeh.happytime.presenter.fragment_weather;

import com.gzfgeh.happytime.APP;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.beans.WeatherBean;
import com.gzfgeh.happytime.utils.Utils;

import java.util.List;
/**
 * Description:
 * Created by guzhenfu on 16/1/17.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class WeatherPresenter implements IWeatherPresenter, WeatherModle.LoadWeatherListener {
    private IWeatherModle modle;
    private IWeatherView view;

    public WeatherPresenter(IWeatherView view) {
        this.view = view;
        modle = new WeatherModle();
    }

    @Override
    public void loadWeather() {
        view.showProgress();
        if (!Utils.isNetworkAvailable(APP.getContext())){
            view.showFail(APP.getContext().getString(R.string.load_fail));
            view.hideProgress();
            return;
        }
        modle.loadWeatherCity(APP.getContext(), listener);
    }

    WeatherModle.LoadCityListener listener = new WeatherModle.LoadCityListener() {
        @Override
        public void onSuccess(String name) {
            view.setCity(name);
            modle.loadWeatherData(name, WeatherPresenter.this);
        }

        @Override
        public void onFail(String msg) {
            view.showFail(msg);
            view.hideProgress();
        }
    };

    @Override
    public void onSuccess(List<WeatherBean> list) {
        WeatherBean bean = list.remove(0);
        view.setToday(bean.getDate());
        view.setWeather(bean.getWeather());
        view.setTemperature(bean.getTemperature());
        view.setWind(bean.getWind());
        view.setWeatherImage(bean.getImageRes());
        view.setWeatherData(list);
        view.hideProgress();
        view.showWeatherLayout();
    }

    @Override
    public void onFail(String msg) {
        view.showFail(msg);
        view.hideProgress();
    }
}
