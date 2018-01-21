package com.xianren.rxjavademo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by guchangyou on 2018/1/21.1
 */

public interface FuntionApi {
    @GET(Urls.userLogo)
    Observable<ResultBean> userLogo(@Query("userName") String userName, @Query("userPsw") String userPsw);
}
