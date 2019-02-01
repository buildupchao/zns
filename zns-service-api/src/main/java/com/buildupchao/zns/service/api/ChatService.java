package com.buildupchao.zns.service.api;

import com.buildupchao.zns.api.annotation.ZnsClient;

/**
 * @author buildupchao
 *         Date: 2019/2/1 15:59
 * @since JDK 1.8
 */

@ZnsClient
public interface ChatService {

    String send();

    String send(String userName, String message);

    String sendWithError(String message);
}
