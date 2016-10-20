package com.drwang.rxjavademo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private static final String TAG = "MainActivity";
    private Subscriber<String> mSubscriber;
    private Observable<String> mObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        //1, 创建被观察的对象
        //在订阅时被调用
        mObservable = Observable.create(new Observable.OnSubscribe<String>(){
            //在订阅时被调用
            @Override
            public void call(Subscriber<? super String> subscriber) {
                showThread("请求数据");
                SystemClock.sleep(5000);

                subscriber.onNext("哈哈哈");

                subscriber.onCompleted();

            }
        });
        //2, 创建观察者对象  订阅
        mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
                showThread("onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                tv.setText(s);
                showThread("onNext");
            }
        };

        //3, 订阅 , 产生调用
//        mObservable.subscribe(mSubscriber);

        //4.异步和同步
        mObservable.subscribeOn(Schedulers.io()) //  call方法在子线程被调用
                .observeOn(AndroidSchedulers.mainThread())// 观察者的方法在主线程被调用
                .subscribe(mSubscriber);
    }
    public void showThread(String str) {
        Log.i(TAG, "showThread: str Thread = " + Thread.currentThread().getName() + "str = " + str);
    }
}
