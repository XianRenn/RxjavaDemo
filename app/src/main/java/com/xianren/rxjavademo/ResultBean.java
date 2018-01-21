package com.xianren.rxjavademo;

import java.io.Serializable;

/**
 * Created by guchangyou on 2018/1/21.
 */

public class ResultBean implements Serializable {

    public String statu;
    public String msg;
    public User data;
    public class User {
        public String userName;
        public String userLevel;
    }

}
