package com.buildupchao.zns.client.util;

import java.util.UUID;

/**
 * @author buildupchao
 *         Date: 2019/2/1 12:49
 * @since JDK 1.8
 */
public class RequestIdUtil {

    public static String requestId() {
        return UUID.randomUUID().toString();
    }
}
