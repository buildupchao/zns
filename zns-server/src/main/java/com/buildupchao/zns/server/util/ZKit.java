package com.buildupchao.zns.server.util;

import com.buildupchao.zns.server.config.ZnsServerConfiguration;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author buildupchao
 *         Date: 2019/2/1 00:33
 * @since JDK 1.8
 */
@Component
public class ZKit {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ZnsServerConfiguration znsServerConfiguration;


    public void createRootNode() {
        boolean exists = zkClient.exists(znsServerConfiguration.getZkRoot());
        if (!exists) {
            zkClient.createPersistent(znsServerConfiguration.getZkRoot());
        }
    }

    public void createNode(String path) {
        boolean exists = zkClient.exists(znsServerConfiguration.getZkRoot() + File.separator + path);
        if (!exists) {
            zkClient.createEphemeral(path);
        }
    }
}
