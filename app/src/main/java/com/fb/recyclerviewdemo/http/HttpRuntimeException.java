package com.fb.recyclerviewdemo.http;

class HttpRuntimeException extends RuntimeException {
    private String errorCode;

    String getErrorCode() {
        return errorCode;
    }

    HttpRuntimeException(String errorCode, String errorMsg) {
        super(getApiExceptionMessage(errorCode, errorMsg));
        this.errorCode = errorCode;
    }

    //8999	参数类型有误//8998	原子封装信息错误//9996	查询无任何数据//9997	Token不正确或者已经过期//9998	非法请求//9999	未知错误
    private static String getApiExceptionMessage(String errorCode, String errorMsg) {
        String message;
        switch (errorCode) {
            case "8999":
                message = "参数类型有误";
                break;
            case "8998":
                message = "原子封装信息错误";
                break;
            case "9996":
                message = "查询无任何数据";
                break;
            case "9997":
                message = "Token不正确或者已经过期";
                break;
            case "9998":
                message = "非法请求";
                break;
            case "9999":
                message = "未知错误";
                break;
            default:
                message = errorMsg;
                break;
        }
        return message;
    }
}
