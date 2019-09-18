package com.buildupchao.zns.zk.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;

/**
 * @author buildupchao
 * @date 2019/2/1 02:52
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderService implements Serializable {
    private String serverIp;
    private int serverPort;
    private int networkPort;

    private long timeout;
    // the weight of service provider
    private int weight;

    private int workerThreads;
    private String appKey;
    private String groupName;

    public ProviderService newBean() {
        ProviderService target = new ProviderService();
        target.setServerIp(serverIp);
        target.setServerPort(serverPort);
        target.setNetworkPort(networkPort);
        target.setTimeout(timeout);
        target.setWeight(weight);
        target.setWorkerThreads(workerThreads);
        target.setAppKey(appKey);
        target.setGroupName(groupName);
        return target;
    }
}
