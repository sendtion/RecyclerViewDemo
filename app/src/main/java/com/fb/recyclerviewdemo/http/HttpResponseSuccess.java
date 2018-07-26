package com.fb.recyclerviewdemo.http;

import com.google.gson.annotations.SerializedName;

/**
 * Description:
 * CreateTime: 2018/6/15 11:17
 * Author: ShengDecheng
 */

public class HttpResponseSuccess<T> {
    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("errorMsg")
    private String errorMsg;
    @SerializedName("data")
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}