package com.fb.recyclerviewdemo.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fb.recyclerviewdemo.BaseActivity;
import com.fb.recyclerviewdemo.MyBaseAdapter;
import com.fb.recyclerviewdemo.MyPaddingDecoration;
import com.fb.recyclerviewdemo.MySectionDecoration;
import com.fb.recyclerviewdemo.R;
import com.fb.recyclerviewdemo.entry.Article;
import com.fb.recyclerviewdemo.entry.ArticleData;
import com.fb.recyclerviewdemo.entry.HttpResponseSuccess;
import com.fb.recyclerviewdemo.entry.TagBean;
import com.fb.recyclerviewdemo.http.HttpService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.media.CamcorderProfile.get;

/**
 * 玩Android首页文章列表
 * http://www.wanandroid.com/blog/show/2
 *
 * Retrofit使用教程
 * https://blog.csdn.net/carson_ho/article/details/73732076
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView mListArticle;
    private MyBaseAdapter baseAdapter;
    private List<Article.DataBean.DatasBean> mDatas;
    private List<TagBean> tagList;

    private HttpService httpService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

    }

    @Override
    protected void initView() {
        mListArticle = (RecyclerView) findViewById(R.id.rv_list_article);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mListArticle.setLayoutManager(layoutManager);
//        //mListArticle.addItemDecoration(new MyPaddingDecoration(this)); //设置分割线
//        mListArticle.addItemDecoration(new MySectionDecoration(this, null, new MySectionDecoration.DecorationCallback() {
//            @Override
//            public String getGroupId(int position) {
//                return null;
//            }
//
//            @Override
//            public String getGroupFirstLine(int position) {
//                return null;
//            }
//        }));
//
//        baseAdapter = new MyBaseAdapter();
//        baseAdapter.setDatas(mDatas);
//        mListArticle.setAdapter(baseAdapter);
    }

    @Override
    protected void initData() {
        initHttp();

        mDatas = new ArrayList<>();
    }

    @Override
    protected void loadData() {
        //getHomeData(0);
        getHomeData2(0);
        //getHomeData3(0);
    }

    private void setMyAdapter(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListArticle.setLayoutManager(layoutManager);
        //mListArticle.addItemDecoration(new MyPaddingDecoration(this)); //设置分割线
        mListArticle.addItemDecoration(new MySectionDecoration(this, tagList, new MySectionDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                // 返回标记id (即每一项对应的标志性的字符串)
                if (tagList.get(position).getName() != null){
                    return tagList.get(position).getName();
                }
                return "-1";
            }

            @Override
            public String getGroupFirstLine(int position) {
                // 获取同组中的第一个内容
                if (tagList.get(position).getName() != null){
                    return tagList.get(position).getName();
                }
                return "";
            }
        }));

        baseAdapter = new MyBaseAdapter();
        baseAdapter.setDatas(mDatas);
        mListArticle.setAdapter(baseAdapter);
    }

    private void setTagData(List<Article.DataBean.DatasBean> datas){
        tagList = new ArrayList<>();

        for (int i = 0; i < datas.size(); i++) {
            Article.DataBean.DatasBean datasBean = datas.get(i);
            TagBean tagBean = new TagBean();
            tagBean.setName(datasBean.getNiceDate());
            tagList.add(tagBean);
        }
        
    }

    private void initHttp() {
        // 自定义Gson转换器，这里格式化时间
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                // 设置根地址，应该以/结尾
                .baseUrl("http://www.wanandroid.com/")
                // 可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(GsonConverterFactory.create(gson)) // 支持Gson解析
                //.addConverterFactory(ProtoConverterFactory.create()) // 支持Prototocobuff解析
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava
                .build();

        httpService = retrofit.create(HttpService.class);
    }

    private void getHomeData(int page){
        Call<ResponseBody> call = httpService.getArticleData(page);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e(TAG, "onResponse1: " + response.body().string() );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure1: " + t.getMessage() );
            }
        });
    }

    private void getHomeData2(int page){
        Call<Article> call = httpService.getArticleData2(page);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                Log.e(TAG, "onResponse2: " + response.body().toString() );
                Article article = response.body();
                if (article != null){
                    Article.DataBean dataBean = article.getData();
                    if (dataBean != null){
                        List<Article.DataBean.DatasBean> datasBeanList = dataBean.getDatas();
                        setTagData(datasBeanList);

                        mDatas.clear();
                        mDatas.addAll(datasBeanList);

                        setMyAdapter();
                    }
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e(TAG, "onFailure2: " + t.getMessage() );
            }
        });
    }

    private void getHomeData3(int page){
        Call<HttpResponseSuccess<ArticleData>> call = httpService.getArticleData3(page);
        call.enqueue(new Callback<HttpResponseSuccess<ArticleData>>() {
            @Override
            public void onResponse(Call<HttpResponseSuccess<ArticleData>> call, Response<HttpResponseSuccess<ArticleData>> response) {
                Log.e(TAG, "onResponse3: " + response.body().getData().toString() );
            }

            @Override
            public void onFailure(Call<HttpResponseSuccess<ArticleData>> call, Throwable t) {
                Log.e(TAG, "onFailure3: " + t.getMessage() );
            }
        });
    }

    // @Header & @Headers
    private void getUser(){
        String token = "";
        Call<ResponseBody> call1 = httpService.getUser1(token);

        Call<ResponseBody> call2 = httpService.getUser2();
    }

    // @Field & @FieldMap
    private void testFormUrlEncoded(){
        // @Field
        Call<ResponseBody> call1 = httpService.testFormUrlEncoded1("Carson", 24);

        // @FieldMap
        // 实现的效果与上面相同，但要传入Map
        Map<String, Object> map = new HashMap<>();
        map.put("username", "Carson");
        map.put("age", 24);
        Call<ResponseBody> call2 = httpService.testFormUrlEncoded2(map);
    }

    private void testFileUpload(){
        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType, "Carson");
        RequestBody age = RequestBody.create(textType, "24");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");

        // @Part
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
        Call<ResponseBody> call3 = httpService.testFileUpload1(name, age, filePart);
        //ResponseBodyPrinter.printResponseBody(call3);

        // @PartMap
        // 实现和上面同样的效果
        Map<String, RequestBody> fileUpload2Args = new HashMap<>();
        fileUpload2Args.put("name", name);
        fileUpload2Args.put("age", age);
        //这里并不会被当成文件，因为没有文件名(包含在Content-Disposition请求头中)，但上面的 filePart 有
        //fileUpload2Args.put("file", file);
        Call<ResponseBody> call4 = httpService.testFileUpload2(fileUpload2Args, filePart); //单独处理文件
        //ResponseBodyPrinter.printResponseBody(call4);
    }

}
