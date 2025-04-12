package com.zmbs.common.result;

/**
 * 返回码接口
 */
public interface IResultCode {
    /**
     * 获取返回码
     */
    Integer getCode();

    /**
     * 获取返回信息
     */
    String getMessage();
} 