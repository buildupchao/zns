package com.buildupchao.zns.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author buildupchao
 *         Date: 2019/1/31 18:19
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZnsResponse {
    private String requestId;
    private Object result;
    private Throwable cause;

    public boolean isError() {
        return cause != null;
    }
}
