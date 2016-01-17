package com.gzfgeh.happytime.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.gzfgeh.happytime.R;
import com.gzfgeh.happytime.beans.ImageBean;
import com.gzfgeh.happytime.beans.NewsBean;
import com.gzfgeh.happytime.beans.NewsDetailBean;
import com.gzfgeh.happytime.beans.WeatherBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by guzhenfu on 16/1/10.
 * Email:  gzfgeh@sina.com
 * Blog:   http://blog.csdn.net/u011370933
 * Github: https://github.com/gzfgeh
 */
public class JsonUtils {
    private static Gson mGson = new Gson();

    /**
     * 将对象准换为json字符串
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String serialize(T object) {
        return mGson.toJson(object);
    }

    /**
     * 将json字符串转换为对象
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json对象转换为实体对象
     * @param json
     * @param clz
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deserialize(JsonObject json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json字符串转换为对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Type type) throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }


    public static List<NewsBean> readJsonNewsBeans(String res, String value) {
        List<NewsBean> beans = new ArrayList<NewsBean>();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(value);
            if(jsonElement == null) {
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                if (jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) {
                    continue;
                }
                if (jo.has("TAGS") && !jo.has("TAG")) {
                    continue;
                }

                if (!jo.has("imgextra")) {
                    NewsBean news = deserialize(jo, NewsBean.class);
                    beans.add(news);
                }
            }
        } catch (Exception e) {
            LogUtils.e("TAG", "readJsonNewsBeans error");
        }
        return beans;
    }

    public static NewsDetailBean readJsonNewsDetailBeans(String res, String docId) {
        NewsDetailBean newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if(jsonElement == null) {
                return null;
            }
            newsDetailBean = deserialize(jsonElement.getAsJsonObject(), NewsDetailBean.class);
        } catch (Exception e) {
            LogUtils.e("TAG", "readJsonNewsBeans error");
        }
        return newsDetailBean;
    }

    public static List<ImageBean> readJsonImageBeans(String res) {
        List<ImageBean> beans = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(res).getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                ImageBean news = JsonUtils.deserialize(jo, ImageBean.class);
                beans.add(news);
            }
        } catch (Exception e) {
            LogUtils.e("TAG", "readJsonImageBeans error");
        }
        return beans;
    }


    /**
     * 从定位的json字串中获取城市
     * @param json
     * @return
     */
    public static String getCity(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        JsonElement status = jsonObj.get("status");
        if(status != null && "OK".equals(status.getAsString())) {
            JsonObject result = jsonObj.getAsJsonObject("result");
            if(result != null) {
                JsonObject addressComponent = result.getAsJsonObject("addressComponent");
                if(addressComponent != null) {
                    JsonElement cityElement = addressComponent.get("city");
                    if(cityElement != null) {
                        return cityElement.getAsString().replace("市", "");
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取天气信息
     * @param json
     * @return
     */
    public static List<WeatherBean> getWeatherInfo(String json) {
        List<WeatherBean> list = new ArrayList<WeatherBean>();
        if (TextUtils.isEmpty(json)) {
            return list;
        }
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        String status = jsonObj.get("status").getAsString();
        if("1000".equals(status)) {
            JsonArray jsonArray = jsonObj.getAsJsonObject("data").getAsJsonArray("forecast");
            for (int i = 0; i < jsonArray.size(); i++) {
                WeatherBean weatherBean = getWeatherBeanFromJson(jsonArray.get(i).getAsJsonObject());
                list.add(weatherBean);
            }
        }
        return list;
    }

    private static WeatherBean getWeatherBeanFromJson(JsonObject jsonObject) {

        String temperature = jsonObject.get("high").getAsString() + " " + jsonObject.get("low").getAsString();
        String weather = jsonObject.get("type").getAsString();
        String wind = jsonObject.get("fengxiang").getAsString();
        String date = jsonObject.get("date").getAsString();

        WeatherBean weatherBean = new WeatherBean();

        weatherBean.setDate(date);
        weatherBean.setTemperature(temperature);
        weatherBean.setWeather(weather);
        weatherBean.setWeek(date.substring(date.length()-3));
        weatherBean.setWind(wind);
        weatherBean.setImageRes(getWeatherImage(weather));
        return weatherBean;
    }

    public static int getWeatherImage(String weather) {
        if (weather.equals("多云") || weather.equals("多云转阴") || weather.equals("多云转晴")) {
            return R.drawable.biz_plugin_weather_duoyun;
        } else if (weather.equals("中雨") || weather.equals("中到大雨")) {
            return R.drawable.biz_plugin_weather_zhongyu;
        } else if (weather.equals("雷阵雨")) {
            return R.drawable.biz_plugin_weather_leizhenyu;
        } else if (weather.equals("阵雨") || weather.equals("阵雨转多云")) {
            return R.drawable.biz_plugin_weather_zhenyu;
        } else if (weather.equals("暴雪")) {
            return R.drawable.biz_plugin_weather_baoxue;
        } else if (weather.equals("暴雨")) {
            return R.drawable.biz_plugin_weather_baoyu;
        } else if (weather.equals("大暴雨")) {
            return R.drawable.biz_plugin_weather_dabaoyu;
        } else if (weather.equals("大雪")) {
            return R.drawable.biz_plugin_weather_daxue;
        } else if (weather.equals("大雨") || weather.equals("大雨转中雨")) {
            return R.drawable.biz_plugin_weather_dayu;
        } else if (weather.equals("雷阵雨冰雹")) {
            return R.drawable.biz_plugin_weather_leizhenyubingbao;
        } else if (weather.equals("晴")) {
            return R.drawable.biz_plugin_weather_qing;
        } else if (weather.equals("沙尘暴")) {
            return R.drawable.biz_plugin_weather_shachenbao;
        } else if (weather.equals("特大暴雨")) {
            return R.drawable.biz_plugin_weather_tedabaoyu;
        } else if (weather.equals("雾") || weather.equals("雾霾")) {
            return R.drawable.biz_plugin_weather_wu;
        } else if (weather.equals("小雪")) {
            return R.drawable.biz_plugin_weather_xiaoxue;
        } else if (weather.equals("小雨")) {
            return R.drawable.biz_plugin_weather_xiaoyu;
        } else if (weather.equals("阴")) {
            return R.drawable.biz_plugin_weather_yin;
        } else if (weather.equals("雨夹雪")) {
            return R.drawable.biz_plugin_weather_yujiaxue;
        } else if (weather.equals("阵雪")) {
            return R.drawable.biz_plugin_weather_zhenxue;
        } else if (weather.equals("中雪")) {
            return R.drawable.biz_plugin_weather_zhongxue;
        } else {
            return R.drawable.biz_plugin_weather_duoyun;
        }
    }

}
