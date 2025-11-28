package com.yxs.gateway.entity;

import lombok.Data;

/**
 * result统一
 * 
 * @author: ChickenWing
 * @date: 2023/10/28
 */
@Data
public class Result<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    public static Result ok() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("成功");
        return result;
    }

    public static <T> Result ok(T data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(500);
        result.setMessage("失败");
        return result;
    }

    public static <T> Result fail(T data) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(500);
        result.setMessage("失败");
        result.setData(data);
        return result;
    }

    public static Result fail(Integer code,String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
