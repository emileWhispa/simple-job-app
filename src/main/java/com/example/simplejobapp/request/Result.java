package com.example.simplejobapp.request;


import com.example.simplejobapp.enums.ResultCodeEnum;

public class Result<T> {


    protected T data;


    protected Integer code;

    protected String message;

    public Result() {
        this.message = ResultCodeEnum.SUCCESS.getDesc();
        this.code = ResultCodeEnum.SUCCESS.getCode();

    }

    public Result(Integer code) {
        this.code = code;
    }


    public Result(String message) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = message;
    }

    public Result(T data) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getDesc();
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(T data, String message) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.data = data;
        this.message = message;
    }

    public Result(T data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.message = ResultCodeEnum.SUCCESS.getDesc();
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }
}
