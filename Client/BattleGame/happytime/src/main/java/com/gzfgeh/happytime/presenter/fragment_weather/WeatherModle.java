package com.gzfgeh.happytime.presenter.fragment_weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import com.gzfgeh.happytime.beans.WeatherBean;
import com.gzfgeh.happytime.utils.JsonUtils;
import com.gzfgeh.happytime.utils.LogUtils;
import com.gzfgeh.happytime.utils.UrlUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Description:
 * Created by guzhenfu on 16/1/17.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class WeatherModle implements IWeatherModle {

    @Override
    public void loadWeatherData(String name, final LoadWeatherListener listener) {
        try {
            String url = UrlUtils.WEATHER + URLEncoder.encode(name, "utf-8");
            OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    listener.onFail(e.getMessage());
                }

                @Override
                public void onResponse(String response) {
                    List<WeatherBean> list = JsonUtils.getWeatherInfo(response);
                    listener.onSuccess(list);
                }
            });
        } catch (UnsupportedEncodingException e) {
            LogUtils.e("TAG", "url encode error.");
        }
    }

    @Override
    public void loadWeatherCity(Context context, final LoadCityListener listener) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                listener.onFail("location failure.");
                return;
            }
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location == null) {
            listener.onFail("location failure.");
            return;
        }
        double latitude = location.getLatitude();     //经度
        double longitude = location.getLongitude(); //纬度
        String url = getLocationURL(latitude, longitude);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                listener.onFail(e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                String city = JsonUtils.getCity(response);
                listener.onSuccess(city);
            }
        });
    }

    private String getLocationURL(double latitude, double longitude) {
        StringBuffer sb = new StringBuffer(UrlUtils.INTERFACE_LOCATION);
        sb.append("?output=json").append("&referer=32D45CBEEC107315C553AD1131915D366EEF79B4");
        sb.append("&location=").append(latitude).append(",").append(longitude);
        LogUtils.d("TAG", sb.toString());
        return sb.toString();
    }

    public interface LoadWeatherListener{
        void onSuccess(List<WeatherBean> list);
        void onFail(String msg);
    }

    public interface LoadCityListener{
        void onSuccess(String name);
        void onFail(String msg);
    }
}
