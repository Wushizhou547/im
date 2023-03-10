package com.djb.im.utils;


import lombok.Data;

import java.io.Serializable;


@Data
public class Result<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result code(int code) {
        this.code = code;
        return this;
    }

    public Result msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    /**
     * 构建分页数据返回
     */
    /*public static Result ofPage(Page page) {
        Map<String, Object> pageResult = new HashMap<>(4);
        pageResult.put("records", page.getRecords());
        pageResult.put("total", page.getTotal());
        pageResult.put("pageNo", page.getCurrent());
        pageResult.put("pageSize", page.getSize());
        return success(pageResult);
    }*/

    private static Result from(ResultCode resultCode) {
        return new Result().code(resultCode.getCode()).msg(resultCode.getMessage());
    }


    public static Result success() {
        return from(ResultCode.SUCCESS);
    }

    public static <T> Result success(T data) {
        return success().data(data);
    }

    public static Result fail(ResultCode code) {
        return from(code);
    }

    public static Result fail(int code, String msg) {
        return new Result(code, msg);
    }
}
