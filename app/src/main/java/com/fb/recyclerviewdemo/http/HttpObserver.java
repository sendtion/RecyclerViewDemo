package com.fb.recyclerviewdemo.http;

import android.util.Log;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Description:
 * CreateTime: 2018/7/24 17:52
 * Author: ShengDecheng
 */

public class HttpObserver<T> implements Observer<T> {

    private HttpOnNextListener<T> onNextListener;
    private Disposable disposable;

    public HttpObserver(HttpOnNextListener<T> onNextListener){
        this.onNextListener = onNextListener;
    }

    /**
     * 开始订阅
     * @param d
     */
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
    }

    /**
     * 数据处理
     * @param t
     */
    @Override
    public void onNext(@NonNull T t) {
        if (onNextListener != null){
            onNextListener.onSuccess(t);
        }
    }

    /**
     * 异常处理
     * @param e
     */
    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("@@@", "onError: " + e.getMessage() );
//        if (!NetworkUtils.isConnected()) {
//            errMsg = "网络连接出错,";
//        } else if (e instanceof APIException) {
//            APIException exception = (APIException) e;
//            errMsg = exception.getMessage() + ", ";
//        } else if (e instanceof HttpException) {
//            errMsg = "网络请求出错,";
//        } else if (e instanceof IOException) {
//            errMsg = "网络出错,";
//        }

        String errMsg = "";
        //如果网络连接正常，判断相对应的错误信息
        if (e != null) {
            if (e instanceof SocketTimeoutException) {
                errMsg = "连接超时,请重新连接";
            } else if (e instanceof ConnectException) {
                errMsg = "连接异常,请检查网络后重新连接";
            } else if (e instanceof UnknownHostException) {
                errMsg = "未知主机";
            } else if (e instanceof JsonSyntaxException) {
                errMsg = "解析异常";
            } else if (e instanceof HttpException) {
                int httpErrorCode = ((HttpException) e).response().code();
                if (httpErrorCode == 401) {
                    errMsg = "授权失败401";
                } else if (httpErrorCode == 404) {
                    errMsg = "无效的请求路径404";
                } else if (httpErrorCode == 500) {
                    errMsg = "服务器异常500";
                } else {
                    errMsg = e.getMessage();
                }
            } else if (e instanceof HttpRuntimeException) {
                //运行时抛出的异常，当返回的json
                String errorCode = ((HttpRuntimeException) e).getErrorCode();
                errMsg = e.getMessage();
                //LogManagerUtil.error(errorCode);
            } else {
                errMsg = e.getMessage();
            }
        } else {
            errMsg = "e is null";
        }
        //Toast.makeText(Utils.getApp(), "" + errMsg, Toast.LENGTH_SHORT).show();

        if (onNextListener != null){
            onNextListener.onError(e);
        }

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 订阅完成
     */
    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
