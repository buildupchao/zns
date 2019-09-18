package com.buildupchao.zns.service.consumer.controller;

import com.buildupchao.zns.client.util.SpringBeanFactory;
import com.buildupchao.zns.service.api.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author buildupchao
 * @date 2019/2/1 15:13
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/testservice")
    public void test() {
        ChatService chatService = SpringBeanFactory.getBean(ChatService.class);

        System.out.println(chatService.send());

        System.out.println(chatService.send("buildupchao", "Happy Spring Festival!"));

        try {
            chatService.sendWithError("No message");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
