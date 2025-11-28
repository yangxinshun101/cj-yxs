package com.yxs.oss.entity;

import lombok.Data;

@Data
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public static Result success() {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("成功");
        return result;
    }

    public static <T> Result success(T data) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }


    public static Result fail() {
        Result result = new Result();
        result.setCode(500);
        result.setMessage("失败");
        return result;
    }

    public static <T> Result fail(T data) {
        Result result = new Result();
        result.setCode(500);
        result.setMessage("失败");
        result.setData(data);
        return result;
    }

    public static Result fail(Integer code,String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
