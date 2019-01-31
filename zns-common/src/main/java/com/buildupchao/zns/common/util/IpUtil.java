package com.buildupchao.zns.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author buildupchao
 *         Date: 2019/1/31 21:05
 * @since JDK 1.8
 */
public class IpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtil.class);

    public static String getHostAddress() {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
            return host;
        } catch (UnknownHostException e) {
            LOGGER.error("Cannot get server host.", e);
        }
        return host;
    }
}
