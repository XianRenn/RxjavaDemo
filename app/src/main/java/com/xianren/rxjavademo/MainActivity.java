package com.xianren.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Result<Student> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = new Result<Student>() {
            @Override
            void succeed(Student data) {
                Log.i("stu", data.name);
            }

            @Override
            public void onCompleted() {
                Log.i("stu", "stu");
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(Student student) {
                Log.i("stu", student.name);
            }
        };
    }

     public void test(View view) {
        getData(Student.class).map(new Func1() {
            @Override
            public Object call(Object o) {
//                可以接收事件源过来传递的数据
                Student stuObj = (Student) o;
                return stuObj;
            }
        }).subscribeOn(Schedulers.io()).subscribe(result);


    }

    public Observable<?> getData(Class T) {
        Student student;
        Teacher teacher;
        Object tem;
        if (T == Student.class) {
            student = new Student();
            student.name = "I am a student";
            tem = student;
        } else {
            teacher = new Teacher();
            teacher.name = "I am a teacher";
            tem = teacher;
        }
//       设置事件源
        Observable<?> obj = Observable.just(tem);
        return obj;
    }


}
