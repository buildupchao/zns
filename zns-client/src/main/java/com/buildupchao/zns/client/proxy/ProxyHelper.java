package com.buildupchao.zns.client.proxy;

import com.buildupchao.zns.client.runner.ZnsRequestManager;
import com.buildupchao.zns.client.runner.ZnsRequestPool;
import com.buildupchao.zns.client.util.RequestIdUtil;
import com.buildupchao.zns.common.bean.ZnsRequest;
import com.buildupchao.zns.common.bean.ZnsResponse;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author buildupchao
 *         Date: 2019/2/1 12:44
 * @since JDK 1.8
 */
@Component
public class ProxyHelper {

    @Autowired
    private ZnsRequestPool znsRequestPool;

    public <T> T newProxyInstance(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new ProxyCallBackHandler());
        return (T) enhancer.create();
    }

    class ProxyCallBackHandler implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            return doIntercept(method, args);
        }

        private Object doIntercept(Method method, Object[] parameters) throws Throwable {
            String requestId = RequestIdUtil.requestId();
            String className = method.getDeclaringClass().getName();
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();

            ZnsRequest znsRequest = ZnsRequest.builder()
                    .requestId(requestId)
                    .className(className)
                    .methodName(methodName)
                    .parameterTypes(parameterTypes)
                    .parameters(parameters)
                    .build();

            ZnsRequestManager.sendRequest(znsRequest);
            ZnsResponse znsResponse = znsRequestPool.fetchResponse(requestId);
            if (znsResponse == null) {
                return null;
            }

            if (znsResponse.isError()) {
                throw znsResponse.getCause();
            }
            return znsResponse.getResult();
        }
    }
}
