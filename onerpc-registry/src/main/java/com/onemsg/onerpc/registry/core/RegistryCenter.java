package com.onemsg.onerpc.registry.core;

public interface RegistryCenter {
    
    boolean register(String name, Address address);

    Address discover(String name);

    boolean unRegister(String name, Address address);

}
