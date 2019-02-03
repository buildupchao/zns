package com.buildupchao.zns.monitor.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author buildupchao
 *         Date: 2019/2/4 05:41
 * @since JDK 1.8
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("'${swagger.enable}' == 'true'")
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.buildupchao.zns.monitor.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("zns-monitor api")
                .description("zns-monitor zk api")
                .termsOfServiceUrl("https://buildupchao.cn")
                .contact("buildupchao")
                .version("1.0.0")
                .build();
    }

}
