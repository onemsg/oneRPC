package com.onemsg.onerpc.server.registry;

import com.onemsg.onerpc.registry.core.Address;
import com.onemsg.onerpc.registry.core.RegistryCenter;
import com.onemsg.onerpc.server.OneRpcServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistryUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(OneRpcServer.class);

    private RegistryCenter registryCenter;
    private Address address;

    public RegistryUtil(RegistryCenter registryCenter, Address address){
        this.registryCenter = registryCenter;
        this.address = address;
    }

    public <T> void register(Class<T> clazz){
        boolean isSuccess = registryCenter.register(clazz.getName(), address);
        if(isSuccess){
            logger.info("服务 {} 注册成功", clazz.getName());
        }else{
            logger.warn("服务 {} 注册失败", clazz.getName());
        }
    }

    public void close(){
        registryCenter.close();
        logger.info("与注册中心的连接已关闭");
    }
}
