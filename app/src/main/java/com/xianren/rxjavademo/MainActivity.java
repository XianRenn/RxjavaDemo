package com.xianren.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
//       创建事件源
        Student student = new Student();
        student.name = "xxxxx";
        Subscriber<Student> obj = null;
        Observable.just(student).map(new Func1() {
            @Override
            public Object call(Object o) {
//                接收just传递的数据
                Student student1 = (Student) o;
                String str = student1.name;
                Log.i("flag", "jhfj");
                return str;
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Object o) {
//map转换数据类型，接收call返回的数据
                Student obj = (Student) o;
                Log.i("flag", "jhfj");
            }
        });


    }
}
