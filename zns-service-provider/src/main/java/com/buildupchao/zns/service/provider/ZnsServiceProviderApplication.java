package com.buildupchao.zns.service.provider;

import com.buildupchao.zns.server.ZnsServerPackage;
import com.buildupchao.zns.server.ZnsServerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackages = "com.buildupchao.zns.service.provider",
        basePackageClasses = ZnsServerPackage.class
)
@SpringBootApplication
public class ZnsServiceProviderApplication implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZnsServiceProviderApplication.class);

    @Autowired
    private ZnsServerRunner znsServerRunner;

    public static void main(String[] args) {
        SpringApplication.run(ZnsServiceProviderApplication.class, args);

        LOGGER.info("Zns service provider application startup successfully");

    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        znsServerRunner.run();
    }
}

