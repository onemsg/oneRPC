package com.onemsg.onerpc.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.onemsg.onerpc.registry.core.Address;
import com.onemsg.onerpc.registry.core.RegistryCenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OneRpcRegistryTest {
    
    RegistryCenter center;
    String serviceName = "com.onemsg.example.HelloService";
    String address = "localhost:18080";

    @BeforeEach
    void setup(){
        center = OneRpcRegistry.registryCenter();
    }

    @Test
    void testCenter(){

        boolean isRegisted = center.register(serviceName, Address.of(address));
        assertTrue(isRegisted);

        String address2 = center.discover("com.onemsg.example.HelloService").toString();
        assertEquals("localhost:18080", address2);

        boolean isUnRegisted = center.unRegister(serviceName,  Address.of(address));
        assertTrue(isUnRegisted);
    }
}
