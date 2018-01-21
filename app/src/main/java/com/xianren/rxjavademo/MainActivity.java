package com.xianren.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
        NetRequest.getInstance(this).getApi(FuntionApi.class)
                .userLogo("name1", "psw1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new ResultBack<ResultBean>() {
            @Override
            void succeed(ResultBean data) {
                String s=data.statu;
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();

            }
        });

    }
}
