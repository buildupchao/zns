package com.buildupchao.zns.common.exception;

import lombok.Getter;

/**
 * @author buildupchao
 * @date 2019/09/19 00:52
 * @since JDK 1.8
 */
@Getter
public enum StatusCode {
    SUCCESS(200, "OK"),

    NO_AVAILABLE_SERVICE_PROVINDER(100001, "no available service provider");


    private Integer code;
    private String description;

    StatusCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
