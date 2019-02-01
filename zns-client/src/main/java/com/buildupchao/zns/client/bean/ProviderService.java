package com.buildupchao.zns.client.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author buildupchao
 *         Date: 2019/2/1 02:52
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
}
