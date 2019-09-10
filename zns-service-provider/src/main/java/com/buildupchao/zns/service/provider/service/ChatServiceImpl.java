package com.buildupchao.zns.service.provider.service;

import com.buildupchao.zns.api.annotation.ZnsService;
import com.buildupchao.zns.common.exception.ZnsException;
import com.buildupchao.zns.service.api.ChatService;

/**
 * @author buildupchao
 * @date 2019/2/1 11:33
 * @since JDK 1.8
 */
@ZnsService(cls = ChatService.class)
public class ChatServiceImpl implements ChatService {

    @Override
    public String send() {
        return "Nobody send message!";
    }

    @Override
    public String send(String userName, String message) {
        return String.format("【%s】:%s", userName, message);
    }

    @Override
    public String sendWithError(String message) {
        throw new ZnsException("test error!" + message);
    }
}
