package com.gzfgeh.customview.CustomHttp;

import android.os.Bundle;
import android.widget.TextView;

import com.gzfgeh.customview.CustomSwipeBack.SwipeBackActivity;
import com.gzfgeh.customview.R;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

/**
 * Created by guzhenfu on 15/12/14.
 */
public class ActivityCustomHttp extends SwipeBackActivity {
    private TextView httpGetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpGetText = (TextView) findViewById(R.id.text);

        getHtml();
    }

    private void getHtml() {
        OkHttpUtils.get().url("http://www.baidu.com").build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                httpGetText.setText(response);
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_custom_http;
    }
}
