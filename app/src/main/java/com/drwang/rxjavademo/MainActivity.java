package com.drwang.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        //1, 创建被观察的对象
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>(){
            //在订阅时被调用
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("哈哈哈");
                subscriber.onCompleted();
            }
        });
        //2, 创建观察者对象  订阅
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: ");
                tv.setText(s);
            }
        };
        //3, 订阅
        observable.subscribe(subscriber);
        
    }
}
