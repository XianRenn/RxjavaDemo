package com.xianren.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


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
        Observable.just(student).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber() {
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
                Toast.makeText(MainActivity.this, "fds", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
