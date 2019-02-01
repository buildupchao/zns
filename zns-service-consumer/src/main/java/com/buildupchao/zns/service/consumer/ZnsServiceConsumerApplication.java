package com.buildupchao.zns.service.consumer;

import com.buildupchao.zns.client.ZnsClientPackage;
import com.buildupchao.zns.client.ZnsClientRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = "com.buildupchao.zns.service.consumer",
        basePackageClasses = ZnsClientPackage.class
)
@SpringBootApplication
public class ZnsServiceConsumerApplication implements ApplicationRunner {

    @Autowired
    private ZnsClientRunner znsClientRunner;

    public static void main(String[] args) {
        SpringApplication.run(ZnsServiceConsumerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        znsClientRunner.run();
    }
}

