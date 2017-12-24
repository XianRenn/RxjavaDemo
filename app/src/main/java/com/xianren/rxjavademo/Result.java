package com.xianren.rxjavademo;

import rx.Observer;

/**
 * Created by xianren on 2017/12/1.
 */

public abstract class Result<T> implements Observer<T> {
    abstract void succeed(T data);
}
