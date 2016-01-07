package com.gzfgeh.happytime.presenter;

import com.gzfgeh.happytime.R;

/**
 * Created by sunup_002 on 2016/1/7.
 */
public class MainPresenterImpl implements IMainPresenter {
    private IMainView mainView;

    public MainPresenterImpl(IMainView mainView){
        this.mainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_news:
                mainView.switch2News();
                break;
            case R.id.navigation_item_images:
                mainView.switch2Images();
                break;
            case R.id.navigation_item_weather:
                mainView.switch2Weather();
                break;
            case R.id.navigation_item_about:
                mainView.switch2About();
                break;
            default:
                mainView.switch2News();
                break;
        }
    }
}
