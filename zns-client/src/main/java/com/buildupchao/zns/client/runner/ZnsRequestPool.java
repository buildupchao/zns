package com.buildupchao.zns.client.runner;

import com.buildupchao.zns.common.bean.ZnsResponse;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author buildupchao
 * @date 2019/2/1 03:16
 * @since JDK 1.8
 */
@Component
public class ZnsRequestPool {

    private final ConcurrentHashMap<String, Promise<ZnsResponse>> requestPool = new ConcurrentHashMap<>();

    public void submitRequest(String requestId, EventExecutor executor) {
        requestPool.put(requestId, new DefaultPromise<>(executor));
    }

    public ZnsResponse fetchResponse(String requestId) throws Exception {
        Promise<ZnsResponse> promise = requestPool.get(requestId);
        if (promise == null) {
            return null;
        }
        ZnsResponse znsResponse = promise.get(10, TimeUnit.SECONDS);
        requestPool.remove(requestId);

        ZnsRequestManager.destroyChannelHolder(requestId);
        return znsResponse;
    }

    public void notifyRequest(String requestId, ZnsResponse znsResponse) {
        Promise<ZnsResponse> promise = requestPool.get(requestId);
        if (promise != null) {
            promise.setSuccess(znsResponse);
        }
    }
}
