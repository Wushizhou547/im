package com.djb.im.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ResultCode {

    //SUCCESS(200, "请求成功"),

    UNKNOWN(-1024, "请求失败"),

    SUCCESS(200, "请求成功"),

    BIZ_ERROR(1000, "业务异常"),

    NOT_AUTHENTICATE(1001, "未认证，请先登录"),

    FORBIDDEN(1002, "无权限访问，请联系管理员"),

    SERVER_ERROR(1005, "服务器繁忙"),

    REQUEST_ERROR(1010, "请求错误"),

    ARGUMENT_ERROR(1011, "参数错误"),

    UNSPECIFIED(1012, "请求的次数超出限制"),

    GET_REDIS_LOCK_ERROR(1101, "已存在业务，请等待完成"),

    THIRD_TOKEN_NOT_AUTHENTICATE(1112, "accessToken不合法"),

    ACCOUNT_NOT_EXIST(1113, "accessToken不合法");

    private final int code;

    private final String message;
}
