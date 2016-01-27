package com.gzfgeh.customview.CustomRxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gzfgeh.customview.R;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by guzhenfu on 2016/1/27.
 */
public class ActivityRxJava extends Activity {
    @Bind(R.id.register)
    Button register;
    @Bind(R.id.just)
    Button just;
    @Bind(R.id.map)
    Button map;
    @Bind(R.id.from)
    Button from;
    @Bind(R.id.flat_map)
    Button flatMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_rxjava);
        ButterKnife.bind(this);

        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello rxjava");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(ActivityRxJava.this, s, Toast.LENGTH_SHORT).show();
            }
        };

        Observable<String> myObservable1 = Observable.just("Hello RxJava easy");
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(ActivityRxJava.this, s, Toast.LENGTH_SHORT).show();
            }
        };

        register.setOnClickListener(view -> myObservable1.subscribe(onNextAction));

        just.setOnClickListener(view -> Observable.just("Hello RxJava very easy easy")
                        .subscribe(s -> Toast.makeText(ActivityRxJava.this, s, Toast.LENGTH_SHORT).show()));

        map.setOnClickListener(view -> Observable.just("Hello RxJava very very easy")
                .map(s -> s + " write by gzf")
                .subscribe(s -> Toast.makeText(ActivityRxJava.this, s, Toast.LENGTH_SHORT).show()));

        String[] array = {"g", "z", "f"};
        from.setOnClickListener(view -> Observable.from(array)
                        .subscribe(s -> Toast.makeText(ActivityRxJava.this, s, Toast.LENGTH_SHORT).show()));

//        flatMap.setOnClickListener(view -> );
    }
}
