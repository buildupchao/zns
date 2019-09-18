package com.buildupchao.zns.client.runner;

import com.buildupchao.zns.client.bean.ChannelHolder;
import com.buildupchao.zns.client.bean.ProviderService;
import com.buildupchao.zns.client.cache.ServiceRouteCache;
import com.buildupchao.zns.client.cluster.ClusterStrategy;
import com.buildupchao.zns.client.cluster.engine.ClusterEngine;
import com.buildupchao.zns.client.config.ZnsClientConfiguration;
import com.buildupchao.zns.client.connector.ZnsClientConnector;
import com.buildupchao.zns.client.util.SpringBeanFactory;
import com.buildupchao.zns.common.bean.ZnsRequest;
import com.buildupchao.zns.common.exception.StatusCode;
import com.buildupchao.zns.common.exception.ZnsException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author buildupchao
 * @date 2019/2/1 12:53
 * @since JDK 1.8
 */
public class ZnsRequestManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZnsRequestManager.class);

    private static final ConcurrentHashMap<String, ChannelHolder> channelHolderMap = new ConcurrentHashMap<>();

    private static final ExecutorService REQUEST_EXECUTOR = new ThreadPoolExecutor(
            30,
            100,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(30),
            new BasicThreadFactory.Builder().namingPattern("request-service-connector-%d").build()
    );

    private static ZnsRequestPool ZNS_REQUEST_POOL;
    private static ServiceRouteCache SERVICE_ROUTE_CACHE;
    private static String CLUSTER_STRATEGY;

    public static void startZnsRequestManager(ZnsRequestPool znsRequestPool, ServiceRouteCache serviceRouteCache) {
        ZNS_REQUEST_POOL = znsRequestPool;
        SERVICE_ROUTE_CACHE = serviceRouteCache;
        CLUSTER_STRATEGY = SpringBeanFactory.getBean(ZnsClientConfiguration.class).getZnsClientClusterStrategy();
    }

    public static void sendRequest(ZnsRequest znsRequest) throws InterruptedException, ZnsException {
        ClusterStrategy strategy = ClusterEngine.queryClusterStrategy(CLUSTER_STRATEGY);
        List<ProviderService> providerServices = SERVICE_ROUTE_CACHE.getServiceRoutes(znsRequest.getClassName());
        ProviderService targetServiceProvider = strategy.select(providerServices);

        if (targetServiceProvider != null) {
            String requestId = znsRequest.getRequestId();
            CountDownLatch latch = new CountDownLatch(1);
            REQUEST_EXECUTOR.execute(new ZnsClientConnector(requestId, targetServiceProvider, latch));

            latch.await();

            ChannelHolder channelHolder = channelHolderMap.get(requestId);
            channelHolder.getChannel().writeAndFlush(znsRequest);
            LOGGER.info("Send request[{}:{}] to service provider successfully", requestId, znsRequest.toString());
        } else {
            throw new ZnsException(StatusCode.NO_AVAILABLE_SERVICE_PROVINDER);
        }
    }

    public static void registerChannelHolder(String requestId, ChannelHolder channelHolder) {
        if (StringUtils.isBlank(requestId) || channelHolder == null) {
            return;
        }
        channelHolderMap.put(requestId, channelHolder);
        LOGGER.info("Register ChannelHolder[{}:{}] successfully", requestId, channelHolder.toString());

        ZNS_REQUEST_POOL.submitRequest(requestId, channelHolder.getChannel().eventLoop());
        LOGGER.info("Submit request into ZnsRequestPool successfully");
    }

    public static void destroyChannelHolder(String requestId) {
        if (StringUtils.isBlank(requestId)) {
            return;
        }
        ChannelHolder channelHolder = channelHolderMap.remove(requestId);
        try {
            channelHolder.getChannel().closeFuture();
            channelHolder.getEventLoopGroup().shutdownGracefully();
        } catch (Exception ex) {
            LOGGER.error("Close ChannelHolder[{}] error", requestId);
        }
        LOGGER.info("Destroy ChannelHolder[{}] successfully", requestId);
    }
}
