package com.onemsg.onerpc.registry;

import java.util.Objects;

import com.onemsg.onerpc.registry.core.EtcdRegistryCenter;
import com.onemsg.onerpc.registry.core.RegistryCenter;

/**
 * OneRpc registry
 * 
 * @author onemsg
 * @since 2022-1
 */
public class OneRpcRegistry {
    
    private OneRpcRegistry() {}

    /**
     * Creates a etcd RegistryCenter with etcd.properties file
     * @return a new RegistryCenter
     */
    public static RegistryCenter registryCenter(){
        EtcdProperties etcdProperties = ConfigService.loadEtcdProperties();
        Objects.requireNonNull(etcdProperties, "Cant creates a etcd RegistryCente with a null etcdProperties");
        return new EtcdRegistryCenter(etcdProperties.endpoints);
    }

    /**
     * Creates a etcd RegistryCenter
     * @param endPoints etcd endPoints
     * @return a new RegistryCenter
     */
    public static RegistryCenter etcdRegistryCenter(String[] endPoints){
        return new EtcdRegistryCenter(endPoints);
    }

}
