package com.buildupchao.zns.server.acceptor.handler;

import com.buildupchao.zns.common.bean.ZnsRequest;
import com.buildupchao.zns.common.bean.ZnsResponse;
import com.buildupchao.zns.server.util.SpringBeanFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author buildupchao
 *         Date: 2019/1/31 21:45
 * @since JDK 1.8
 */
@Component
public class ZnsRequestHandler extends SimpleChannelInboundHandler<ZnsRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZnsRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ZnsRequest znsRequest) throws Exception {
        ZnsResponse znsResponse = new ZnsResponse();
        znsResponse.setRequestId(znsRequest.getRequestId());

        String className = znsRequest.getClassName();
        String methodName = znsRequest.getMethodName();
        Class<?>[] parameterTypes = znsRequest.getParameterTypes();
        Object[] parameterValues = znsRequest.getParameters();

        try {
            Object targetClass = SpringBeanFactory.getBean(Class.forName(className));
            Method targetMethod = targetClass.getClass().getMethod(methodName, parameterTypes);
            Object result = targetMethod.invoke(targetClass, parameterValues);
            znsResponse.setResult(result);
        } catch (Throwable cause) {
            LOGGER.error("{} invoke generated an error!", this.getClass().getSimpleName());
            znsResponse.setCause(cause);
        }
        ctx.writeAndFlush(znsResponse);
    }
}
