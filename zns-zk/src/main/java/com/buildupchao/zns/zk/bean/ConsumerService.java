package com.buildupchao.zns.zk.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author buildupchao
 * @date 2019/2/17 17:15
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerService implements Serializable {

    private String consumerIp;
    private int consumerPort;
    private long timeout;

    private String appKey;
    private String groupName = "default";
}
