package com.onemsg.onerpc.client;

import com.onemsg.onerpc.client.core.ClientService;

public class OneRpcClient {
    
    private static final ClientService servive = new ClientService();

    public static <T> T getService(Class<T> interfaceClass){
        return servive.getService(interfaceClass);
    }

}
