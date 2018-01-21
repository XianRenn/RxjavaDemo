package com.xianren.rxjavademo;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * Created by guchangyou on 2018/1/21.
 */

public class NetRequest {
    static NetRequest netRequest;
    public static final Charset UTF8 = Charset.forName("UTF-8");
    private OkHttpClient okHttpClient = getOkHttpClient();
    private MyInterceptor interceptor;
  public static   Context context;

    private NetRequest() {
    }

    public static NetRequest getInstance(Context contex) {
        context=contex;
        if (netRequest == null)
            return new NetRequest();
        else
            return netRequest;

    }

    public <T> T getApi(Class<T> api) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.baseUrl)
                .addConverterFactory(GsonConverFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        return retrofit.create(api);
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            interceptor = new MyInterceptor();
           /* if (BuildConfig.DEBUG) {
                okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(50, TimeUnit.SECONDS).writeTimeout(50, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).build();
            } else {*/
            //proxy(Proxy.NO_PROXY)okHttp防止第三方抓包
//            okHttpClient = new OkHttpClient.Builder().proxy(Proxy.NO_PROXY).addInterceptor(interceptor).readTimeout(50, TimeUnit.SECONDS).writeTimeout(50, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).build();
            okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(50, TimeUnit.SECONDS).writeTimeout(50, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).build();
        }
        return okHttpClient;
    }

    class MyInterceptor implements Interceptor {
        final LocalSession mSession = LocalSession.get(context);

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder newBuilder = chain.request().newBuilder();
            try {
                newBuilder.addHeader("version", context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            newBuilder.addHeader("client", "android");
            newBuilder.addHeader("channel", "0");
            newBuilder.addHeader("build", mSession.getOsVersion());
            newBuilder.addHeader("screenSize", mSession.getScreenHeight(context) + "x" + mSession.getScreenWidth(context));
            if (mSession.getSafeToken() != null) {                                 //如果有safeToken,就加入请求头
                newBuilder.addHeader("safeToken", mSession.getSafeToken());
            }

            if (mSession.getToken() != null) {                                 //如果有safeToken,就加入请求头
                newBuilder.addHeader("deviceToken", mSession.getToken());
            }
            Request newRequest = newBuilder.build();
            StringBuilder builder = new StringBuilder();
            /*builder.append("-----------[ ").append(newRequest.url()).append("] request-----------").append("\r\n");
            Utils.E("okHttp", builder.toString());*/

            Response response = chain.proceed(newRequest);
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            String str = buffer.clone().readString(charset);
           /* builder.delete(0, builder.length() - 1);
            builder.append("-----------[ ").append(newRequest.url()).append("] reponse-----------").append("\r\n");
            builder.append("response->").append(str);
            Utils.E("okHttp", builder.toString());*/
            return response;
        }

    }
}



