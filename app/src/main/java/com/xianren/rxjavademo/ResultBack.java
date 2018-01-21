package com.xianren.rxjavademo;

import rx.Observer;

/**
 * Created by xianren on 2017/12/1.
 */

public abstract class ResultBack<T> implements Observer<T> {
     abstract void succeed(T data);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
        succeed(t);



    }
}
