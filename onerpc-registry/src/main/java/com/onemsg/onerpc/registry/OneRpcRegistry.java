package com.onemsg.onerpc.registry;

import com.onemsg.onerpc.registry.common.ConfigService;
import com.onemsg.onerpc.registry.core.EtcdRegistryCenter;
import com.onemsg.onerpc.registry.core.RegistryCenter;

public class OneRpcRegistry {
    
    public static RegistryCenter registryCenter(){
        String[] endPoints = ConfigService.endPoints();
        return new EtcdRegistryCenter(endPoints);
    }
}
