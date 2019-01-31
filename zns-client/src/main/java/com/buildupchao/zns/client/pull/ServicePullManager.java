package com.buildupchao.zns.client.pull;

import com.buildupchao.zns.client.annotation.ZnsClient;
import com.buildupchao.zns.client.util.SpringBeanFactory;
import com.buildupchao.zns.client.util.ZKit;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author buildupchao
 *         Date: 2019/2/1 02:32
 * @since JDK 1.8
 */
@Component
public class ServicePullManager {

    @Autowired
    private ZKit zKit;

    public void pullServiceFromZK() {
        Map<String, Object> beans = SpringBeanFactory.getClassListByAnnotationClass(ZnsClient.class);
        if (MapUtils.isEmpty(beans)) {
            return;
        }
        for (Object v : beans.values()) {
            String serviceName = v.getClass().getName();
            zKit.subscribeZKEvent(serviceName);
        }
    }
}
