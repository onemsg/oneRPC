package com.onemsg.onerpc.server.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import com.onemsg.onerpc.core.model.RequestModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务调度器
 */
public final class ServiceDispatcher {

    private ServiceCenter center = ServiceCenter.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(ServiceDispatcher.class);

    public ServiceDispatcher() { }

    public Object invoke(RequestModel model) {

        Objects.requireNonNull(model);
        Object provider = center.getProvider(model.getClassName());
        if (provider != null){
            try {
                Method method = provider.getClass().getMethod(model.getMethodName(), model.getParameterTypes());
                return method.invoke(provider, model.getParams());
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                logger.error("methodName 执行出错: " + model.getMethodName(), e);
            }
        }else{
            logger.info("不存在 provider: {}", model.getClassName());
        }
        return null;
    }
}