package com.onemsg.onerpc.server.core;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务中心，管理着本地提供服务的对象
 */
public final class ServiceCenter {
    
    private final ConcurrentMap<String, Object> center = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(ServiceCenter.class);

    private static final ServiceCenter INSTANCE = new ServiceCenter();

    private ServiceCenter() { }

    public static ServiceCenter getInstance() {
        return INSTANCE;
    }
    
    public Object getProvider(String className){
        if(validateClassName(className)){
            return getProvider0(className);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProvider(Class<T> clazz){
        String className = clazz.getName();
        return (T) getProvider0(className);
    }

    private Object getProvider0(String className){
        Object o =  center.get(className);
        if(o == null){
            logger.warn("Service Center 不存在 {} 实例", className);
            return null;
        }
        return o;
    }

    public <T> void putProvider(Class<T> clazz, T provider){
        Objects.requireNonNull(provider, "RPC服务 provider 不能为空");
        center.put(clazz.getName(), provider);
    }


    public <T> void removeProvider(Class<T> clazz){
        center.remove(clazz.getName());
    }

    private static boolean validateClassName(String className){
        if(className == null || className.isBlank()) return false;
        try {
            return Class.forName(className) != null;
        } catch (Exception e) {
            logger.error("className 不存在", e);
            return false;
        }
    }
}
