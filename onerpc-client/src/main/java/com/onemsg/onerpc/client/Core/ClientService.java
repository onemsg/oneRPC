package com.onemsg.onerpc.client.Core;

import java.lang.reflect.Proxy;

import com.onemsg.onerpc.registry.OneRpcRegistry;
import com.onemsg.onerpc.registry.core.RegistryCenter;

public class ClientService {
    
    private RegistryUtil registryUtil;
    private RpcHttpStub stub;
    
    public ClientService(){
        RegistryCenter registryCenter = OneRpcRegistry.registryCenter();
        registryUtil = new RegistryUtil(registryCenter);
        stub = new RpcHttpStub(registryUtil);
    }
    
    public ClientService(RegistryUtil registryUtil){
        this.registryUtil = registryUtil;
        stub = new RpcHttpStub(registryUtil);
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> interfaceClass){
        return (T) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{interfaceClass},
            new RpcInvocationHandler(interfaceClass, stub)
        );
    }

}
