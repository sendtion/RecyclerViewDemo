package com.fb.recyclerviewdemo.http;

import com.fb.recyclerviewdemo.entry.Article;
import com.fb.recyclerviewdemo.entry.MyJoke;
import com.fb.recyclerviewdemo.entry.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Description:
 * CreateTime: 2018/6/11 10:39
 * Author: ShengDecheng
 */

public interface HttpService {

    // 首页文章列表
    @GET("article/list/{page}/json")
    Call<ResponseBody> getArticleData(@Path("page") int page);

    @GET("article/list/{page}/json")
    Call<Article> getArticleData2(@Path("page") int page);

    @GET("article/list/{page}/json")
    Observable<Article> getArticleData3(@Path("page") int page);
    //Observable<ArticleData> getArticleData3(@Path("page") int page);
    //Observable<HttpResponseSuccess<ArticleData>> getArticleData3(@Path("page") int page);

    @GET("xiaohua.json")
    Observable<List<MyJoke>> getJoke();

    /**
     * method：网络请求的方法（区分大小写）
     * path：网络请求地址路径
     * hasBody：是否有请求体
     */
    @HTTP(method = "GET", path = "article/list/{page}/json", hasBody = false)
    Call<ResponseBody> getArticleData4(@Path("page") int page);
    // {id} 表示是一个变量
    // method 的值 retrofit 不会做处理，所以要自行保证准确

    // 用户登录
    @POST("user/login")
    Call<User> userLogin(@Field("username") String username, @Field("password") String password);
    // 用户注册
    @GET("user/register")
    Call<ResponseBody> userRegister(@Field("username") String username, @Field("password") String password, @Field("password") String repassword);

    /*********************************************************************************/

    // @Header
    @GET("user")
    Call<ResponseBody> getUser1(@Header("Authorization") String authorization);

    // @Headers
    @Headers("Authorization: authorization")
    @GET("user")
    Call<ResponseBody> getUser2();
    // 以上的效果是一致的。
    // 区别在于使用场景和使用方式
    // 1. 使用场景：@Header用于添加不固定的请求头，@Headers用于添加固定的请求头
    // 2. 使用方式：@Header作用于方法的参数；@Headers作用于方法

    /*********************************************************************************/

    /**
     *表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);

    /**
     * Map的key作为表单的键
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);

    /*********************************************************************************/

    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    /**
     * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
     * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
     * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload3(@PartMap Map<String, RequestBody> args);

    /*********************************************************************************/

    @GET("/")
    Call<String> cate(@Query("cate") String cate);
    // 其使用方式同 @Field与@FieldMap，这里不作过多描述
    // @Query和@QueryMap

    /*********************************************************************************/

    @GET("users/{user}/repos")
    Call<ResponseBody>  getBlog(@Path("user") String user );
    // 访问的API是：https://api.github.com/users/{user}/repos
    // 在发起请求时， {user} 会被替换为方法的第一个参数 user（被@Path注解作用）

    /*********************************************************************************/

    @GET
    Call<ResponseBody> testUrlAndQuery(@Url String url, @Query("showAll") boolean showAll);
    // 当有URL注解时，@GET传入的URL就可以省略
    // 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供

    /*********************************************************************************/

}
