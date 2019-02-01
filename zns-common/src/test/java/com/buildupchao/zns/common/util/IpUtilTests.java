package com.buildupchao.zns.common.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author buildupchao
 *         Date: 2019/1/31 21:11
 * @since JDK 1.8
 */
public class IpUtilTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtilTests.class);

    @Test
    public void testGetHost() {
        LOGGER.info("Test getHost: {}", IpUtil.getHostAddress());
    }
}
