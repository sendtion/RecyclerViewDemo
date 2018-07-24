package com.fb.recyclerviewdemo.http;

/**
 * Description:
 * CreateTime: 2018/7/24 18:03
 * Author: ShengDecheng
 */

public interface HttpOnNextListener<T> {

    void onSuccess(T t);

    void onError(Throwable e);
}
