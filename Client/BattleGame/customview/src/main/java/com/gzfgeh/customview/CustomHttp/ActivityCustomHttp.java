package com.gzfgeh.customview.CustomHttp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.gzfgeh.customview.CustomSwipeBack.SwipeBackActivity;
import com.gzfgeh.customview.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

/**
 * Created by guzhenfu on 15/12/14.
 */
public class ActivityCustomHttp extends SwipeBackActivity{
    private TextView httpGetText;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpGetText = (TextView) findViewById(R.id.text);

        mOkHttpClient = new OkHttpClient();
        httpGetMethod();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_custom_http;
    }

    private void httpGetMethod(){
        final Request request = new Request.Builder().url("http://www.baidu.com")
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        httpGetText.setText(s);
                        Toast.makeText(ActivityCustomHttp.this, s, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void httpPostMethod(){
//        Request request = buildMultipartFormRequest(
//                url, new File[]{file}, new String[]{fileKey}, null);
//        FormEncodingBuilder builder = new FormEncodingBuilder();
//        builder.add("username","张鸿洋");
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(builder.build())
//                .build();
//        mOkHttpClient.newCall(request).enqueue(new Callback(){});
    }
}