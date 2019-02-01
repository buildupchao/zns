# ZNS
What's the ZNS is an implementation of distribution RPC.

## 1 Introduction

ZNS是一个分布式RPC框架。

- 通过Zookeeper实现服务的注册与发现

- 基于Netty实现底层网络通信

- 采用Spring实现最小依赖注入

## 2 Feature LIST

* [x]服务注册
* [x]服务发现
* [x]网络通信层
* [x]服务异步调用
* [x]灵活的水平扩容、缩容
* [ ]服务生产者与Zookeeper心跳机制
* [ ]更多的集群服务路由策略
* [ ]服务监控
* [ ]服务降级
* [ ]服务版本控制


## 3 Architecture

![](https://github.com/buildupchao/ImgStore/blob/master/blog/RPC%E5%8E%9F%E7%90%86.png?raw=true)

![](https://github.com/buildupchao/ImgStore/blob/master/blog/zns.png?raw=true)

### 3.1 zns-server

`ZNS` 服务端：提供了服务注册，接受`client`端Connector连接，反射调用服务等

### 3.2 zns-client

`ZNS` 客户端：提供了服务发现，请求连接`server`端Acceptor，服务代理等

## 4 How to use

### 4.1 服务提供端

- 添加 `zns-server` 依赖包

```
<dependency>
    <groupId>com.buildupchao</groupId>
    <artifactId>zns-service-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <exclusions>
        <exclusion>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </exclusion>
    </exclusions>
</dependency>    
```

- 添加扫描包路径`ZnsServerPackage.class`以及启动调用方法`znsServerRunner.run()`

```
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
```

- 在application.yml中配置属性

```
zns:
  server:
      zk:
        root: /zns
        addr: localhost:2181
        switch: true
  network:
      port: 8888
```

### 4.2 服务消费端

- 端添加 `zns-client` 依赖包

```
<dependency>
    <groupId>com.buildupchao</groupId>
    <artifactId>zns-client</artifactId>
    <version>1.0-SNAPSHOT</version>
    <exclusions>
        <exclusion>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

- 添加扫描包路径`ZnsClientPackage.class`以及启动调用方法`znsClientRunner.run()`

```
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
```

- 在application.yml中配置属性

```
zns:
  client:
      zk:
        root: /zns
        addr: localhost:2181
        switch: true
      api:
        package: com.buildupchao.zns.service.api
```

### 4.3 公共API`zns-service-api`需要引入`zns-api`包依赖

```
<dependency>
    <groupId>com.buildupchao</groupId>
    <artifactId>zns-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Contact Me

- [buildupchao@gmail.com](buildupchao@gmail.com)