package com.fb.recyclerviewdemo.http;

import com.fb.recyclerviewdemo.entry.Article;
import com.fb.recyclerviewdemo.entry.MyJoke;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description: 来福岛开放接口
 * CreateTime: 2018/7/24 15:51
 * Author: ShengDecheng
 */

public class HttpMethods {
    //来福岛：http://api.laifudao.com/open/
    //玩安卓：http://www.wanandroid.com/
    private static final String BASE_URL = "http://www.wanandroid.com/";
    private static final int TIME_OUT = 5;
    private static HttpMethods httpMethods;
    private Retrofit retrofit;
    private HttpService httpService;

    private HttpMethods(){
        /**
         * 构造函数私有化
         * 并在构造函数中进行retrofit的初始化
         */
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(TIME_OUT, TimeUnit.SECONDS);

        // 自定义Gson转换器，这里格式化时间
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        /**
         * 由于retrofit底层的实现是通过okhttp实现的，所以可以通过okHttp来设置一些连接参数，如超时等
         */
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    private static class signalInstance {
        public static final HttpMethods instance = new HttpMethods();
    }

    public static HttpMethods getInstance2(){
        return signalInstance.instance;
    }

    public static HttpMethods getInstance(){
        if (httpMethods == null){
            synchronized (HttpMethods.class){
                if (httpMethods == null){
                    httpMethods = new HttpMethods();
                }
            }
        }
        return httpMethods;
    }

    public void getJoke(Observer<List<MyJoke>> observer){
        httpService.getJoke()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getArticleData3(int page, Observer<Article> observer){
        httpService.getArticleData3(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
