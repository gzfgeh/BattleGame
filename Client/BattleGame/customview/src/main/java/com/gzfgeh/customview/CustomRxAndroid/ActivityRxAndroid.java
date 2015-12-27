package com.gzfgeh.customview.CustomRxAndroid;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.gzfgeh.customview.R;
import com.gzfgeh.customview.threadpool.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by guzhenfu on 15/12/27.
 */
public class ActivityRxAndroid extends BaseActivity {
    @Bind(R.id.rx_android)
    Button rxAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        rxAndroid.setOnClickListener(v -> {
            Observable.just("Hello Rxjava")
                    .subscribe(s -> Toast.makeText(ActivityRxAndroid.this,
                            s, Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_rx_android;
    }
}
