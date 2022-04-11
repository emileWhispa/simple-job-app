package com.example.simplejobapp.enums;

public enum ResultCodeEnum {
    SUCCESS(1, "success"),
    ORDER_PAY_ERROR(9008, "payment failure"),
    REFUND_PAY_ERROR(9009, "payment failure"),
    QUERY_ERROR(9005, "query fails"),
    ERROR(9999, "unknown error"),
    APPLICATION_ERROR(9000, "application error"),
    VALIDATE_ERROR(9001, "validate error"),
    SERVICE_ERROR(9002, "service error"),
    CACHE_ERROR(9003, "cache error"),
    DAO_ERROR(9004, "dao error"),
    NEED_LOGIN(10, "need login"),
    SYSTEM_ERROR(100, "Other system anomalies"),
    ILLEGAL_ARGUMENT(2, "illegal argument");


    private final int code;
    private final String desc;

    ResultCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
