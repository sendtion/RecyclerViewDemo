package com.fb.recyclerviewdemo.http;

import com.fb.recyclerviewdemo.app.MyApplication;
import com.fb.recyclerviewdemo.entry.Article;
import com.fb.recyclerviewdemo.entry.ArticleData;
import com.fb.recyclerviewdemo.entry.MyJoke;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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
    private static ClearableCookieJar cookieJar;

    private HttpMethods(){
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.app));
        /**
         * 构造函数私有化
         * 并在构造函数中进行retrofit的初始化
         */
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();
        //client.newBuilder().connectTimeout(TIME_OUT, TimeUnit.SECONDS);

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

    /**
     * 退出登录后清除cookie
     */
    public static void clearCookie() {
        if (cookieJar != null) {
            cookieJar.clear();
        }
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

    /**
     * 使用RxJava2请求
     * @param page
     * @param observer
     */
    public void getArticleData3(int page, Observer<Article> observer){
        httpService.getArticleData3(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 对Observer进行封装
     * @param page
     * @param observer
     */
    public void getArticleData4(int page, HttpObserver<Article> observer){
        httpService.getArticleData3(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 使用Map操作符
     * @param page
     * @param observer
     */
    public void getArticleData5(int page, HttpObserver<ArticleData> observer){
        httpService.getArticleData4(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<HttpResponseSuccess<ArticleData>, ArticleData>() {
                    @Override
                    public ArticleData apply(@NonNull HttpResponseSuccess<ArticleData> articleDataHttpResponseSuccess) throws Exception {
                        return articleDataHttpResponseSuccess.getData();
                    }
                })
                .subscribe(observer);
    }
}
